package com.cwiztech.datalogs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DATABASETABLES")
public class DatabaseTables {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long DATABASETABLE_ID;

	@Column(name = "DATABASETABLE_NAME")
	private String DATABASETABLE_NAME;

	@Column(name = "DATABASETABLE_DESC")
	private String DATABASETABLE_DESC;

	public Long getDATABASETABLE_ID() {
		return DATABASETABLE_ID;
	}

	public void setDATABASETABLE_ID(Long dATABASETABLE_ID) {
		DATABASETABLE_ID = dATABASETABLE_ID;
	}

	public String getDATABASETABLE_NAME() {
		return DATABASETABLE_NAME;
	}

	public void setDATABASETABLE_NAME(String dATABASETABLE_NAME) {
		DATABASETABLE_NAME = dATABASETABLE_NAME;
	}

	public String getDATABASETABLE_DESC() {
		return DATABASETABLE_DESC;
	}

	public void setDATABASETABLE_DESC(String dATABASETABLE_DESC) {
		DATABASETABLE_DESC = dATABASETABLE_DESC;
	}

}
