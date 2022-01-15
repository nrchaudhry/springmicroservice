package com.cwiztech.academics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwiztech.academics.model.Room;

public interface roomRepository extends JpaRepository<Room, Long> {

	@Query(value = "select * from TBLACADEMICSCAMPUSROOM where ISACTIVE='Y'", nativeQuery = true)
	public List<Room> findActive();

	@Query(value = "select * from TBLACADEMICSCAMPUSROOM "
			+ "where ROOM_CODE LIKE ?1 or ROOM_NAME like ?1  and ISACTIVE='Y'", nativeQuery = true)
	public List<Room> findAllBySearch(String serch);

	@Query(value = "select * from TBLACADEMICSCAMPUSROOM "
			+ "where ROOM_CODE LIKE ?1 or ROOM_NAME like ?1 ", nativeQuery = true)
	public List<Room> findBySearch(String serch);

	@Query(value = "select a.* from TBLACADEMICSCAMPUSROOM as a "
			+ "inner join TBLACADEMICSCAMPUSBUILDING as b on a.BUILDING_ID=b.BUILDING_ID "
			+ "inner join TBLACADEMICSCAMPUS as c on b.CAMPUS_ID=c.CAMPUS_ID "
			+ "inner join TBLACADEMICSUNIVERSITY as d on c.UNIVERSITY_ID=d.UNIVERSITY_ID "
			+ "where b.CAMPUS_ID LIKE CASE WHEN ?1 = 0 THEN b.CAMPUS_ID ELSE ?1 END "
			+ "and a.BUILDING_ID LIKE CASE WHEN ?2 = 0 THEN a.BUILDING_ID ELSE ?2 END "
			+ "and c.UNIVERSITY_ID LIKE CASE WHEN ?3 = 0 THEN c.UNIVERSITY_ID ELSE ?3 END "
			+ "order by b.CAMPUS_ID, a.BUILDING_ID, ROOM_CODE", nativeQuery = true)
	public List<Room> findAllByAdvancedSearch(Long cid, Long bid, Long uid);

	@Query(value = "select a.* from TBLACADEMICSCAMPUSROOM as a "
			+ "inner join TBLACADEMICSCAMPUSBUILDING as b on a.BUILDING_ID=b.BUILDING_ID "
			+ "inner join TBLACADEMICSCAMPUS as c on b.CAMPUS_ID=c.CAMPUS_ID "
			+ "inner join TBLACADEMICSUNIVERSITY as d on c.UNIVERSITY_ID=d.UNIVERSITY_ID "
			+ "where b.CAMPUS_ID LIKE CASE WHEN ?1 = 0 THEN b.CAMPUS_ID ELSE ?1 END "
			+ "and a.BUILDING_ID LIKE CASE WHEN ?2 = 0 THEN a.BUILDING_ID ELSE ?2 END "
			+ "and c.UNIVERSITY_ID LIKE CASE WHEN ?3 = 0 THEN c.UNIVERSITY_ID ELSE ?3 END "
			+ "and a.ISACTIVE='Y' and b.ISACTIVE='Y' and c.ISACTIVE='Y' "
			+ "order by b.CAMPUS_ID, a.BUILDING_ID, ROOM_CODE", nativeQuery = true)
	public List<Room> findByAdvancedSearch(Long cid, Long bid, Long uid);
	
	@Query(value = "select * from TBLACADEMICSCAMPUSROOM "
			+ "where ROOM_ID in (:ids) "
			+ "", nativeQuery = true)
	public List<Room> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLACADEMICSCAMPUSROOM "
			+ "where ROOM_ID not in (:ids) "
			+ "", nativeQuery = true)
	public List<Room> findByNotInIDs(@Param("ids") List<Integer> ids);
}
