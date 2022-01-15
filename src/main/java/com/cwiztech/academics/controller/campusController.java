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
import com.cwiztech.academics.repository.campusRepository;
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
@RequestMapping("/campus")
public class campusController {
	private static final Logger log = LoggerFactory.getLogger(campusController.class);

	@Autowired
	private lookupRepository lookuprepository;

	@Autowired
	private campusRepository campusrepository;

	@Autowired
	private universityRepository universityrepository;


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

		log.info("GET: /campus");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/campus", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/campus", null,
				workstation);

		List<Campus> campus = campusrepository.findActive();
		rtn = mapper.writeValueAsString(campus);

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

		log.info("GET: /campus/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/campus/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/campus/all", null, workstation);
		
		List<Campus> campus = campusrepository.findAll();
		
        rtn = mapper.writeValueAsString(campus);

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

		log.info("GET: /campus/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/campus/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/campus/" + id, null, workstation);
		
		Campus campus = campusrepository.findOne(id);
		
        rtn = mapper.writeValueAsString(campus);

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

		log.info("POST: /campus/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/campus/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> campus_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/campus/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray campuss = jsonObj.getJSONArray("campuss");
		for (int i=0; i<campuss.length(); i++) {
			campus_IDS.add((Integer) campuss.get(i));
		}
		List<Campus> campus = new ArrayList<Campus>();
		if (campuss.length()>0)
			campus = campusrepository.findByIDs(campus_IDS);
		
		rtn = mapper.writeValueAsString(campus);

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

		log.info("POST: /campus/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/campus/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> campus_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/campus/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray campuss = jsonObj.getJSONArray("campuss");
		for (int i=0; i<campuss.length(); i++) {
			campus_IDS.add((Integer) campuss.get(i));
		}
		List<Campus> campus = new ArrayList<Campus>();
		if (campuss.length()>0)
			campus = campusrepository.findByNotInIDs(campus_IDS);
		
		rtn = mapper.writeValueAsString(campus);

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
		

		log.info("POST: /campus");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/campus", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/campus", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		Campus campus = new Campus();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/campus", data,
				workstation);

		if (jsonObj.has("university_ID") && !jsonObj.isNull("university_ID"))
			campus.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

		if (!jsonObj.has("campus_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Campus", "Campus code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		campus.setCAMPUS_CODE(jsonObj.getString("campus_CODE"));

		if (!jsonObj.has("campus_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Campus", "Campus name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}
		campus.setCAMPUS_NAME(jsonObj.getString("campus_NAME"));

		if (jsonObj.has("campus_DESCRIPTION") && !jsonObj.isNull("campus_DESCRIPTION"))

			campus.setCAMPUS_DESCRIPTION(jsonObj.getString("campus_DESCRIPTION"));

		if (jsonObj.has("address_LINE1") && !jsonObj.isNull("address_LINE1"))
			campus.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));
		if (jsonObj.has("address_LINE2") && !jsonObj.isNull("address_LINE2"))
			campus.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));
		if (jsonObj.has("address_LINE3") && !jsonObj.isNull("address_LINE3"))
			campus.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));
		if (jsonObj.has("address_LINE4") && !jsonObj.isNull("address_LINE4"))
			campus.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));
		if (jsonObj.has("address_LINE5") && !jsonObj.isNull("address_LINE5"))
			campus.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));
		if (jsonObj.has("address_POSTCODE") && !jsonObj.isNull("address_POSTCODE"))
			campus.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (jsonObj.has("addresscountry_ID") && !jsonObj.isNull("addresscountry_ID"))
			campus.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone") && !jsonObj.isNull("telephone"))
			campus.setTELEPHONE(jsonObj.getString("telephone"));
		if (jsonObj.has("faxno") && !jsonObj.isNull("faxno"))
			campus.setFAXNO(jsonObj.getString("faxno"));
		if (jsonObj.has("email") && !jsonObj.isNull("email"))
			campus.setEMAIL(jsonObj.getString("email"));

		campus.setISACTIVE("Y");
		campus.setMODIFIED_BY(requestUser);
		campus.setMODIFIED_WORKSTATION(workstation);
		campus.setMODIFIED_WHEN(dateFormat1.format(date));
		campus = campusrepository.saveAndFlush(campus);
		tbldatalogrepository.saveAndFlush(tableDataLogs.TableSaveDataLog(campus.getCAMPUS_ID(), databaseTableID,
				requestUser, mapper.writeValueAsString(campus)));

		rtn = mapper.writeValueAsString(campus);
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

		log.info("PUT: /campus/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/campus", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/campus", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		Campus campus = campusrepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/campus", data,
				workstation);
		if (jsonObj.has("university_ID") && !jsonObj.isNull("university_ID"))
			campus.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

		if (jsonObj.has("campus_CODE") && !jsonObj.isNull("campus_CODE"))
			campus.setCAMPUS_CODE(jsonObj.getString("campus_CODE"));

		if (jsonObj.has("campus_NAME") && !jsonObj.isNull("campus_NAME"))
			campus.setCAMPUS_NAME(jsonObj.getString("campus_NAME"));

		if (jsonObj.has("campus_DESCRIPTION") && !jsonObj.isNull("campus_DESCRIPTION"))
			campus.setCAMPUS_DESCRIPTION(jsonObj.getString("campus_DESCRIPTION"));

		if (jsonObj.has("address_LINE1") && !jsonObj.isNull("address_LINE1"))
			campus.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));
		if (jsonObj.has("address_LINE2") && !jsonObj.isNull("address_LINE2"))
			campus.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));
		if (jsonObj.has("address_LINE3") && !jsonObj.isNull("address_LINE3"))
			campus.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));
		if (jsonObj.has("address_LINE4") && !jsonObj.isNull("address_LINE4"))
			campus.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));
		if (jsonObj.has("address_LINE5") && !jsonObj.isNull("address_LINE5"))
			campus.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));
		if (jsonObj.has("address_POSTCODE") && !jsonObj.isNull("address_POSTCODE"))
			campus.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

		if (jsonObj.has("addresscountry_ID") && !jsonObj.isNull("addresscountry_ID"))
			campus.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

		if (jsonObj.has("telephone") && !jsonObj.isNull("telephone"))
			campus.setTELEPHONE(jsonObj.getString("telephone"));
		if (jsonObj.has("faxno") && !jsonObj.isNull("faxno"))
			campus.setFAXNO(jsonObj.getString("faxno"));
		if (jsonObj.has("email") && !jsonObj.isNull("email"))
			campus.setEMAIL(jsonObj.getString("email"));

		if (jsonObj.has("isactive"))
			campus.setISACTIVE(jsonObj.getString("isactive"));
		campus.setMODIFIED_BY(requestUser);
		campus.setMODIFIED_WORKSTATION(workstation);
		campus.setMODIFIED_WHEN(dateFormat1.format(date));
		campus = campusrepository.saveAndFlush(campus);
		rtn = mapper.writeValueAsString(campus);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(campus.getCAMPUS_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /campus");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/campus", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/campus", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<Campus> campuss = new ArrayList<Campus>();

		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			Campus  campus  = new  Campus ();
			long id=0; 
			
			if (jsonObj.has("campus_ID")) {
				id = jsonObj.getLong("campus_ID");
				if (id!=0)
					campus  = campusrepository.findOne(id);
			}
			
			if (id==0) {
				
				if (!jsonObj.has("university_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "university_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("campus_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "campus_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("campus_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "campus_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("campus_DESCRIPTION")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "campus_DESCRIPTION is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("address_LINE1")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_LINE1 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE2")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_LINE2 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE3")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_LINE3 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE4")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_LINE4 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_LINE5")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_LINE5 is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("address_POSTCODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "address_POSTCODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("addresscountry_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "addresscountry_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("telephone")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "telephone is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("faxno")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "faxno is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				if (!jsonObj.has("email")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "campus", "email is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
			}

			if (id!=-1) {
				if (jsonObj.has("university_ID") && !jsonObj.isNull("university_ID"))
					campus.setUNIVERSITY_ID(universityrepository.findOne(jsonObj.getLong("university_ID")));

				if (jsonObj.has("campus_CODE") && !jsonObj.isNull("campus_CODE"))
					campus.setCAMPUS_CODE(jsonObj.getString("campus_CODE"));

				if (jsonObj.has("campus_NAME") && !jsonObj.isNull("campus_NAME"))
					campus.setCAMPUS_NAME(jsonObj.getString("campus_NAME"));

				if (jsonObj.has("campus_DESCRIPTION") && !jsonObj.isNull("campus_DESCRIPTION"))
					campus.setCAMPUS_DESCRIPTION(jsonObj.getString("campus_DESCRIPTION"));

				if (jsonObj.has("address_LINE1") && !jsonObj.isNull("address_LINE1"))
					campus.setADDRESS_LINE1(jsonObj.getString("address_LINE1"));
				if (jsonObj.has("address_LINE2") && !jsonObj.isNull("address_LINE2"))
					campus.setADDRESS_LINE2(jsonObj.getString("address_LINE2"));
				if (jsonObj.has("address_LINE3") && !jsonObj.isNull("address_LINE3"))
					campus.setADDRESS_LINE3(jsonObj.getString("address_LINE3"));
				if (jsonObj.has("address_LINE4") && !jsonObj.isNull("address_LINE4"))
					campus.setADDRESS_LINE4(jsonObj.getString("address_LINE4"));
				if (jsonObj.has("address_LINE5") && !jsonObj.isNull("address_LINE5"))
					campus.setADDRESS_LINE5(jsonObj.getString("address_LINE5"));
				if (jsonObj.has("address_POSTCODE") && !jsonObj.isNull("address_POSTCODE"))
					campus.setADDRESS_POSTCODE(jsonObj.getString("address_POSTCODE"));

				if (jsonObj.has("addresscountry_ID") && !jsonObj.isNull("addresscountry_ID"))
					campus.setADDRESSCOUNTRY_ID(lookuprepository.findOne(jsonObj.getLong("addresscountry_ID")));

				if (jsonObj.has("telephone") && !jsonObj.isNull("telephone"))
					campus.setTELEPHONE(jsonObj.getString("telephone"));
				if (jsonObj.has("faxno") && !jsonObj.isNull("faxno"))
					campus.setFAXNO(jsonObj.getString("faxno"));
				if (jsonObj.has("email") && !jsonObj.isNull("email"))
					campus.setEMAIL(jsonObj.getString("email"));
					
					if (jsonObj.has("isactive"))
						campus .setISACTIVE(jsonObj.getString("isactive"));
					else
						campus .setISACTIVE("Y");

					campus.setMODIFIED_BY(requestUser);
					campus.setMODIFIED_WORKSTATION(workstation);
					campus.setMODIFIED_WHEN(dateFormat1.format(date));
					campus= campusrepository.saveAndFlush(campus );
					
					rtn = mapper.writeValueAsString(campuss);
					tbldatalogrepository.saveAndFlush(
							tableDataLogs.TableSaveDataLog(campus.getCAMPUS_ID(), databaseTableID, requestUser, rtn));

					campuss.add(campus );
				}
		
		}
		
		rtn = mapper.writeValueAsString(campuss);
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

		log.info("DELETE: /campus/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/campus", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/campus", null,
				workstation);

		Campus campus = campusrepository.findOne(id);

		campusrepository.delete(campus);
		rtn = mapper.writeValueAsString(campus);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(campus.getCAMPUS_ID(), databaseTableID, requestUser, rtn));

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

		log.info("GET: /campus/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/campus" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/campus" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		Campus campus = campusrepository.findOne(id);

		campus.setISACTIVE("N");
		campus.setMODIFIED_BY(requestUser);
		campus.setMODIFIED_WORKSTATION(workstation);
		campus.setMODIFIED_WHEN(dateFormat1.format(date));
		campus = campusrepository.saveAndFlush(campus);
		rtn = mapper.writeValueAsString(campus);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(campus.getCAMPUS_ID(), databaseTableID, requestUser, rtn));

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/campus/search",
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/campus/search/all",
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

		log.info("POST: campus/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/campus/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<Campus> campus = ((active == true)
				? campusrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: campusrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		rtn = mapper.writeValueAsString(campus);

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/campus/advancedsearch", data, workstation);
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/campus/advancedsearch/all", data, workstation);
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

		log.info("POST: campus/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long university_ID = 0;

		if (jsonObj.has("university_ID"))
			university_ID = jsonObj.getLong("university_ID");


		List<Campus> campus = ((active == true)
				? campusrepository.findByAdvancedSearch(university_ID)
				: campusrepository.findAllByAdvancedSearch(university_ID));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/campus/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(campus);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/openadmission", method = RequestMethod.POST)
	public ResponseEntity getcoursecampuslist(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		JSONObject checkTokenResponse = AccessToken.checkToken(headToken);
		String rtn, workstation = null;
		APIRequestDataLog apiRequest;

		log.info("POST: /campus/openadmission");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		Long cID = jsonObj.getLong("course_ID");
		Long cmID = jsonObj.getLong("coursemode_ID");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Campus.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/campus/openadmission", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/campus/openadmission", null,
				workstation);

		List<Campus> campus = campusrepository.findCampusByCourseModeForAdmission(cID, cmID);
		rtn = mapper.writeValueAsString(campus);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return new ResponseEntity(rtn, HttpStatus.OK);
	}
};
