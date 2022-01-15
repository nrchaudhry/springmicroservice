package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Building;
import com.cwiztech.academics.model.Campus;

public interface campusRepository extends JpaRepository<Campus, Long> {

	@Query(value = "select * from TBLACADEMICSCAMPUS where ISACTIVE='Y' ", nativeQuery = true)
	public List<Campus> findActive();

    @Query(value = "select * from TBLACADEMICSCAMPUS  as a "
		+ "where CAMPUS_CODE LIKE CASE WHEN ?1='' THEN CAMPUS_CODE ELSE ?1 END or "
		+ "CAMPUS_NAME like CASE WHEN ?1='' THEN CAMPUS_NAME ELSE ?1 END or "
		+ "CAMPUS_DESCRIPTION like CASE WHEN ?1='' THEN CAMPUS_DESCRIPTION ELSE ?1 END and isActive='Y' ", nativeQuery = true)
    List<Campus> findBySearch(String search);

    @Query(value = "select * from TBLACADEMICSCAMPUS  as a "
			+ "where CAMPUS_CODE LIKE CASE WHEN ?1='' THEN CAMPUS_CODE ELSE ?1 END or "
			+ "CAMPUS_NAME like CASE WHEN ?1='' THEN CAMPUS_NAME ELSE ?1 END or "
			+ "CAMPUS_DESCRIPTION like CASE WHEN ?1='' THEN CAMPUS_DESCRIPTION ELSE ?1 END ", nativeQuery = true)
	List<Campus> findAllBySearch(String search);

	@Query(value = "select * from TBLACADEMICSCAMPUS "
			+ "where UNIVERSITY_ID like CASE WHEN ?1=0 THEN UNIVERSITY_ID ELSE ?1 END and ISACTIVE='Y'", nativeQuery = true)
	public List<Campus> findByAdvancedSearch(long uni_id);

	@Query(value = "select * from TBLACADEMICSCAMPUS "
			+ "where UNIVERSITY_ID like CASE WHEN ?1=0 THEN UNIVERSITY_ID ELSE ?1 END", nativeQuery = true)
	public List<Campus> findAllByAdvancedSearch(long uni_id);

	// Admission
	@Query(value = "select distinct d.* from TBLACADEMICSCOURSE as a inner join TBLACADEMICSINTAKECOURSE as b on a.COURSE_ID=b.COURSE_ID inner join TBLACADEMICSINTAKE as c on b.INTAKE_ID=c.INTAKE_ID inner join TBLACADEMICSCAMPUS as d on b.CAMPUS_ID=d.CAMPUS_ID where a.COURSE_ID=?1 and b.COURSEMODE_ID=?2 and a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y' and ISADMISSIONOPEN='Y'", nativeQuery = true)
	public List<Campus> findCampusByCourseModeForAdmission(Long cid, Long cmid);
	
	@Query(value = "select * from TBLACADEMICSCAMPUS "
			+ "where CAMPUS_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Campus> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSCAMPUS "
			+ "where CAMPUS_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Campus> findByNotInIDs(@Param("ids") List<Integer> ids);	

}
