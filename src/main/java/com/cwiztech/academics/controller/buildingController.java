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

import com.cwiztech.academics.model.Building;
import com.cwiztech.academics.repository.buildingRepository;
import com.cwiztech.academics.repository.campusRepository;
import com.cwiztech.common.common;
import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/building")
public class buildingController {
	private static final Logger log = LoggerFactory.getLogger(buildingController.class);

	@Autowired
	private buildingRepository buildingrepository;

	@Autowired
	private campusRepository campusrepository;

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

		log.info("GET: /building");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/building", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/building", null,
				workstation);

		List<Building> building = buildingrepository.findActive();
		rtn = mapper.writeValueAsString(building);

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

		log.info("GET: /building/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/building/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/building/all", null, workstation);
		
		List<Building> building = buildingrepository.findAll();
		
        rtn = mapper.writeValueAsString(building);

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

		log.info("GET: /building/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/building/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/building/" + id, null, workstation);
		
		Building building = buildingrepository.findOne(id);
		
        rtn = mapper.writeValueAsString(building);

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

		log.info("POST: /building/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/building/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> building_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/building/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray buildings = jsonObj.getJSONArray("buildings");
		for (int i=0; i<buildings.length(); i++) {
			building_IDS.add((Integer) buildings.get(i));
		}
		List<Building> building = new ArrayList<Building>();
		if (buildings.length()>0)
			building = buildingrepository.findByIDs(building_IDS);
		
		rtn = mapper.writeValueAsString(building);

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

		log.info("POST: /building/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/building/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> building_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/building/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray buildings = jsonObj.getJSONArray("buildings");
		for (int i=0; i<buildings.length(); i++) {
			building_IDS.add((Integer) buildings.get(i));
		}
		List<Building> building = new ArrayList<Building>();
		if (buildings.length()>0)
			building = buildingrepository.findByNotInIDs(building_IDS);
		
		rtn = mapper.writeValueAsString(building);

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
		

		log.info("POST: /building");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/building", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/building", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		Building building = new Building();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/building", data,
				workstation);

		if (!jsonObj.has("campus_ID")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Building", "Building campus is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("building_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Building", "Building code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("building_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Building", "Building name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		building.setCAMPUS_ID(campusrepository.findOne(jsonObj.getLong("campus_ID")));
		building.setBUILDING_CODE(jsonObj.getString("building_CODE"));
		building.setBUILDING_NAME(jsonObj.getString("building_NAME"));
		building.setISACTIVE("Y");
		building.setMODIFIED_BY(requestUser);
		building.setMODIFIED_WORKSTATION(workstation);
		building.setMODIFIED_WHEN(dateFormat1.format(date));
		building = buildingrepository.saveAndFlush(building);
		rtn = mapper.writeValueAsString(building);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(building.getBUILDING_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /building/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/building", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/building", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		Building building = buildingrepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/building", data,
				workstation);

		if (jsonObj.has("campus_ID") && !jsonObj.isNull("campus_ID"))
			building.setCAMPUS_ID(campusrepository.findOne(jsonObj.getLong("campus_ID")));

		if (jsonObj.has("building_CODE") && !jsonObj.isNull("building_CODE"))
			building.setBUILDING_CODE(jsonObj.getString("building_CODE"));

		if (jsonObj.has("building_NAME") && !jsonObj.isNull("building_NAME"))
			building.setBUILDING_NAME(jsonObj.getString("building_NAME"));
		if (jsonObj.has("isactive"))
			building.setISACTIVE(jsonObj.getString("isactive"));
		building.setMODIFIED_BY(requestUser);
		building.setMODIFIED_WORKSTATION(workstation);
		building.setMODIFIED_WHEN(dateFormat1.format(date));
		building = buildingrepository.saveAndFlush(building);
		rtn = mapper.writeValueAsString(building);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(building.getBUILDING_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /building");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/building", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/building", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<Building> buildings = new ArrayList<Building>();
		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			Building  building  = new  Building ();
			long id=0; 
			
			if (jsonObj.has("building_ID")) {
				id = jsonObj.getLong("building_ID");
				if (id!=0)
					building  = buildingrepository.findOne(id);
			}
			
			if (id==0) {
				
				if (!jsonObj.has("campus_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "building", "campus_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("building_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "building", "building_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("building_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "building", "building_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	
			}

			if (id!=-1) {
				if (jsonObj.has("campus_ID") && !jsonObj.isNull("campus_ID"))
					building.setCAMPUS_ID(campusrepository.findOne(jsonObj.getLong("campus_ID")));

				if (jsonObj.has("building_CODE") && !jsonObj.isNull("building_CODE"))
					building.setBUILDING_CODE(jsonObj.getString("building_CODE"));

				if (jsonObj.has("building_NAME") && !jsonObj.isNull("building_NAME"))
					building.setBUILDING_NAME(jsonObj.getString("building_NAME"));
					
					if (jsonObj.has("isactive"))
						building .setISACTIVE(jsonObj.getString("isactive"));
					else
						building .setISACTIVE("Y");

					building.setMODIFIED_BY(requestUser);
					building.setMODIFIED_WORKSTATION(workstation);
					building.setMODIFIED_WHEN(dateFormat1.format(date));
					building= buildingrepository.saveAndFlush(building );
					
					rtn = mapper.writeValueAsString(buildings);
					tbldatalogrepository.saveAndFlush(
							tableDataLogs.TableSaveDataLog(building.getBUILDING_ID(), databaseTableID, requestUser, rtn));

					buildings.add(building );
				}
		
		}
		
		rtn = mapper.writeValueAsString(buildings);
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

		log.info("DELETE: /building/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/building", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/building", null,
				workstation);

		Building building = buildingrepository.findOne(id);

		buildingrepository.delete(building);
		rtn = mapper.writeValueAsString(building);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(building.getBUILDING_ID(), databaseTableID, requestUser, rtn));

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

		log.info("GET: /building/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/building" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/building" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		Building building = buildingrepository.findOne(id);

		building.setISACTIVE("N");
		building.setMODIFIED_BY(requestUser);
		building.setMODIFIED_WORKSTATION(workstation);
		building.setMODIFIED_WHEN(dateFormat1.format(date));
		building = buildingrepository.saveAndFlush(building);
		rtn = mapper.writeValueAsString(building);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(building.getBUILDING_ID(), databaseTableID, requestUser, rtn));

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/building/search",
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/building/search/all",
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
		long campus_ID=0;
		JSONObject jsonObj = new JSONObject(data);

		log.info("POST: building/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);
		
		if (common.isNumeric(jsonObj.getString("search"))) {
			campus_ID = Long.parseLong(jsonObj.getString("search"));
		}

		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/building/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<Building> building = ((active == true)
				? buildingrepository.findBySearch("%" + jsonObj.getString("search") + "%",campus_ID)
				: buildingrepository.findAllBySearch("%" + jsonObj.getString("search") + "%",campus_ID));

		rtn = mapper.writeValueAsString(building);

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/building/advancedsearch", data, workstation);
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Building.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/building/advancedsearch/all", data, workstation);
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

		log.info("POST: building/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long university_ID = 0, campus_ID = 0;

		if (jsonObj.has("university_ID"))
			university_ID = jsonObj.getLong("university_ID");
		if (jsonObj.has("campus_ID"))
			campus_ID = jsonObj.getLong("campus_ID");

		List<Building> building = ((active == true)
				? buildingrepository.findByAdvancedSearch(university_ID,campus_ID)
				: buildingrepository.findAllByAdvancedSearch(university_ID,campus_ID));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/building/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(building);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

}
