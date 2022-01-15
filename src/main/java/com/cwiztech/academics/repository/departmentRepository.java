package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Department;

public interface departmentRepository extends JpaRepository<Department,Long>{

	@Query(value="select * from TBLACADEMICSDEPARTMENT where ISACTIVE='Y'",nativeQuery=true)	
	public List<Department> findActive();

	@Query(value = "select * from TBLACADEMICSDEPARTMENT  as a "
			+ "where (DEPARTMENT_CODE LIKE ?1 or DEPARTMENT_NAME like ?1 or DEPARTMENT_DESCRIPTION ?1) and isActive='Y' ", nativeQuery = true)
	List<Department> findBySearch(String search, long id);
    
	@Query(value = "select * from TBLACADEMICSDEPARTMENT  as a "
			+ "where DEPARTMENT_CODE LIKE ?1 or DEPARTMENT_NAME like ?1 or DEPARTMENT_DESCRIPTION ?1 ", nativeQuery = true)
	List<Department> findAllBySearch(String search, long id);
	
	@Query(value = "select * from TBLACADEMICSDEPARTMENT  as a "
			+ "inner join TBLACADEMICSCOLLEGE as b on a.COLLEGE_ID=b.COLLEGE_ID "
			+ "where UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN UNIVERSITY_ID ELSE ?1 END "
			+ "and a.COLLEGE_ID LIKE CASE WHEN ?2 = 0 THEN a.COLLEGE_ID ELSE ?2 END "
			+ "and COLLEGETYPE_ID LIKE CASE WHEN ?3 = 0 THEN COLLEGETYPE_ID ELSE ?3 END "
			+ "and a.ISACTIVE='Y'", nativeQuery = true)
	public List<Department> findByAdvancedSearch(Long uid, Long cid, Long ctid);

	@Query(value = "select * from TBLACADEMICSDEPARTMENT  as a "
			+ "inner join TBLACADEMICSCOLLEGE as b on a.COLLEGE_ID=b.COLLEGE_ID "
			+ "where UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN UNIVERSITY_ID ELSE ?1 END "
			+ "and a.COLLEGE_ID LIKE CASE WHEN ?2 = 0 THEN a.COLLEGE_ID ELSE ?2 END "
			+ "and COLLEGETYPE_ID LIKE CASE WHEN ?3 = 0 THEN COLLEGETYPE_ID ELSE ?3 END ", nativeQuery = true)
	public List<Department> findAllByAdvancedSearch(Long uid, Long cid, Long ctid);

	@Query(value = "select * from TBLACADEMICSDEPARTMENT "
			+ "where DEPARTMENT_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Department> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSDEPARTMENT "
			+ "where DEPARTMENT_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Department> findByNotInIDs(@Param("ids") List<Integer> ids);
}
