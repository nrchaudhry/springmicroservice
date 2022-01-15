package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Building;

public interface buildingRepository extends JpaRepository<Building, Long> {

	@Query(value = "select * from TBLACADEMICSCAMPUSBUILDING where ISACTIVE='Y'", nativeQuery = true)
	public List<Building> findActive();
	
	@Query(value = "select * from TBLACADEMICSCAMPUSBUILDING  as a "
			+ "where CAMPUS_ID LIKE CASE WHEN ?2='' THEN CAMPUS_ID ELSE ?2 END and "
			+ "(BUILDING_NAME like CASE WHEN ?1='' THEN BUILDING_NAME ELSE ?1 END or "
			+ "BUILDING_CODE like CASE WHEN ?1='' THEN BUILDING_CODE ELSE ?1 END and ISACTIVE='Y'", nativeQuery = true)
	List<Building> findBySearch(String search, long id);

	@Query(value = "select * from TBLACADEMICSCAMPUSBUILDING  as a "
			+ "where CAMPUS_ID LIKE CASE WHEN ?2='' THEN CAMPUS_ID ELSE ?2 END and "
			+ "(BUILDING_NAME like CASE WHEN ?1='' THEN BUILDING_NAME ELSE ?1 END or "
			+ "BUILDING_CODE like CASE WHEN ?1='' THEN BUILDING_CODE ELSE ?1 END)", nativeQuery = true)
	List<Building> findAllBySearch(String search, long id);	

	@Query(value = "Select a.* from TBLACADEMICSCAMPUSBUILDING as a "
			+ "inner join TBLACADEMICSCAMPUS as b on a.CAMPUS_ID=b.CAMPUS_ID "
			+ "inner join TBLACADEMICSUNIVERSITY as c on b.UNIVERSITY_ID=c.UNIVERSITY_ID "
			+ "where b.UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN b.UNIVERSITY_ID ELSE ?1 END and "
			+ "a.CAMPUS_ID LIKE CASE WHEN ?2 = 0 THEN a.CAMPUS_ID ELSE ?2 END and "
			+ "a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y'", nativeQuery = true)
	public List<Building> findByAdvancedSearch(Long university_ID, Long campus_ID);

	@Query(value = "Select a.* from TBLACADEMICSCAMPUSBUILDING as a "
			+ "inner join TBLACADEMICSCAMPUS as b on a.CAMPUS_ID=b.CAMPUS_ID "
			+ "inner join TBLACADEMICSUNIVERSITY as c on b.UNIVERSITY_ID=c.UNIVERSITY_ID "
			+ "where b.UNIVERSITY_ID LIKE CASE WHEN ?1 = 0 THEN b.UNIVERSITY_ID ELSE ?1 END and "
			+ "a.CAMPUS_ID LIKE CASE WHEN ?2 = 0 THEN a.CAMPUS_ID ELSE ?2 END", nativeQuery = true)
	public List<Building> findAllByAdvancedSearch(Long university_ID, Long campus_ID);
	
	@Query(value = "select * from TBLACADEMICSCAMPUSBUILDING "
			+ "where BUILDING_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Building> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSCAMPUSBUILDING "
			+ "where BUILDING_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Building> findByNotInIDs(@Param("ids") List<Integer> ids);	
	
}
