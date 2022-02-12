package com.bancow.bancowback.domain.sub.buyer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import com.bancow.bancowback.domain.common.dto.BaseTimeEntity;
import com.bancow.bancowback.domain.manager.entity.Manager;
import com.bancow.bancowback.domain.sub.farm.entity.Farm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Buyer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "buyer_name", nullable = false)
	private String buyerName;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@OneToOne(fetch = FetchType.LAZY)
	private Manager manager;

	@OneToOne(fetch = FetchType.LAZY)
	private Farm farm;

	@Column(nullable = false)
	private Boolean status;

	@Column(name = "create_date", nullable = false)
	@CreatedDate
	private LocalDateTime createDate;

}
