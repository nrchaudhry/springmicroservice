package com.cwiztech.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLACADEMICSDEPARTMENT")
public class Department{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long DEPARTMENT_ID;
    
    @OneToOne
	@JoinColumn(name = "COLLEGE_ID")
	private College COLLEGE_ID;

    @Column(name = "DEPARTMENT_CODE")
    private String DEPARTMENT_CODE;

    @Column(name = "DEPARTMENT_NAME")
    private String DEPARTMENT_NAME;

    @Column(name = "DEPARTMENT_DESCRIPTION")
    private String DEPARTMENT_DESCRIPTION;

	@Column(name="ISACTIVE")
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

	public long getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(long dEPARTMENT_ID) {
		DEPARTMENT_ID = dEPARTMENT_ID;
	}

	public College getCOLLEGE_ID() {
		return COLLEGE_ID;
	}

	public void setCOLLEGE_ID(College cOLLEGE_ID) {
		COLLEGE_ID = cOLLEGE_ID;
	}

	public String getDEPARTMENT_CODE() {
		return DEPARTMENT_CODE;
	}

	public void setDEPARTMENT_CODE(String dEPARTMENT_CODE) {
		DEPARTMENT_CODE = dEPARTMENT_CODE;
	}

	public String getDEPARTMENT_NAME() {
		return DEPARTMENT_NAME;
	}

	public void setDEPARTMENT_NAME(String dEPARTMENT_NAME) {
		DEPARTMENT_NAME = dEPARTMENT_NAME;
	}

	public String getDEPARTMENT_DESCRIPTION() {
		return DEPARTMENT_DESCRIPTION;
	}

	public void setDEPARTMENT_DESCRIPTION(String dEPARTMENT_DESCRIPTION) {
		DEPARTMENT_DESCRIPTION = dEPARTMENT_DESCRIPTION;
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
		return (long) 10;
	}

}