package com.bancow.bancowback.domain.main.farmqna.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bancow.bancowback.domain.main.farmqna.entity.FarmQna;

@Repository
public interface FarmQnaRepository extends JpaRepository<FarmQna, Long> {

	List<FarmQna> findByIdIn(List<Long> idList);

	@Query("select count(f) from FarmQna f")
	Integer countFarmQna();

	@Query("select count(f) from FarmQna f where f.checked = false")
	Integer uncheckedFarmQna();

	// @Query("select function('MONTH', f.createDate) from FarmQna f "
	// 	+ "where function('YEAR', f.createDate) = :year group by f.createDate")
	// List<Integer> countMonth(int year);

	// @Query("select function('MONTH', f.createDate), count(f) from FarmQna f "
	// 	+ "where function('YEAR', f.createDate) = :year group by f.createDate")
	// List<Map<Integer, Integer>> countMonth(int year);

	// @Query("select concat( function('year', f.create_date) , '-', function('lpad', (function('month', f.create_date), 2, '0'))) fg, count(f.create_date) "
	// 	+ "from FarmQna f "
	// 	+ "where function('YEAR', f.createDate) = :year group by f.createDate")
	@Query(nativeQuery = true, value = "select concat(year(f.create_date) , '-', lpad(month(f.create_date), 2, '0')) year_month, count(f.create_date) month_count "
		+"from Farm_qna f where year(f.create_date) = :#{#year} group by year_month")
	List<Map<String, Object>> countMonth(int year);



}
