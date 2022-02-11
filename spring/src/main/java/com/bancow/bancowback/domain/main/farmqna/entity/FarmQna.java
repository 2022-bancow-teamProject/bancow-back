package com.bancow.bancowback.domain.main.farmqna.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmQna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "farm_qna_name", nullable = false)
	private String farmQnaName;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String email;

	@Column(name = "farm_name", nullable = false)
	private String farmName;

	@Column(name = "farm_address", nullable = false)
	private String farmAddress;

	@Column(name = "cow_num", nullable = false)
	private Integer cowNum;

	@Column(name = "feed_name", nullable = false)
	private String feedName;

	@Column(nullable = false)
	private Boolean checked;

	@Column(name = "available_date", nullable = false)
	private LocalDate availableDate;

	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

}
