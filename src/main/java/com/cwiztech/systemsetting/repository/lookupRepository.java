package com.cwiztech.systemsetting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwiztech.systemsetting.model.Lookup;;

@Repository
public interface lookupRepository extends JpaRepository<Lookup, Long> {

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ISACTIVE='Y'", nativeQuery = true)
	public List<Lookup> findActive();

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ID in (:ids) ", nativeQuery = true)
	public List<Lookup> findByIDs(@Param("ids") List<Integer> ids);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP "
			+ "where (CODE like ?1 or DESCRIPTION like ?1 or ENTITY_STATUS like ?1) and ISACTIVE='Y'", nativeQuery = true)
	public List<Lookup> findBySearch(String search);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP "
			+ "where CODE like ?1 or DESCRIPTION like ?1 or ENTITY_STATUS like ?1 ", nativeQuery = true)
	public List<Lookup> findAllBySearch(String search);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP " 
			+ "where ID LIKE CASE WHEN ?1 = 0 THEN ID ELSE ?1 END "
			+ "and ISACTIVE='Y'", nativeQuery = true)
	List<Lookup> findByAdvancedSearch(Long id);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP " 
			+ "where ID LIKE CASE WHEN ?1 = 0 THEN ID ELSE ?1 END ", nativeQuery = true)
	List<Lookup> findAllByAdvancedSearch(Long id);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME=?1 and ISACTIVE='Y'", nativeQuery = true)
	public List<Lookup> findActiveByEntityName(String data);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME=?1", nativeQuery = true)
	public List<Lookup> findAllByEntityName(String data);

	@Query(value = "select distinct ENTITYNAME from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME not in ('EMAILSETTING') order by ENTITYNAME", nativeQuery = true)
	public List<Object> findEntityList();

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where DESCRIPTION=?1", nativeQuery = true)
	public Lookup getDescriptionID(String des);

	@Query(value = "select * from TBLSYSTEMSETTINGLOOKUP where ENTITYNAME=?1 and CODE=?2", nativeQuery = true)
	public Lookup findByCode(String e, String c);
	
}
