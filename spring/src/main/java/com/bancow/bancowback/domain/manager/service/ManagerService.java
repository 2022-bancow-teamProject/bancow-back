package com.bancow.bancowback.domain.manager.service;

import static com.bancow.bancowback.domain.common.exception.ErrorCode.*;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bancow.bancowback.domain.common.dto.ServiceResult;
import com.bancow.bancowback.domain.common.exception.ManagerNotFoundException;
import com.bancow.bancowback.domain.common.exception.ManagerNotValidException;
import com.bancow.bancowback.domain.common.exception.ManagerNotValidPasswordException;
import com.bancow.bancowback.domain.common.exception.RegisterDuplicateEmailException;
import com.bancow.bancowback.domain.common.exception.RegisterNotEqualPasswordException;
import com.bancow.bancowback.domain.common.exception.TokenNotFoundException;
import com.bancow.bancowback.domain.common.util.PasswordUtils;
import com.bancow.bancowback.domain.common.util.mail.service.MailService;
import com.bancow.bancowback.domain.common.util.token.entity.Token;
import com.bancow.bancowback.domain.common.util.token.service.TokenService;
import com.bancow.bancowback.domain.manager.dto.ManagerFindDto;
import com.bancow.bancowback.domain.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.domain.manager.dto.ManagerLoginResultDto;
import com.bancow.bancowback.domain.manager.dto.ManagerPasswordDto;
import com.bancow.bancowback.domain.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.domain.manager.dto.ManagerResponseDto;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.manager.entity.ManagerStatus;
import com.bancow.bancowback.domain.manager.mapper.ManagerMapper;
import com.bancow.bancowback.domain.manager.repository.ManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {

	private final ManagerRepository managerRepository;
	private final ManagerMapper managerMapper;
	private final TokenService tokenService;
	private final MailService mailService;

	public ServiceResult registerManager(ManagerRegisterDto managerRegisterDto) {

		Optional<Manager> optionalUser = managerRepository.findByEmail(managerRegisterDto.getEmail());
		if (optionalUser.isPresent()) {
			throw new RegisterDuplicateEmailException(DUPLICATE_EMAIL, "이미 가입된 이메일입니다.");
		}

		if (!(managerRegisterDto.getPassword().equals(managerRegisterDto.getPassword2()))) {
			throw new RegisterNotEqualPasswordException(NOT_EQUAL_PASSWORD, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		}

		Manager manager = managerMapper.registerToEntity(managerRegisterDto);

		managerRepository.save(manager);

		mailService.sendMail(manager, "MANAGER_REGISTER");

		return ServiceResult.success("회원가입을 성공하였습니다.");
	}

	public ManagerLoginResultDto loginManager(ManagerLoginDto managerLoginDto) {

		Manager manager = managerRepository.findByEmail(managerLoginDto.getEmail())
			.orElseThrow(() -> new ManagerNotFoundException(NOT_FOUND_MANAGER, "사용자 정보가 없습니다."));

		if (!PasswordUtils.equalPassword(managerLoginDto.getPassword(), manager.getPassword())) {
			throw new ManagerNotValidPasswordException(NOT_VALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
		}

		Token token = tokenService.saveByManager(manager);
		tokenService.validTokenAuthority(token.getToken());
		return ManagerLoginResultDto.builder()
			.token(token.getToken())
			.build();
	}

	public ServiceResult logoutManager(String token) {
		Token findToken = tokenService.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException(NOT_FOUND_TOKEN, "토큰 정보를 찾을 수 없습니다."));
		String username = findToken.getManager().getUsername();
		tokenService.delete(findToken);
		return ServiceResult.success(username + " 님의 로그아웃에 성공하였습니다.");
	}

	public ServiceResult authentication(String token) {
		Token findToken = tokenService.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException(NOT_FOUND_TOKEN, "Not Found Token"));
		String email = findToken.getManager().getEmail();
		Manager user = managerRepository.findByEmail(email)
			.orElseThrow(() -> new ManagerNotFoundException(NOT_FOUND_MANAGER, "User Not Found"));
		user.setManagerStatus(ManagerStatus.PENDING_SUPER);
		managerRepository.save(user);
		tokenService.delete(findToken);

		return ServiceResult.success(user.getUsername() + " 님의 인증에 성공하였습니다.");
	}

	public ServiceResult statusToAdmin(String token, Long id) {

		tokenService.validTokenSuper(token);

		Manager manager = managerRepository.findById(id)
			.orElseThrow(() -> new ManagerNotFoundException(NOT_FOUND_MANAGER, "해당 정보의 유저가 존재하지 않습니다."));
		manager.setManagerStatus(ManagerStatus.ADMIN);
		managerRepository.save(manager);

		return ServiceResult.success(manager.getUsername() + " 님의 상태를 ADMIN으로 변경하였습니다.");
	}

	public Page<ManagerResponseDto> findPagingManager(int page, String token) {

		tokenService.validTokenSuper(token);
		Page<Manager> allManager = managerRepository.findAll(
			PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id")));
		return allManager.map(managerMapper::toRequest);
	}

	public ServiceResult findManager(ManagerFindDto managerFindDto) {
		Optional<Manager> optionalManager = managerRepository.findByEmail(managerFindDto.getEmail());
		if (optionalManager.isEmpty()) {
			throw new ManagerNotValidException(NOT_VALID_USER, "존재하지 않는 이메일입니다.");
		}
		Manager manager = optionalManager.get();
		if (!manager.getUsername().equals(managerFindDto.getUsername())) {
			throw new ManagerNotValidException(NOT_VALID_USER, "회원정보가 틀립니다.");
		}

		mailService.sendMail(manager, "FIND_MANAGER");
		return ServiceResult.success(manager.getUsername() + " 님의 이메일로 비밀번호 초기화 메시지가 발송되었습니다.");
	}

	public ServiceResult authenticationPassword(String token) {
		Manager manager = getManagerAuthenticationPassword(token);
		return ServiceResult.success(manager.getUsername() + " 님의 비밀번호 변경을 위한 인증에 성공하였습니다.");
	}

	private Manager getManagerAuthenticationPassword(String token) {
		Token findToken = tokenService.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException(NOT_FOUND_TOKEN, "Not Found Token"));
		String email = findToken.getManager().getEmail();
		return managerRepository.findByEmail(email).orElseThrow(() -> new ManagerNotFoundException(NOT_FOUND_MANAGER, "User Not Found"));
	}

	public ServiceResult changePassword(String token, ManagerPasswordDto managerPasswordDto) {
		Manager manager = getManagerAuthenticationPassword(token);
		if (!managerPasswordDto.getPassword1().equals(managerPasswordDto.getPassword2())) {
			throw new RegisterNotEqualPasswordException(NOT_EQUAL_PASSWORD, "비밀번호1, 2가 일치하지 않습니다.");
		}
		String encryptPassword = PasswordUtils.encryptedPassword(managerPasswordDto.getPassword1());
		manager.setPassword(encryptPassword);
		managerRepository.save(manager);

		Token findToken = tokenService.findByToken(token).get();
		tokenService.delete(findToken);

		return ServiceResult.success(manager.getUsername() + " 님의 비밀번호 변경에 성공하였습니다.");
	}
}
