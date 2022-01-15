package com.cwiztech.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLACADEMICSCAMPUSBUILDING")
public class Building {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long BUILDING_ID;

	@ManyToOne // (cascade = { CascadeType.ALL })
	@JoinColumn(name = "CAMPUS_ID")
	private Campus CAMPUS_ID;

	@Column(name = "BUILDING_CODE")
	private String BUILDING_CODE;

	@Column(name = "BUILDING_NAME")
	private String BUILDING_NAME;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;

	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getBUILDING_ID() {
		return BUILDING_ID;
	}

	public void setBUILDING_ID(long bUILDING_ID) {
		BUILDING_ID = bUILDING_ID;
	}

	public Campus getCAMPUS_ID() {
		return CAMPUS_ID;
	}

	public void setCAMPUS_ID(Campus cAMPUS_ID) {
		CAMPUS_ID = cAMPUS_ID;
	}

	public String getBUILDING_CODE() {
		return BUILDING_CODE;
	}

	public void setBUILDING_CODE(String bUILDING_CODE) {
		BUILDING_CODE = bUILDING_CODE;
	}

	public String getBUILDING_NAME() {
		return BUILDING_NAME;
	}

	public void setBUILDING_NAME(String bUILDING_NAME) {
		BUILDING_NAME = bUILDING_NAME;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
	
    @JsonIgnore
	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}
	@JsonIgnore
	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	@JsonIgnore
	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	public static long getDatabaseTableID() {
		return (long) 3;
	}

}