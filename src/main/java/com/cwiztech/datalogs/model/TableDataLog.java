package com.cwiztech.datalogs.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TABLEDATALOG")
public class TableDataLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long TABLELOG_ID;

	@Column(name = "TABLELOG_DATE")
	private String TABLELOG_DATE;

	@Column(name = "TABLE_ID")
	private long TABLE_ID;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "DATABASETABLE_ID")
	private DatabaseTables DATABASETABLE_ID;

	@Column(name = "TABLE_DATA")
	private String TABLE_DATA;

	@Column(name = "TABLELOG_BY")
	private Long TABLELOG_BY;

	@Column(name = "ISDELETED")
	private String ISDELETED;

	public Long getTABLELOG_ID() {
		return TABLELOG_ID;
	}

	public void setTABLELOG_ID(Long tABLELOG_ID) {
		TABLELOG_ID = tABLELOG_ID;
	}

	public String getTABLELOG_DATE() {
		return TABLELOG_DATE;
	}

	public void setTABLELOG_DATE(String tABLELOG_DATE) {
		TABLELOG_DATE = tABLELOG_DATE;
	}

	public long getTABLE_ID() {
		return TABLE_ID;
	}

	public void setTABLE_ID(long tABLE_ID) {
		TABLE_ID = tABLE_ID;
	}

	public DatabaseTables getDATABASETABLE_ID() {
		return DATABASETABLE_ID;
	}

	public Long getTABLELOG_BY() {
		return TABLELOG_BY;
	}

	public void setTABLELOG_BY(Long tABLELOG_BY) {
		TABLELOG_BY = tABLELOG_BY;
	}

	public void setDATABASETABLE_ID(DatabaseTables dATABASETABLE_ID) {
		DATABASETABLE_ID = dATABASETABLE_ID;
	}

	public String getTABLE_DATA() {
		return TABLE_DATA;
	}

	public void setTABLE_DATA(String tABLE_DATA) {
		TABLE_DATA = tABLE_DATA;
	}

	public String getISDELETED() {
		return ISDELETED;
	}

	public void setISDELETED(String iSDELETED) {
		ISDELETED = iSDELETED;
	}
}
