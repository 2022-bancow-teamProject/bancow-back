package com.bancow.bancowback.domain.main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancow.bancowback.domain.main.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
