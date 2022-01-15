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

import com.cwiztech.academics.model.Campus;
import com.cwiztech.academics.model.College;
import com.cwiztech.academics.repository.collegeRepository;
import com.cwiztech.academics.repository.universityRepository;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.systemsetting.model.Lookup;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/college")
public class collegeController {
	private static final Logger log = LoggerFactory.getLogger(collegeController.class);

	@Autowired
	private universityRepository universityrepository;

	@Autowired
	private collegeRepository collegerepository;

	@Autowired
	private lookupRepository lookuprepository;

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

		log.info("GET: /college");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/college", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/college", null,
				workstation);

		List<College> college = collegerepository.findActive();
		rtn = mapper.writeValueAsString(college);

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

		log.info("GET: /college/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/college/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/college/all", null, workstation);
		
		List<College> college = collegerepository.findAll();
		
        rtn = mapper.writeValueAsString(college);

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

		log.info("GET: /college/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/college/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/college/" + id, null, workstation);
		
		College college = collegerepository.findOne(id);
		
        rtn = mapper.writeValueAsString(college);

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

		log.info("POST: /college/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/college/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> college_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/college/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray colleges = jsonObj.getJSONArray("colleges");
		for (int i=0; i<colleges.length(); i++) {
			college_IDS.add((Integer) colleges.get(i));
		}
		List<College> college = new ArrayList<College>();
		if (colleges.length()>0)
			college = collegerepository.findByIDs(college_IDS);
		
		rtn = mapper.writeValueAsString(college);

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

		log.info("POST: /college/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/college/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> college_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/college/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray colleges = jsonObj.getJSONArray("colleges");
		for (int i=0; i<colleges.length(); i++) {
			college_IDS.add((Integer) colleges.get(i));
		}
		List<College> college = new ArrayList<College>();
		if (colleges.length()>0)
			college = collegerepository.findByNotInIDs(college_IDS);
		
		rtn = mapper.writeValueAsString(college);

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
		

		log.info("POST: /college");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/college", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/college", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		College college = new College();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/college", data,
				workstation);

		if (!jsonObj.has("college_CODE") || jsonObj.isNull("college_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "College", "College code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		college.setCOLLEGE_CODE(jsonObj.getString("college_CODE"));

		if (!jsonObj.has("college_NAME") || jsonObj.isNull("college_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "College", "College name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		college.setCOLLEGE_NAME(jsonObj.getString("college_NAME"));

		if (jsonObj.has("university_ID"))
			college.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

		if (jsonObj.has("collegetype_ID"))
			college.setCOLLEGETYPE_ID(lookuprepository.findOne(jsonObj.getLong("collegetype_ID")));

		if (jsonObj.has("college_DESCRIPTION") && !jsonObj.isNull("college_DESCRIPTION"))
			college.setCOLLEGE_DESCRIPTION(jsonObj.getString("college_DESCRIPTION"));

		if (jsonObj.has("address_LINE1") && !jsonObj.isNull("address_LINE1"))
			college.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));
		if (jsonObj.has("address_LINE2") && !jsonObj.isNull("address_LINE2"))
			college.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));
		if (jsonObj.has("address_LINE3") && !jsonObj.isNull("address_LINE3"))
			college.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));
		if (jsonObj.has("address_LINE4") && !jsonObj.isNull("address_LINE4"))
			college.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));
		if (jsonObj.has("address_LINE5") && !jsonObj.isNull("address_LINE5"))
			college.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));
		if (jsonObj.has("address_POSTCODE") && !jsonObj.isNull("address_POSTCODE"))
			college.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (!jsonObj.has("addresscountry_ID") && !jsonObj.isNull("addresscountry_ID"))
			college.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone") && !jsonObj.isNull("telephone"))
			college.setTELEPHONE(jsonObj.getString("telephone"));
		if (jsonObj.has("faxno") && !jsonObj.isNull("faxno"))
			college.setFAXNO(jsonObj.getString("faxno"));
		if (jsonObj.has("email") && !jsonObj.isNull("email"))
			college.setEMAIL(jsonObj.getString("email"));
		college.setISACTIVE("Y");
		college.setMODIFIED_BY(requestUser);
		college.setMODIFIED_WORKSTATION(workstation);
		college.setMODIFIED_WHEN(dateFormat1.format(date));
		college = collegerepository.saveAndFlush(college);
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(college.getCOLLEGE_ID(), databaseTableID,
				requestUser, mapper.writeValueAsString(college)));

		rtn = mapper.writeValueAsString(college);
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

		log.info("PUT: /college/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/college", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/college", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		College college = collegerepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/college", data,
				workstation);

		if (jsonObj.has("university_ID"))
			college.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

		if (jsonObj.has("collegetype_ID"))
			college.setCOLLEGETYPE_ID(lookuprepository.findOne(jsonObj.getLong("collegetype_ID")));

		if (jsonObj.has("college_CODE") && !jsonObj.isNull("college_CODE"))
			college.setCOLLEGE_CODE(jsonObj.getString("college_CODE"));

		if (jsonObj.has("college_NAME") && !jsonObj.isNull("college_NAME"))
			college.setCOLLEGE_NAME(jsonObj.getString("college_NAME"));

		if (jsonObj.has("college_DESCRIPTION"))
			college.setCOLLEGE_DESCRIPTION(jsonObj.getString("college_DESCRIPTION"));

		if (jsonObj.has("address_LINE1"))
			college.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));

		if (jsonObj.has("address_LINE2"))
			college.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));

		if (jsonObj.has("address_LINE3"))
			college.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));

		if (jsonObj.has("address_LINE4"))
			college.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));

		if (jsonObj.has("address_LINE5"))
			college.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));

		if (jsonObj.has("address_POSTCODE"))
			college.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (jsonObj.has("addresscountry_ID"))
			college.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone"))
			college.setTELEPHONE(jsonObj.getString("telephone"));

		if (jsonObj.has("faxno"))
			college.setFAXNO(jsonObj.getString("faxno"));

		if (jsonObj.has("email"))
			college.setEMAIL(jsonObj.getString("email"));

		if (jsonObj.has("isactive"))
			college.setISACTIVE(jsonObj.getString("isactive"));
		college.setMODIFIED_BY(requestUser);
		college.setMODIFIED_WORKSTATION(workstation);
		college.setMODIFIED_WHEN(dateFormat1.format(date));
		college = collegerepository.saveAndFlush(college);
		rtn = mapper.writeValueAsString(college);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(college.getCOLLEGE_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /college");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/college", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/college", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<College> colleges = new ArrayList<College>();
		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			College  college  = new  College ();
			long id=0; 
			
			if (jsonObj.has("college_ID")) {
				id = jsonObj.getLong("college_ID");
				if (id!=0)
					college  = collegerepository.findOne(id);
			}
			
			if (id==0) {
				
				if (!jsonObj.has("university_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "university_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("collegetype_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "collegetype_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("college_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "college_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("college_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "college_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("college_DESCRIPTION")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "college_DESCRIPTION is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("address_LINE1")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_LINE1 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE2")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_LINE2 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE3")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_LINE3 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE4")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_LINE4 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE5")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_LINE5 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_POSTCODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "address_POSTCODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("addresscountry_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "addresscountry_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("telephone")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "telephone is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("faxno")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "faxno is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("email")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "college", "email is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
			}

			if (id!=-1) {
				if (jsonObj.has("university_ID"))
					college.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

				if (jsonObj.has("collegetype_ID"))
					college.setCOLLEGETYPE_ID(lookuprepository.findOne(jsonObj.getLong("collegetype_ID")));

				if (jsonObj.has("college_CODE") && !jsonObj.isNull("college_CODE"))
					college.setCOLLEGE_CODE(jsonObj.getString("college_CODE"));

				if (jsonObj.has("college_NAME") && !jsonObj.isNull("college_NAME"))
					college.setCOLLEGE_NAME(jsonObj.getString("college_NAME"));

				if (jsonObj.has("college_DESCRIPTION"))
					college.setCOLLEGE_DESCRIPTION(jsonObj.getString("college_DESCRIPTION"));

				if (jsonObj.has("address_LINE1"))
					college.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));

				if (jsonObj.has("address_LINE2"))
					college.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));

				if (jsonObj.has("address_LINE3"))
					college.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));

				if (jsonObj.has("address_LINE4"))
					college.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));

				if (jsonObj.has("address_LINE5"))
					college.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));

				if (jsonObj.has("address_POSTCODE"))
					college.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

				if (jsonObj.has("addresscountry_ID"))
					college.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

				if (jsonObj.has("telephone"))
					college.setTELEPHONE(jsonObj.getString("telephone"));

				if (jsonObj.has("faxno"))
					college.setFAXNO(jsonObj.getString("faxno"));

				if (jsonObj.has("email"))
					college.setEMAIL(jsonObj.getString("email"));
					
					if (jsonObj.has("isactive"))
						college .setISACTIVE(jsonObj.getString("isactive"));
					else
						college .setISACTIVE("Y");

					college.setMODIFIED_BY(requestUser);
					college.setMODIFIED_WORKSTATION(workstation);
					college.setMODIFIED_WHEN(dateFormat1.format(date));
					college= collegerepository.saveAndFlush(college );
					
					rtn = mapper.writeValueAsString(colleges);
					tbldatalogrepository.saveAndFlush(
							tableDataLogs.TableSaveDataLog(college.getCOLLEGE_ID(), databaseTableID, requestUser, rtn));

					colleges.add(college );
				}
		
		}
		
		rtn = mapper.writeValueAsString(colleges);
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

		log.info("DELETE: /college/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/college", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/college", null,
				workstation);

		College college = collegerepository.findOne(id);

		collegerepository.delete(college);
		rtn = mapper.writeValueAsString(college);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(college.getCOLLEGE_ID(), databaseTableID, requestUser, rtn));

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

		log.info("GET: /college/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/college" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/college" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		College college = collegerepository.findOne(id);

		college.setISACTIVE("N");
		college.setMODIFIED_BY(requestUser);
		college.setMODIFIED_WORKSTATION(workstation);
		college.setMODIFIED_WHEN(dateFormat1.format(date));
		college = collegerepository.saveAndFlush(college);
		rtn = mapper.writeValueAsString(college);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(college.getCOLLEGE_ID(), databaseTableID, requestUser, rtn));

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/college/search",
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/college/search/all",
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
		JSONObject jsonObj = new JSONObject(data);

		log.info("POST: college/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/college/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<College> college = ((active == true)
				? collegerepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: collegerepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		rtn = mapper.writeValueAsString(college);

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/college/advancedsearch", data, workstation);
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/college/advancedsearch/all", data, workstation);
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

		log.info("POST: college/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long university_ID = 0, collegetype_ID = 0;

		if (jsonObj.has("university_ID"))
			university_ID = jsonObj.getLong("university_ID");
		if (jsonObj.has("collegetype_ID"))
			collegetype_ID = jsonObj.getLong("collegetype_ID");

		List<College> college = ((active == true)
				? collegerepository.findByAdvancedSearch(university_ID,collegetype_ID)
				: collegerepository.findAllByAdvancedSearch(university_ID,collegetype_ID));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/college/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(college);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/openadmission", method = RequestMethod.GET)
	public ResponseEntity getuniversitylist(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("GET: /college/openadmission");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(College.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/college/openadmission", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/college/openadmission", null,
				workstation);

		List<College> college = collegerepository.findCollegeOpenAdmission();
		rtn = mapper.writeValueAsString(college);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}
};
