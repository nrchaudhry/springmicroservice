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
@Table(name = "TBLACADEMICSQUALIFICATION")
public class Qualification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long QUALIFICATION_ID;

	@ManyToOne
	@JoinColumn(name = "UNIVERSITY_ID")
	private University UNIVERSITY_ID;

	@Column(name = "QUALIFICATION_CODE")
	private String QUALIFICATION_CODE;

	@Column(name = "QUALIFICATION_NAME")
	private String QUALIFICATION_NAME;

	@Column(name = "QUALIFICATION_DESCRIPTION")
	private String QUALIFICATION_DESCRIPTION;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@JsonIgnore
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;
	
	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;
	
	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getQUALIFICATION_ID() {
		return QUALIFICATION_ID;
	}

	public void setQUALIFICATION_ID(long qUALIFICATION_ID) {
		QUALIFICATION_ID = qUALIFICATION_ID;
	}

	public University getUNIVERSITY_ID() {
		return UNIVERSITY_ID;
	}

	public void setUNIVERSITY_ID(University uNIVERSITY_ID) {
		UNIVERSITY_ID = uNIVERSITY_ID;
	}

	public String getQUALIFICATION_CODE() {
		return QUALIFICATION_CODE;
	}

	public void setQUALIFICATION_CODE(String qUALIFICATION_CODE) {
		QUALIFICATION_CODE = qUALIFICATION_CODE;
	}

	public String getQUALIFICATION_NAME() {
		return QUALIFICATION_NAME;
	}

	public void setQUALIFICATION_NAME(String qUALIFICATION_NAME) {
		QUALIFICATION_NAME = qUALIFICATION_NAME;
	}

	public String getQUALIFICATION_DESCRIPTION() {
		return QUALIFICATION_DESCRIPTION;
	}

	public void setQUALIFICATION_DESCRIPTION(String qUALIFICATION_DESCRIPTION) {
		QUALIFICATION_DESCRIPTION = qUALIFICATION_DESCRIPTION;
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
		return (long) 16;
	}

}