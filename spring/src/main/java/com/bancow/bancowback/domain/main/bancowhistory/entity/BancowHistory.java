package com.bancow.bancowback.domain.main.bancowhistory.entity;

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

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BancowHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String historyDate;

	@Column
	private String message;

	@Column
	private boolean status;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "update_date")
	private LocalDateTime updateDate;
}
