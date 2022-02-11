package com.bancow.bancowback.domain.main.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByStatus(boolean b);
}
