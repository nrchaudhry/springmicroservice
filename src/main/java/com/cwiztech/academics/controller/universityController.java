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

import com.cwiztech.academics.model.University;
import com.cwiztech.academics.repository.universityRepository;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/university")
public class universityController {
	private static final Logger log = LoggerFactory.getLogger(universityController.class);

	@Autowired
	private universityRepository universityrepository;

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

		log.info("GET: /university");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/university", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/university", null,
				workstation);

		List<University> university = universityrepository.findActive();
		rtn = mapper.writeValueAsString(university);

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

		log.info("GET: /university/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/university/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/university/all", null, workstation);
		
		List<University> university = universityrepository.findAll();
		
        rtn = mapper.writeValueAsString(university);

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

		log.info("GET: /university/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/university/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/university/" + id, null, workstation);
		
		University university = universityrepository.findOne(id);
		
        rtn = mapper.writeValueAsString(university);

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

		log.info("POST: /university/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/university/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> university_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/university/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray universitys = jsonObj.getJSONArray("universitys");
		for (int i=0; i<universitys.length(); i++) {
			university_IDS.add((Integer) universitys.get(i));
		}
		List<University> university = new ArrayList<University>();
		if (universitys.length()>0)
			university = universityrepository.findByIDs(university_IDS);
		
		rtn = mapper.writeValueAsString(university);

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

		log.info("POST: /university/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/university/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> university_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/university/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray universitys = jsonObj.getJSONArray("universitys");
		for (int i=0; i<universitys.length(); i++) {
			university_IDS.add((Integer) universitys.get(i));
		}
		List<University> university = new ArrayList<University>();
		if (universitys.length()>0)
			university = universityrepository.findByNotInIDs(university_IDS);
		
		rtn = mapper.writeValueAsString(university);

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
		

		log.info("POST: /university");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/university", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/university", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		University university = new University();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/university", data,
				workstation);

		if (!jsonObj.has("university_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "university code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		university.setUNIVERSITY_CODE(jsonObj.getString("university_CODE"));

		if (!jsonObj.has("university_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "university name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		university.setUNIVERSITY_NAME(jsonObj.getString("university_NAME"));

		if (jsonObj.has("university_DESCRIPTION") && !jsonObj.isNull("university_DESCRIPTION"))
			university.setUNIVERSITY_DESCRIPTION(jsonObj.getString("university_DESCRIPTION"));

		if (jsonObj.has("address_LINE1") && !jsonObj.isNull("address_LINE1"))
			university.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));

		if (jsonObj.has("address_LINE2") && !jsonObj.isNull("address_LINE2"))
			university.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));

		if (jsonObj.has("address_LINE3") && !jsonObj.isNull("address_LINE3"))
			university.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));

		if (jsonObj.has("address_LINE4") && !jsonObj.isNull("address_LINE4"))
			university.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));

		if (jsonObj.has("address_LINE5") && !jsonObj.isNull("address_LINE5"))
			university.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));

		if (jsonObj.has("address_POSTCODE") && !jsonObj.isNull("address_POSTCODE"))
			university.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (!jsonObj.has("addresscountry_ID") && !jsonObj.isNull("addresscountry_ID"))
			university.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone") && !jsonObj.isNull("telephone"))
			university.setTELEPHONE(jsonObj.getString("telephone"));

		if (jsonObj.has("faxno") && !jsonObj.isNull("faxno"))
			university.setFAXNO(jsonObj.getString("faxno"));

		if (jsonObj.has("email") && !jsonObj.isNull("email"))
			university.setEMAIL(jsonObj.getString("email"));

		if (jsonObj.has("logo_PATH") && !jsonObj.isNull("logo_PATH"))
			university.setLOGO_PATH(jsonObj.getString("logo_PATH"));

		if (jsonObj.has("icon_PATH") && !jsonObj.isNull("icon_PATH"))
			university.setICON_PATH(jsonObj.getString("icon_PATH"));

		university.setISACTIVE("Y");
		university.setMODIFIED_BY(requestUser);
		university.setMODIFIED_WORKSTATION(workstation);
		university.setMODIFIED_WHEN(dateFormat1.format(date));
		university = universityrepository.saveAndFlush(university);
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(university.getUNIVERSITY_ID(), databaseTableID,
				requestUser, mapper.writeValueAsString(university)));

		rtn = mapper.writeValueAsString(university);
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

		log.info("PUT: /university/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/university", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/university", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		University university = universityrepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/university", data,
				workstation);

		if (jsonObj.has("university_CODE") && !jsonObj.isNull("university_CODE"))
			university.setUNIVERSITY_CODE(jsonObj.getString("university_CODE"));

		if (jsonObj.has("university_NAME") && !jsonObj.isNull("university_NAME"))
			university.setUNIVERSITY_NAME(jsonObj.getString("university_NAME"));

		if (jsonObj.has("university_DESCRIPTION"))
			university.setUNIVERSITY_DESCRIPTION(jsonObj.getString("university_DESCRIPTION"));

		if (jsonObj.has("address_LINE1"))
			university.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));

		if (jsonObj.has("address_LINE2"))
			university.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));

		if (jsonObj.has("address_LINE3"))
			university.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));

		if (jsonObj.has("address_LINE4"))
			university.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));

		if (jsonObj.has("address_LINE5"))
			university.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));

		if (jsonObj.has("address_POSTCODE"))
			university.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (jsonObj.has("addresscountry_ID"))
			university.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone"))
			university.setTELEPHONE(jsonObj.getString("telephone"));

		if (jsonObj.has("faxno"))
			university.setFAXNO(jsonObj.getString("faxno"));

		if (jsonObj.has("email"))
			university.setEMAIL(jsonObj.getString("email"));

		if (jsonObj.has("logo_PATH"))
			university.setLOGO_PATH(jsonObj.getString("logo_PATH"));

		if (jsonObj.has("icon_PATH"))
			university.setICON_PATH(jsonObj.getString("icon_PATH"));

		if (jsonObj.has("isactive"))
			university.setISACTIVE(jsonObj.getString("isactive"));
		
		university.setMODIFIED_BY(requestUser);
		university.setMODIFIED_WORKSTATION(workstation);
		university.setMODIFIED_WHEN(dateFormat1.format(date));
		university = universityrepository.saveAndFlush(university);
		rtn = mapper.writeValueAsString(university);

		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(university.getUNIVERSITY_ID(), databaseTableID,
				requestUser, rtn));

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

		log.info("PUT: /university");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/university", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/university", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<University> universitys = new ArrayList<University>();
		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			University university = new  University();
			long id=0; 
			
			if (jsonObj.has("university_ID")) {
				id = jsonObj.getLong("university_ID");
				if (id!=0)
					university = universityrepository.findOne(id);
			}
			
			if (id==0) {
				if (!jsonObj.has("university_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "university_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("university_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "university_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
				if (!jsonObj.has("university_DESCRIPTION")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "university_DESCRIPTION is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
				if (!jsonObj.has("address_LINE1")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_LINE1 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
				if (!jsonObj.has("address_LINE2")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_LINE2 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE3")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_LINE3 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE4")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_LINE4 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE5")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_LINE5 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_POSTCODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "address_POSTCODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("addresscountry_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "addresscountry_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("faxno")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "faxno is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("email")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "email is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("logo_PATH")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "logo_PATH is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("icon_PATH")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "university", "icon_PATH is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				
			}

			if (id!=-1) {
				if (jsonObj.has("university_CODE") && !jsonObj.isNull("university_CODE"))
					university.setUNIVERSITY_CODE(jsonObj.getString("university_CODE"));

				if (jsonObj.has("university_NAME") && !jsonObj.isNull("university_NAME"))
					university.setUNIVERSITY_NAME(jsonObj.getString("university_NAME"));

				if (jsonObj.has("university_DESCRIPTION"))
					university.setUNIVERSITY_DESCRIPTION(jsonObj.getString("university_DESCRIPTION"));

				if (jsonObj.has("address_LINE1"))
					university.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));

				if (jsonObj.has("address_LINE2"))
					university.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));

				if (jsonObj.has("address_LINE3"))
					university.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));

				if (jsonObj.has("address_LINE4"))
					university.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));

				if (jsonObj.has("address_LINE5"))
					university.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));

				if (jsonObj.has("address_POSTCODE"))
					university.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

				if (jsonObj.has("addresscountry_ID"))
					university.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

				if (jsonObj.has("telephone"))
					university.setTELEPHONE(jsonObj.getString("telephone"));

				if (jsonObj.has("faxno"))
					university.setFAXNO(jsonObj.getString("faxno"));

				if (jsonObj.has("email"))
					university.setEMAIL(jsonObj.getString("email"));

				if (jsonObj.has("logo_PATH"))
					university.setLOGO_PATH(jsonObj.getString("logo_PATH"));

				if (jsonObj.has("icon_PATH"))
					university.setICON_PATH(jsonObj.getString("icon_PATH"));

	
				    if (jsonObj.has("isactive"))
					university.setISACTIVE(jsonObj.getString("isactive"));
				else
					university.setISACTIVE("Y");

				university.setMODIFIED_BY(requestUser);
				university.setMODIFIED_WORKSTATION(workstation);
				university.setMODIFIED_WHEN(dateFormat1.format(date));
				university = universityrepository.saveAndFlush(university);
				
				rtn = mapper.writeValueAsString(universitys);
				tbldatalogrepository.saveAndFlush(
						tableDataLogs.TableSaveDataLog(university.getUNIVERSITY_ID(), databaseTableID, requestUser, rtn));

				universitys.add(university);
			}
			
		}
		
		rtn = mapper.writeValueAsString(universitys);

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

		log.info("DELETE: /university/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/university", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/university", null,
				workstation);

		University university = universityrepository.findOne(id);

		universityrepository.delete(university);
		rtn = mapper.writeValueAsString(university);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(university.getUNIVERSITY_ID(), databaseTableID, requestUser, rtn));

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

		log.info("GET: /university/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/university" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/university" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		University university = universityrepository.findOne(id);

		university.setISACTIVE("N");
		university.setMODIFIED_BY(requestUser);
		university.setMODIFIED_WORKSTATION(workstation);
		university.setMODIFIED_WHEN(dateFormat1.format(date));
		university = universityrepository.saveAndFlush(university);
		rtn = mapper.writeValueAsString(university);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(university.getUNIVERSITY_ID(), databaseTableID, requestUser, rtn));

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/university/search",
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/university/search/all",
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

		log.info("POST: university/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/university/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<University> university = ((active == true)
				? universityrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: universityrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		rtn = mapper.writeValueAsString(university);

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/university/advancedsearch", data, workstation);
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(University.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/university/advancedsearch/all", data, workstation);
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

		log.info("POST: university/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long country_ID=0;

		if (jsonObj.has("country_ID"))
			country_ID = jsonObj.getLong("country_ID");

		List<University> university = ((active == true)
				? universityrepository.findByAdvancedSearch(country_ID)
				: universityrepository.findAllByAdvancedSearch(country_ID));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/university/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(university);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
};
