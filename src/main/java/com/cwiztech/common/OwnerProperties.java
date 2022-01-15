package com.cwiztech.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file_path")
public class OwnerProperties {

	private String applicationPath;
	private String employee;
	private String student;
	private String library;
	private String findAddressUKAPIPath;
	private String findAddressUKAPIKey;
	private String findDetailAddressUKAPIPath;

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getFindAddressUKAPIPath() {
		return findAddressUKAPIPath;
	}

	public void setFindAddressUKAPIPath(String findAddressUKAPIPath) {
		this.findAddressUKAPIPath = findAddressUKAPIPath;
	}

	public String getFindAddressUKAPIKey() {
		return findAddressUKAPIKey;
	}

	public void setFindAddressUKAPIKey(String findAddressUKAPIKey) {
		this.findAddressUKAPIKey = findAddressUKAPIKey;
	}

	public String getFindDetailAddressUKAPIPath() {
		return findDetailAddressUKAPIPath;
	}

	public void setFindDetailAddressUKAPIPath(String findDetailAddressUKAPIPath) {
		this.findDetailAddressUKAPIPath = findDetailAddressUKAPIPath;
	}
	

}
