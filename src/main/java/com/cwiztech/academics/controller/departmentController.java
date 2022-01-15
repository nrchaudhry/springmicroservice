package com.cwiztech.academics.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cwiztech.academics.model.Department;
import com.cwiztech.academics.repository.collegeRepository;
import com.cwiztech.academics.repository.departmentRepository;
import com.cwiztech.common.common;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.systemsetting.model.Lookup;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/department")
public class departmentController {
	private static final Logger log = LoggerFactory.getLogger(departmentController.class);

	@Autowired
	private collegeRepository collegerepository;

	@Autowired
	private departmentRepository departmentrepository;


	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;

	@Autowired
	private tableDataLogRepository tbldatalogrepository;

	@Autowired
	private databaseTablesRepository databasetablesrepository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("GET: /department");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/department", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/department", null,
				workstation);

		List<Department> department = departmentrepository.findActive();
		rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("GET: /department/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/department/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/department/all", null, workstation);
		
		List<Department> department = departmentrepository.findAll();
		
        rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity getOne(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("GET: /department/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/department/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/department/" + id, null, workstation);
		
		Department department = departmentrepository.findOne(id);
		
        rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	public ResponseEntity getByIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		log.info("POST: /department/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/department/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> department_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/department/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray departments = jsonObj.getJSONArray("departments");
		for (int i=0; i<departments.length(); i++) {
			department_IDS.add((Integer) departments.get(i));
		}
		List<Department> department = new ArrayList<Department>();
		if (departments.length()>0)
			department = departmentrepository.findByIDs(department_IDS);
		
		rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notin/ids", method = RequestMethod.POST)
	public ResponseEntity getByNotInIDs(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		log.info("POST: /department/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/department/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> department_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/department/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray departments = jsonObj.getJSONArray("departments");
		for (int i=0; i<departments.length(); i++) {
			department_IDS.add((Integer) departments.get(i));
		}
		List<Department> department = new ArrayList<Department>();
		if (departments.length()>0)
			department = departmentrepository.findByNotInIDs(department_IDS);
		
		rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity insert(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JSONException, ParseException, InterruptedException, IOException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;
		

		log.info("POST: /department");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/department", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/department", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		Department department = new Department();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/department", data,
				workstation);

		if (!jsonObj.has("department_CODE") || jsonObj.isNull("department_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Department", "Department code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		department.setDEPARTMENT_CODE(jsonObj.getString("department_CODE"));

		if (!jsonObj.has("department_NAME") || jsonObj.isNull("department_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Department", "Department name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		department.setDEPARTMENT_NAME(jsonObj.getString("department_NAME"));

		if (jsonObj.has("college_ID"))
			department.setCOLLEGE_ID(collegerepository.findOne(jsonObj.getLong("college_ID")));

		if (jsonObj.has("department_DESCRIPTION") && !jsonObj.isNull("department_DESCRIPTION"))
			department.setDEPARTMENT_DESCRIPTION(jsonObj.getString("department_DESCRIPTION"));

		department.setISACTIVE("Y");
		department.setMODIFIED_BY(requestUser);
		department.setMODIFIED_WORKSTATION(workstation);
		department.setMODIFIED_WHEN(dateFormat1.format(date));
		department = departmentrepository.saveAndFlush(department);
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(department.getDEPARTMENT_ID(), databaseTableID,
				requestUser, mapper.writeValueAsString(department)));

		rtn = mapper.writeValueAsString(department);
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity update(@PathVariable Long id, @RequestBody String data,
			@RequestHeader(value = "Authorization") String headToken)
			throws JSONException, ParseException, InterruptedException, IOException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("PUT: /department/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/department", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/department", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		Department department = departmentrepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/department", data,
				workstation);

		if (jsonObj.has("college_ID"))
			department.setCOLLEGE_ID(collegerepository.findOne(jsonObj.getLong("college_ID")));

		if (jsonObj.has("department_CODE") && !jsonObj.isNull("department_CODE"))
			department.setDEPARTMENT_CODE(jsonObj.getString("department_CODE"));

		if (jsonObj.has("department_NAME") && !jsonObj.isNull("department_NAME"))
			department.setDEPARTMENT_NAME(jsonObj.getString("department_NAME"));

		if (jsonObj.has("department_DESCRIPTION"))
			department.setDEPARTMENT_DESCRIPTION(jsonObj.getString("department_DESCRIPTION"));

		if (jsonObj.has("isactive"))
			department.setISACTIVE(jsonObj.getString("isactive"));
		department.setMODIFIED_BY(requestUser);
		department.setMODIFIED_WORKSTATION(workstation);
		department.setMODIFIED_WHEN(dateFormat1.format(date));
		department = departmentrepository.saveAndFlush(department);
		rtn = mapper.writeValueAsString(department);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(department.getDEPARTMENT_ID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity updateAll(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("PUT: /department");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/department", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/department", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<Department> departments = new ArrayList<Department>();

		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			Department  department  = new  Department ();
			long id=0; 
			
			if (jsonObj.has("department_ID")) {
				id = jsonObj.getLong("department_ID");
				if (id!=0)
					department  = departmentrepository.findOne(id);
			}
			
			if (id==0) {
				
				if (!jsonObj.has("college_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "department", "college_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("department_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "department", "department_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("department_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "department", "department_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("department_DESCRIPTION")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "department", "department_DESCRIPTION is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
			}

			if (id!=-1) {
				if (jsonObj.has("college_ID"))
					department.setCOLLEGE_ID(collegerepository.findOne(jsonObj.getLong("college_ID")));

				if (jsonObj.has("department_CODE") && !jsonObj.isNull("department_CODE"))
					department.setDEPARTMENT_CODE(jsonObj.getString("department_CODE"));

				if (jsonObj.has("department_NAME") && !jsonObj.isNull("department_NAME"))
					department.setDEPARTMENT_NAME(jsonObj.getString("department_NAME"));

				if (jsonObj.has("department_DESCRIPTION"))
					department.setDEPARTMENT_DESCRIPTION(jsonObj.getString("department_DESCRIPTION"));

					
					if (jsonObj.has("isactive"))
						department .setISACTIVE(jsonObj.getString("isactive"));
					else
						department .setISACTIVE("Y");

					department.setMODIFIED_BY(requestUser);
					department.setMODIFIED_WORKSTATION(workstation);
					department.setMODIFIED_WHEN(dateFormat1.format(date));
					department= departmentrepository.saveAndFlush(department );
					
					rtn = mapper.writeValueAsString(departments);
					tbldatalogrepository.saveAndFlush(
							tableDataLogs.TableSaveDataLog(department.getDEPARTMENT_ID(), databaseTableID, requestUser, rtn));

					departments.add(department );
				}
		
		}
		
		rtn = mapper.writeValueAsString(departments);
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");
		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("DELETE: /department/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/department", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/department", null,
				workstation);

		Department department = departmentrepository.findOne(id);

		departmentrepository.delete(department);
		rtn = mapper.writeValueAsString(department);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(department.getDEPARTMENT_ID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity remove(@PathVariable Long id, @RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("GET: /department/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/department" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/department" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		Department department = departmentrepository.findOne(id);

		department.setISACTIVE("N");
		department.setMODIFIED_BY(requestUser);
		department.setMODIFIED_WORKSTATION(workstation);
		department.setMODIFIED_WHEN(dateFormat1.format(date));
		department = departmentrepository.saveAndFlush(department);
		rtn = mapper.writeValueAsString(department);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(department.getDEPARTMENT_ID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity getBySearch(@RequestBody String data,
			@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String workstation = null;
		APIRequestDataLog apiRequest = null;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/department/search",
					data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		Long requestUser = checkTokenResponse.getLong("user_ID");

		return new ResponseEntity(BySearch(data, true, databaseTableID, apiRequest, requestUser), HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public ResponseEntity getAllBySearch(@RequestBody String data,
			@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String workstation = null;
		APIRequestDataLog apiRequest = null;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/department/search/all",
					data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		Long requestUser = checkTokenResponse.getLong("user_ID");

		return new ResponseEntity(BySearch(data, false, databaseTableID, apiRequest, requestUser), HttpStatus.OK);
	}

	public String BySearch(String data, boolean active, DatabaseTables databaseTableID, APIRequestDataLog apiRequest,
			Long requestUser) throws JsonProcessingException {
		String rtn, workstation = null;
		long department_ID=0;
		JSONObject jsonObj = new JSONObject(data);

		log.info("POST: department/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);
		
		if (common.isNumeric(jsonObj.getString("search"))) {
			department_ID = Long.parseLong(jsonObj.getString("search"));
		}


		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/department/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<Department> department = ((active == true)
				? departmentrepository.findBySearch("%" + jsonObj.getString("search") + "%",department_ID)
				: departmentrepository.findAllBySearch("%" + jsonObj.getString("search") + "%",department_ID));

		rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public ResponseEntity getByAdvancedSearch(@RequestBody String data,
			@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String workstation = null;
		APIRequestDataLog apiRequest = null;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/department/advancedsearch", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		Long requestUser = checkTokenResponse.getLong("user_ID");

		return new ResponseEntity(ByAdvancedSearch(data, true, databaseTableID, apiRequest, requestUser),
				HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public ResponseEntity getAllByAdvancedSearch(@RequestBody String data,
			@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String workstation = null;
		APIRequestDataLog apiRequest = null;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Department.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/department/advancedsearch/all", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		Long requestUser = checkTokenResponse.getLong("user_ID");

		return new ResponseEntity(ByAdvancedSearch(data, false, databaseTableID, apiRequest, requestUser),
				HttpStatus.OK);
	}

	public String ByAdvancedSearch(String data, boolean active, DatabaseTables databaseTableID,
			APIRequestDataLog apiRequest, Long requestUser) throws JsonProcessingException {
		String rtn, workstation = null;
		ObjectMapper mapper = new ObjectMapper();

		log.info("POST: department/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long university_ID = 0, college_ID = 0, collegetype_ID = 0;

		if (jsonObj.has("university_ID"))
			university_ID = jsonObj.getLong("university_ID");
		if (jsonObj.has("college_ID"))
			college_ID = jsonObj.getLong("college_ID");
		if (jsonObj.has("collegetype_ID"))
			collegetype_ID = jsonObj.getLong("collegetype_ID");

		List<Department> department = ((active == true)
				? departmentrepository.findByAdvancedSearch(university_ID,college_ID,collegetype_ID)
				: departmentrepository.findAllByAdvancedSearch(university_ID,college_ID,collegetype_ID));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/department/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(department);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
};
