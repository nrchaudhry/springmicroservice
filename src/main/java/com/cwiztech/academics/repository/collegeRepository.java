package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Campus;
import com.cwiztech.academics.model.College;

public interface collegeRepository extends JpaRepository<College, Long> {

	@Query(value = "select * from TBLACADEMICSCOLLEGE where ISACTIVE='Y'", nativeQuery = true)
	public List<College> findActive();

	@Query(value = "select * from TBLACADEMICSCOLLEGE  as a "
			+ "where (COLLEGE_CODE LIKE ?1 "
			+ "or COLLEGE_NAME like ?1 "
			+ "or COLLEGE_DESCRIPTION like ?1) "
			+ "and ISACTIVE='Y'"
			, nativeQuery = true)
	List<College> findBySearch(String search);
    
	@Query(value = "select * from TBLACADEMICSCOLLEGE  as a "
			+ "where COLLEGE_CODE LIKE ?1 "
			+ "or COLLEGE_NAME like ?1 "
			+ "or COLLEGE_DESCRIPTION like ?1 "
			, nativeQuery = true)
	List<College> findAllBySearch(String search);
	
	@Query(value = "select * from TBLACADEMICSCOLLEGE "
			+ "where UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN UNIVERSITY_ID ELSE ?1 END "
			+ "and COLLEGETYPE_ID LIKE CASE WHEN ?2 = 0 THEN COLLEGETYPE_ID ELSE ?2 END "
			+ "and ISACTIVE='Y'"
			, nativeQuery = true)
	public List<College> findByAdvancedSearch(long uniid, long ctid);

	@Query(value = "select * from TBLACADEMICSCOLLEGE "
			+ "where UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN UNIVERSITY_ID ELSE ?1 END "
			+ "and COLLEGETYPE_ID LIKE CASE WHEN ?2 = 0 THEN COLLEGETYPE_ID ELSE ?2 END"
			, nativeQuery = true)
	public List<College> findAllByAdvancedSearch(long uniid, long ctid);

	// Admission
	@Query(value = "select distinct d.* from TBLACADEMICSCOLLEGE as a "
			+ "inner join TBLACADEMICSINTAKECOURSE as b on a.COURSE_ID=b.COURSE_ID "
			+ "inner join TBLACADEMICSINTAKE as c on b.INTAKE_ID=c.INTAKE_ID "
			+ "inner join TBLACADEMICSCAMPUS as d on b.CAMPUS_ID=d.CAMPUS_ID "
			+ "where a.COURSE_ID=?1 and b.COURSEMODE_ID=?2 and a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y' and ISADMISSIONOPEN='Y'", nativeQuery = true)
	public List<College> findCollegeByCourseModeForAdmission(Long cid, Long cmid);

	@Query(value = "select  distinct a.* from TBLACADEMICSCOLLEGE as a "
			+ "inner join TBLACADEMICSDEPARTMENT as b on a.COLLEGE_ID=b.COLLEGE_ID "
			+ "inner join TBLACADEMICSMODULE as c on b.DEPARTMENT_ID=c.DEPARTMENT_ID "
			+ "inner join TBLACADEMICSCOURSEMODULE as d on c.MODULE_ID=d.MODULE_ID "
			+ "inner join TBLACADEMICSCOURSE as e on d.COURSE_ID=e.COURSE_ID "
			+ "inner join TBLACADEMICSINTAKECOURSE as f on e.COURSE_ID=f.COURSE_ID "
			+ "inner join TBLACADEMICSINTAKE as g on f.INTAKE_ID=g.INTAKE_ID "
			+ "where a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y' and d.ISACTIVE='Y' and e.ISACTIVE='Y' and f.ISACTIVE='Y' and g.ISACTIVE='Y' and ISADMISSIONOPEN='Y' ", nativeQuery = true)
	public List<College> findCollegeOpenAdmission();
	
	@Query(value = "select * from TBLACADEMICSCOLLEGE "
			+ "where COLLEGE_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<College> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSCOLLEGE "
			+ "where COLLEGE_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<College> findByNotInIDs(@Param("ids") List<Integer> ids);	
	
}
