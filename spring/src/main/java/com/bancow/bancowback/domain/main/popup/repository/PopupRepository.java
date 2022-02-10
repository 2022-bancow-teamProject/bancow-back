package com.bancow.bancowback.domain.main.popup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.popup.entity.Popup;

public interface PopupRepository extends JpaRepository<Popup, Long> {
	List<Popup> findByIdIn(List<Long> idList);

	Popup findByStatus(boolean b);
}