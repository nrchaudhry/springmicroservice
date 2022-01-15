package com.cwiztech.datalogs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "APIREQUESTDATALOG")
public class APIRequestDataLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long LOG_ID;

	@Column(name = "LOG_DATE")
	private String LOG_DATE;

	@OneToOne
	@JoinColumn(name = "DATABASETABLE_ID")
	private DatabaseTables DATABASETABLE_ID;

	@Column(name = "REQUEST_ID")
	private Long REQUEST_ID;

	@Column(name = "REQUEST_TYPE")
	private String REQUEST_TYPE;

	@Column(name = "REQUEST_PATH")
	private String REQUEST_PATH;

	@Column(name = "REQUEST_INPUT")
	private String REQUEST_INPUT;

	@Column(name = "REQUEST_OUTPUT")
	private String REQUEST_OUTPUT;

	@Column(name = "REQUEST_STATUS")
	private String REQUEST_STATUS;

	@Column(name = "LOG_WORKSTATION")
	private String LOG_WORKSTATION;

	public Long getLOG_ID() {
		return LOG_ID;
	}

	public void setLOG_ID(Long lOG_ID) {
		LOG_ID = lOG_ID;
	}

	public String getLOG_DATE() {
		return LOG_DATE;
	}

	public void setLOG_DATE(String lOG_DATE) {
		LOG_DATE = lOG_DATE;
	}

	public DatabaseTables getDATABASETABLE_ID() {
		return DATABASETABLE_ID;
	}

	public void setDATABASETABLE_ID(DatabaseTables dATABASETABLE_ID) {
		DATABASETABLE_ID = dATABASETABLE_ID;
	}

	public Long getREQUEST_ID() {
		return REQUEST_ID;
	}

	public void setREQUEST_ID(Long rEQUEST_ID) {
		REQUEST_ID = rEQUEST_ID;
	}

	public String getREQUEST_TYPE() {
		return REQUEST_TYPE;
	}

	public void setREQUEST_TYPE(String rEQUEST_TYPE) {
		REQUEST_TYPE = rEQUEST_TYPE;
	}

	public String getREQUEST_PATH() {
		return REQUEST_PATH;
	}

	public void setREQUEST_PATH(String rEQUEST_PATH) {
		REQUEST_PATH = rEQUEST_PATH;
	}

	public String getREQUEST_INPUT() {
		return REQUEST_INPUT;
	}
	public void setREQUEST_INPUT(String rEQUEST_INPUT) {
		REQUEST_INPUT = rEQUEST_INPUT;
	}

	public String getREQUEST_OUTPUT() {
		return REQUEST_OUTPUT;
	}

	public void setREQUEST_OUTPUT(String rEQUEST_OUTPUT) {
		REQUEST_OUTPUT = rEQUEST_OUTPUT;
	}

	public String getREQUEST_STATUS() {
		return REQUEST_STATUS;
	}

	public void setREQUEST_STATUS(String rEQUEST_STATUS) {
		REQUEST_STATUS = rEQUEST_STATUS;
	}

	public String getLOG_WORKSTATION() {
		return LOG_WORKSTATION;
	}

	public void setLOG_WORKSTATION(String lOG_WORKSTATION) {
		LOG_WORKSTATION = lOG_WORKSTATION;
	}

}
