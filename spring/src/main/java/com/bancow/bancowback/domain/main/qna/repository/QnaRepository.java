package com.bancow.bancowback.domain.main.qna.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.qna.entity.Qna;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long> {
	@Query("select count(q) from Qna q")
	Integer countQna();

	@Query("select count(q) from Qna q where q.checked = false")
	Integer uncheckedQna();

	@Query(nativeQuery = true, value =
		"select concat(year(q.create_date) , '-', lpad(month(q.create_date), 2, '0')) year_month, count(q.create_date) month_count "
			+ "from qna q where year(q.create_date) = :#{#year} group by year_month")
	List<Map<String, Object>> countMonth(int year);

	List<Qna> findByIdIn(List<Long> id);
}
