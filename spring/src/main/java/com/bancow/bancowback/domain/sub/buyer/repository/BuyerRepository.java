package com.bancow.bancowback.domain.sub.buyer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.sub.buyer.entity.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
	List<Buyer> findByStatus(boolean b);

	List<Buyer> findByIdIn(List<Long> id);
}
