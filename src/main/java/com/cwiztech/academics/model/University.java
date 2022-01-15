package com.cwiztech.academics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cwiztech.systemsetting.model.Lookup;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBLACADEMICSUNIVERSITY")
public class University {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long UNIVERSITY_ID;

	@Column(name = "UNIVERSITY_CODE")
	private String UNIVERSITY_CODE;

	@Column(name = "UNIVERSITY_NAME")
	private String UNIVERSITY_NAME;

	@Column(name = "UNIVERSITY_DESCRIPTION")
	private String UNIVERSITY_DESCRIPTION;

	@Column(name = "ADDRESS_LINE1")
	private String ADDRESS_LINE1;

	@Column(name = "ADDRESS_LINE2")
	private String ADDRESS_LINE2;

	@Column(name = "ADDRESS_LINE3")
	private String ADDRESS_LINE3;

	@Column(name = "ADDRESS_LINE4")
	private String ADDRESS_LINE4;

	@Column(name = "ADDRESS_LINE5")
	private String ADDRESS_LINE5;

	@Column(name = "ADDRESS_POSTCODE")
	private String ADDRESS_POSTCODE;

	@ManyToOne
	@JoinColumn(name = "ADDRESSCOUNTRY_ID")
	private Lookup ADDRESSCOUNTRY_ID;

	@Column(name = "TELEPHONE")
	private String TELEPHONE;

	@Column(name = "FAXNO")
	private String FAXNO;

	@Column(name = " EMAIL")
	private String EMAIL;

	@Column(name = "ISACTIVE")
	private String ISACTIVE;

	@Column(name = "LOGO_PATH")
	private String LOGO_PATH;

	@Column(name = "ICON_PATH")
	private String ICON_PATH;

	@JsonIgnore
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;

	@JsonIgnore
	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@JsonIgnore
	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getUNIVERSITY_ID() {
		return UNIVERSITY_ID;
	}

	public void setUNIVERSITY_ID(long uNIVERSITY_ID) {
		UNIVERSITY_ID = uNIVERSITY_ID;
	}

	public String getUNIVERSITY_CODE() {
		return UNIVERSITY_CODE;
	}

	public void setUNIVERSITY_CODE(String uNIVERSITY_CODE) {
		UNIVERSITY_CODE = uNIVERSITY_CODE;
	}

	public String getUNIVERSITY_NAME() {
		return UNIVERSITY_NAME;
	}

	public void setUNIVERSITY_NAME(String uNIVERSITY_NAME) {
		UNIVERSITY_NAME = uNIVERSITY_NAME;
	}

	public String getUNIVERSITY_DESCRIPTION() {
		return UNIVERSITY_DESCRIPTION;
	}

	public void setUNIVERSITY_DESCRIPTION(String uNIVERSITY_DESCRIPTION) {
		UNIVERSITY_DESCRIPTION = uNIVERSITY_DESCRIPTION;
	}

	public String getADDRESS_LINE1() {
		return ADDRESS_LINE1;
	}

	public void setADDRESS_LINE1(String aDDRESS_LINE1) {
		ADDRESS_LINE1 = aDDRESS_LINE1;
	}

	public String getADDRESS_LINE2() {
		return ADDRESS_LINE2;
	}

	public void setADDRESS_LINE2(String aDDRESS_LINE2) {
		ADDRESS_LINE2 = aDDRESS_LINE2;
	}

	public String getADDRESS_LINE3() {
		return ADDRESS_LINE3;
	}

	public void setADDRESS_LINE3(String aDDRESS_LINE3) {
		ADDRESS_LINE3 = aDDRESS_LINE3;
	}

	public String getADDRESS_LINE4() {
		return ADDRESS_LINE4;
	}

	public void setADDRESS_LINE4(String aDDRESS_LINE4) {
		ADDRESS_LINE4 = aDDRESS_LINE4;
	}

	public String getADDRESS_LINE5() {
		return ADDRESS_LINE5;
	}

	public void setADDRESS_LINE5(String aDDRESS_LINE5) {
		ADDRESS_LINE5 = aDDRESS_LINE5;
	}

	public String getADDRESS_POSTCODE() {
		return ADDRESS_POSTCODE;
	}

	public void setADDRESS_POSTCODE(String aDDRESS_POSTCODE) {
		ADDRESS_POSTCODE = aDDRESS_POSTCODE;
	}

	public Lookup getADDRESSCOUNTRY_ID() {
		return ADDRESSCOUNTRY_ID;
	}

	public void setADDRESSCOUNTRY_ID(Lookup aDDRESSCOUNTRY_ID) {
		ADDRESSCOUNTRY_ID = aDDRESSCOUNTRY_ID;
	}

	public String getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	public String getFAXNO() {
		return FAXNO;
	}

	public void setFAXNO(String fAXNO) {
		FAXNO = fAXNO;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(String iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public String getLOGO_PATH() {
		return LOGO_PATH;
	}

	public void setLOGO_PATH(String lOGO_PATH) {
		LOGO_PATH = lOGO_PATH;
	}

	public String getICON_PATH() {
		return ICON_PATH;
	}

	public void setICON_PATH(String iCON_PATH) {
		ICON_PATH = iCON_PATH;
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
		return (long) 27;
	}
}
