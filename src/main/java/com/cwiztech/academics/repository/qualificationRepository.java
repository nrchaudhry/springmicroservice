package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Qualification;

public interface qualificationRepository extends JpaRepository<Qualification, Long> {

	@Query(value = "select * from TBLACADEMICSQUALIFICATION where ISACTIVE='Y'", nativeQuery = true)
	public List<Qualification> findActive();

	@Query(value = "select * from TBLACADEMICSQUALIFICATION  as a "
			+ "where (QUALIFICATION_CODE LIKE ?1 or QUALIFICATION_NAME like ?1 or "
			+ "QUALIFICATION_DESCRIPTION like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<Qualification> findBySearch(String serch);
	
	@Query(value = "select * from TBLACADEMICSQUALIFICATION  as a "
			+ "where QUALIFICATION_CODE LIKE ?1 or QUALIFICATION_NAME like ?1 or "
			+ "QUALIFICATION_DESCRIPTION like ?1 ", nativeQuery = true)
	public List<Qualification> findAllBySearch(String serch);
	
	@Query(value = "select * from TBLACADEMICSQUALIFICATION "
			+ "where UNIVERSITY_ID like CASE WHEN ?1=0 THEN UNIVERSITY_ID ELSE ?1 END and ISACTIVE='Y'", nativeQuery = true)
	public List<Qualification> findByAdvancedSearch(long uni_id);

	@Query(value = "select * from TBLACADEMICSQUALIFICATION "
			+ "where UNIVERSITY_ID like CASE WHEN ?1=0 THEN UNIVERSITY_ID ELSE ?1 END", nativeQuery = true)
	public List<Qualification> findAllByAdvancedSearch(long uni_id);
	
	@Query(value = "select * from TBLACADEMICSQUALIFICATION "
			+ "where QUALIFICATION_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Qualification> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSQUALIFICATION "
			+ "where QUALIFICATION_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Qualification> findByNotInIDs(@Param("ids") List<Integer> ids);

}
