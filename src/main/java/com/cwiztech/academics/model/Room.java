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
@Table(name = "TBLACADEMICSCAMPUSROOM")
public class Room{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ROOM_ID;
    

    @JoinColumn(name = "ROOMTYPE_ID")
	private long ROOMTYPE_ID;


	@ManyToOne
	@JoinColumn(name = "BUILDING_ID")
	private Building BUILDING_ID;
	
    @Column(name = "ROOM_CODE")
    private String ROOM_CODE;

    @Column(name = "ROOM_NAME")
    private String ROOM_NAME;

    @Column(name = "ROOM_FLOOR")
    private long ROOM_FLOOR;

    @Column(name = "ROOM_CAPACITY")
    private long ROOM_CAPACITY;

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

	public long getROOM_ID() {
		return ROOM_ID;
	}

	public void setROOM_ID(long rOOM_ID) {
		ROOM_ID = rOOM_ID;
	}

	

	public Long getROOMTYPE_ID() {
		return ROOMTYPE_ID;
	}

	public void setROOMTYPE_ID(Long rOOMTYPE_ID) {
		ROOMTYPE_ID = rOOMTYPE_ID;
	}

	public Building getBUILDING_ID() {
		return BUILDING_ID;
	}

	public void setBUILDING_ID(Building bUILDING_ID) {
		BUILDING_ID = bUILDING_ID;
	}

	

	public String getROOM_CODE() {
		return ROOM_CODE;
	}

	public void setROOM_CODE(String rOOM_CODE) {
		ROOM_CODE = rOOM_CODE;
	}

	public void setROOMTYPE_ID(long rOOMTYPE_ID) {
		ROOMTYPE_ID = rOOMTYPE_ID;
	}

	public String getROOM_NAME() {
		return ROOM_NAME;
	}

	public void setROOM_NAME(String rOOM_NAME) {
		ROOM_NAME = rOOM_NAME;
	}

	public long getROOM_FLOOR() {
		return ROOM_FLOOR;
	}

	public void setROOM_FLOOR(long rOOM_FLOOR) {
		ROOM_FLOOR = rOOM_FLOOR;
	}

	public long getROOM_CAPACITY() {
		return ROOM_CAPACITY;
	}

	public void setROOM_CAPACITY(long rOOM_CAPACITY) {
		ROOM_CAPACITY = rOOM_CAPACITY;
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
		return (long) 17;
	}

	
	
}