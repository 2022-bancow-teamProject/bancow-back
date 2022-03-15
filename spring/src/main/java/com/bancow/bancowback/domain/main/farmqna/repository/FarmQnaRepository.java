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

	@Query(nativeQuery = true, value = "select concat(year(f.create_date) , '-', lpad(month(f.create_date), 2, '0')) ym, count(f.create_date) month_count "
		+"from bancow.farm_qna as f where year(f.create_date) = :#{#year} group by ym")
	List<Map<String, Object>> countMonth(int year);

}
