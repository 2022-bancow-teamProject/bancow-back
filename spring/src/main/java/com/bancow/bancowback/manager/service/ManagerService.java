package com.bancow.bancowback.manager.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bancow.bancowback.common.dto.ServiceResult;
import com.bancow.bancowback.common.exception.BizException;
import com.bancow.bancowback.common.util.PasswordUtils;
import com.bancow.bancowback.common.util.mail.MailComponent;
import com.bancow.bancowback.common.util.mail.entity.MailTemplate;
import com.bancow.bancowback.common.util.mail.repository.MailTemplateRepository;
import com.bancow.bancowback.common.util.token.entity.Token;
import com.bancow.bancowback.common.util.token.repository.TokenRepository;
import com.bancow.bancowback.common.util.token.service.TokenService;
import com.bancow.bancowback.manager.dto.ManagerDto;
import com.bancow.bancowback.manager.dto.ManagerFindDto;
import com.bancow.bancowback.manager.dto.ManagerLoginDto;
import com.bancow.bancowback.manager.dto.ManagerPasswordDto;
import com.bancow.bancowback.manager.dto.ManagerRegisterDto;
import com.bancow.bancowback.manager.entity.Manager;
import com.bancow.bancowback.manager.entity.ManagerStatus;
import com.bancow.bancowback.manager.repository.ManagerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerService {

	private final ManagerRepository managerRepository;
	private final TokenRepository tokenRepository;
	private final TokenService tokenService;

	private final MailTemplateRepository mailTemplateRepository;
	private final MailComponent mailComponent;

	public ServiceResult registerManager(ManagerRegisterDto managerRegisterDto) {

		Optional<Manager> optionalUser = managerRepository.findByEmail(managerRegisterDto.getEmail());
		if (optionalUser.isPresent()) {
			throw new BizException("이미 가입된 이메일입니다.");
		}

		if (!(managerRegisterDto.getPassword().equals(managerRegisterDto.getPassword2()))) {
			throw new BizException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		}

		String encryptPassword = PasswordUtils.encryptedPassword(managerRegisterDto.getPassword());

		Manager manager = Manager.builder()
			.email(managerRegisterDto.getEmail())
			.password(encryptPassword)
			.username(managerRegisterDto.getUsername())
			.managerStatus(ManagerStatus.PENDING_EMAIL)
			.createDate(LocalDateTime.now())
			.updateDate(LocalDateTime.now())
			.build();

		managerRepository.save(manager);

		sendMail(manager, "MANAGER_REGISTER");

		return ServiceResult.success("회원가입을 성공하였습니다.");
	}

	public ServiceResult loginManager(ManagerLoginDto managerLoginDto) {

		Manager manager = managerRepository.findByEmail(managerLoginDto.getEmail())
			.orElseThrow(() -> new BizException("사용자 정보가 없습니다."));

		if (!PasswordUtils.equalPassword(managerLoginDto.getPassword(), manager.getPassword())) {
			throw new BizException("비밀번호가 일치하지 않습니다.");
		}

		String authenticationKey = makeJwtToken(manager);
		Token token = Token.builder()
			.token(authenticationKey)
			.manager(manager)
			.expiredDate(LocalDateTime.now().plusDays(1))
			.build();

		tokenRepository.save(token);

		return ServiceResult.success(manager.getUsername() + " 님의 로그인에 성공하였습니다.");
	}

	public ServiceResult logoutManager(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("토큰 정보를 찾을 수 없습니다."));
		String username = findToken.getManager().getUsername();
		tokenRepository.delete(findToken);
		return ServiceResult.success(username + " 님의 로그아웃에 성공하였습니다.");
	}

	public ServiceResult authentication(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("Not Found Token"));
		String email = findToken.getManager().getEmail();
		Manager user = managerRepository.findByEmail(email)
			.orElseThrow(() -> new BizException("User Not Found"));
		user.setManagerStatus(ManagerStatus.PENDING_SUPER);
		managerRepository.save(user);
		tokenRepository.delete(findToken);

		return ServiceResult.success(user.getUsername() + " 님의 인증에 성공하였습니다.");
	}

	private void sendMail(Manager manager, String templateId) {
		String serverURL = "http://localhost:8080";

		String userAuthenticationKey = makeJwtToken(manager);
		Token token = Token.builder()
			.token(userAuthenticationKey)
			.manager(manager)
			.expiredDate(LocalDateTime.now().plusDays(1))
			.build();

		tokenRepository.save(token);

		Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId(templateId);
		optionalMailTemplate.ifPresent(e -> {
			String fromEmail = e.getSendEmail();
			String fromUserName = e.getSendUserName();
			String title = e.getTitle().replaceAll("\\{USER_NAME\\}", manager.getUsername());
			String contents = e.getContents().replaceAll("\\{USER_NAME\\}", manager.getUsername())
				.replaceAll("\\{SERVER_URL\\}", serverURL)
				.replaceAll("\\{USER_AUTHENTICATION_KEY\\}", userAuthenticationKey);

			mailComponent.send(fromEmail, fromUserName, manager.getEmail(), manager.getUsername(), title, contents);
		});
	}

	public String makeJwtToken(Manager manager) {
		LocalDateTime expiredDatetime = LocalDateTime.now().plusMonths(1);
		Date expiredDate = java.sql.Timestamp.valueOf(expiredDatetime);
		return JWT.create()
			.withExpiresAt(expiredDate)
			.withClaim("user_id", manager.getId())
			.withSubject(manager.getUsername())
			.withIssuer(manager.getEmail())
			.sign(Algorithm.HMAC512("bancowAlgorithm".getBytes()));
	}

	public ServiceResult statusToAdmin(String token, Long id) {

		tokenService.checkTokenSuper(token);

		Manager manager = managerRepository.findById(id)
			.orElseThrow(() -> new BizException("해당 정보의 유저가 존재하지 않습니다."));
		manager.setManagerStatus(ManagerStatus.ADMIN);
		managerRepository.save(manager);

		return ServiceResult.success(manager.getUsername() + " 님의 상태를 ADMIN으로 변경하였습니다.");
	}

	public List<ManagerDto> findAllManager(String token) {

		tokenService.checkTokenSuper(token);
		List<Manager> allManager = managerRepository.findAll();
		List<ManagerDto> allManagerDto = new ArrayList<>();
		allManager.forEach(e -> {
			ManagerDto build = ManagerDto.builder()
				.email(e.getEmail())
				.username(e.getUsername())
				.managerStatus(e.getManagerStatus())
				.createDate(e.getCreateDate())
				.updateDate(e.getUpdateDate())
				.build();
			allManagerDto.add(build);
		});

		return allManagerDto;
	}

	public ServiceResult findManager(ManagerFindDto managerFindDto) {
		Optional<Manager> optionalManager = managerRepository.findByEmail(managerFindDto.getEmail());
		if (optionalManager.isEmpty()) {
			throw new BizException("존재하지 않는 이메일입니다.");
		}
		Manager manager = optionalManager.get();
		if (!manager.getUsername().equals(managerFindDto.getUsername())) {
			throw new BizException("회원정보가 틀립니다.");
		}

		sendMail(manager, "FIND_MANAGER");
		return ServiceResult.success(manager.getUsername() + " 님의 이메일로 비밀번호 초기화 메시지가 발송되었습니다.");
	}

	public Manager authenticationPassword(String token) {
		Token findToken = tokenRepository.findByToken(token)
			.orElseThrow(() -> new BizException("Not Found Token"));
		String email = findToken.getManager().getEmail();
		return managerRepository.findByEmail(email).orElseThrow(() -> new BizException("User Not Found"));
	}

	public ServiceResult changePassword(String token, ManagerPasswordDto managerPasswordDto) {
		Manager manager = authenticationPassword(token);
		if (!managerPasswordDto.getPassword1().equals(managerPasswordDto.getPassword2())) {
			throw new BizException("비밀번호1, 2가 일치하지 않습니다.");
		}
		String encryptPassword = PasswordUtils.encryptedPassword(managerPasswordDto.getPassword1());
		manager.setPassword(encryptPassword);
		managerRepository.save(manager);

		Token findToken = tokenRepository.findByToken(token).get();
		tokenRepository.delete(findToken);

		return ServiceResult.success(manager.getUsername() + " 님의 비밀번호 변경에 성공하였습니다.");
	}
}
