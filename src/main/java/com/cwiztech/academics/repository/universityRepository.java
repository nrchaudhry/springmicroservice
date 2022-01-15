package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.University;

public interface universityRepository extends JpaRepository<University, Long> {

	@Query(value = "select * from TBLACADEMICSUNIVERSITY where ISACTIVE='Y'", nativeQuery = true)
	public List<University> findActive();

	@Query(value = "select * from TBLACADEMICSUNIVERSITY "
			+ "where UNIVERSITY_CODE like ?1 or  UNIVERSITY_NAME like ?1 or  UNIVERSITY_DESCRIPTION like ?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<University> findBySearch(String search);

	@Query(value = "select * from TBLACADEMICSUNIVERSITY "
			+ "where UNIVERSITY_CODE like ?1 or  UNIVERSITY_NAME like ?1 or  UNIVERSITY_DESCRIPTION like ?1 ", nativeQuery = true)
	public List<University> findAllBySearch(String search);

	@Query(value = "select * from TBLACADEMICSUNIVERSITY " 
			+ "where ADDRESSCOUNTRY_ID LIKE CASE WHEN ?1 = 0 THEN ADDRESSCOUNTRY_ID ELSE ?1 END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<University> findByAdvancedSearch(Long countryID);

	@Query(value = "select * from TBLACADEMICSUNIVERSITY " 
			+ "where ADDRESSCOUNTRY_ID LIKE CASE WHEN ?1 = 0 THEN ADDRESSCOUNTRY_ID ELSE ?1 END ", nativeQuery = true)
	List<University> findAllByAdvancedSearch(Long countryID);
	
	@Query(value = "select * from TBLACADEMICSUNIVERSITY "
			+ "where UNIVERSITY_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<University> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSUNIVERSITY "
			+ "where UNIVERSITY_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<University> findByNotInIDs(@Param("ids") List<Integer> ids);
}
