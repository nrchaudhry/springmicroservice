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

import com.cwiztech.academics.model.Room;
import com.cwiztech.academics.repository.buildingRepository;
import com.cwiztech.academics.repository.roomRepository;
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
@RequestMapping("/room")
public class roomController {
	private static final Logger log = LoggerFactory.getLogger(roomController.class);

	@Autowired
	private roomRepository roomrepository;

	@Autowired
	private buildingRepository buildingrepository;
	
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

		log.info("GET: /room");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/room", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/room", null,
				workstation);

		List<Room> room = roomrepository.findActive();
		rtn = mapper.writeValueAsString(room);

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

		log.info("GET: /room/all");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/room/all", null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/room/all", null, workstation);
		
		List<Room> room = roomrepository.findAll();
		
        rtn = mapper.writeValueAsString(room);

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

		log.info("GET: /room/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0, "/room/" + id, null, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/room/" + id, null, workstation);
		
		Room room = roomrepository.findOne(id);
		
        rtn = mapper.writeValueAsString(room);

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

		log.info("POST: /room/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/room/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> room_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/room/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray rooms = jsonObj.getJSONArray("rooms");
		for (int i=0; i<rooms.length(); i++) {
			room_IDS.add((Integer) rooms.get(i));
		}
		List<Room> room = new ArrayList<Room>();
		if (rooms.length()>0)
			room = roomrepository.findByIDs(room_IDS);
		
		rtn = mapper.writeValueAsString(room);

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

		log.info("POST: /room/notin/ids");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/room/ids", data, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		List<Integer> room_IDS = new ArrayList<Integer>(); 
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, 
				"/room/ids", data, workstation);
		
		JSONObject jsonObj = new JSONObject(data);
		JSONArray rooms = jsonObj.getJSONArray("rooms");
		for (int i=0; i<rooms.length(); i++) {
			room_IDS.add((Integer) rooms.get(i));
		}
		List<Room> room = new ArrayList<Room>();
		if (rooms.length()>0)
			room = roomrepository.findByNotInIDs(room_IDS);
		
		rtn = mapper.writeValueAsString(room);

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
		

		log.info("POST: /room");
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/room", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/room", data,
				workstation);

		JSONObject jsonObj = new JSONObject(data);

		Room room = new Room();

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/product", data,
				workstation);
		
		if (!jsonObj.has("roomtype_ID")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Roomtype_ID is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("building_ID")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Room building is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("room_CODE")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Room code is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("room_NAME")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Room name is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("room_CAPACITY")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Room capacity is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		if (!jsonObj.has("room_FLOOR")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Room", "Room floor is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		room.setBUILDING_ID(buildingrepository.findOne(jsonObj.getLong("building_ID")));

		room.setROOM_CODE(jsonObj.getString("room_CODE"));

		room.setROOMTYPE_ID(jsonObj.getLong("roomtype_ID"));
		room.setROOM_CODE(jsonObj.getString("room_CODE"));

		room.setROOM_NAME(jsonObj.getString("room_NAME"));
		room.setROOM_CAPACITY(jsonObj.getLong("room_CAPACITY"));
		room.setROOM_FLOOR(jsonObj.getLong("room_FLOOR"));
		room.setISACTIVE("Y");
		room.setMODIFIED_BY(requestUser);
		room.setMODIFIED_WORKSTATION(workstation);
		room.setMODIFIED_WHEN(dateFormat1.format(date));
		room = roomrepository.saveAndFlush(room);
		rtn = mapper.writeValueAsString(room);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(room.getROOM_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /room/" + id);
		log.info("Input: " + data);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/room", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/room", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date STARTDATE, ENDDATE;

		JSONObject jsonObj = new JSONObject(data);
		Room room = roomrepository.findOne(id);

		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/room", data,
				workstation);

		
		if (jsonObj.has("roomtype_ID") && !jsonObj.isNull("roomtype_ID"))
			room.setROOMTYPE_ID(jsonObj.getLong("roomtype_ID"));
		
		if (jsonObj.has("building_ID") && !jsonObj.isNull("building_ID"))
			room.setBUILDING_ID(buildingrepository.findOne(jsonObj.getLong("building_ID")));

		if (jsonObj.has("room_CODE") && !jsonObj.isNull("room_CODE"))
			room.setROOM_CODE(jsonObj.getString("room_CODE"));

		if (jsonObj.has("room_NAME") && !jsonObj.isNull("room_NAME"))
			room.setROOM_NAME(jsonObj.getString("room_NAME"));

		if (jsonObj.has("room_CAPACITY") && !jsonObj.isNull("room_CAPACITY"))
			room.setROOM_CAPACITY(jsonObj.getLong("room_CAPACITY"));

		if (jsonObj.has("room_FLOOR") && !jsonObj.isNull("room_FLOOR"))
			room.setROOM_FLOOR(jsonObj.getLong("room_FLOOR"));
		
		if (jsonObj.has("isactive"))
			room.setISACTIVE(jsonObj.getString("isactive"));
		room.setMODIFIED_BY(requestUser);
		room.setMODIFIED_WORKSTATION(workstation);
		room.setMODIFIED_WHEN(dateFormat1.format(date));
		room = roomrepository.saveAndFlush(room);
		rtn = mapper.writeValueAsString(room);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(room.getROOM_ID(), databaseTableID, requestUser, rtn));

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

		log.info("PUT: /room");
		log.info("Input: " + data);
		Date STARTDATE, ENDDATE;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/room", data,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/room", data,
				workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		JSONArray jsonPAV = new JSONArray(data);
		List<Room> rooms = new ArrayList<Room>();
		for (int i=0; i<jsonPAV.length(); i++) {
			JSONObject jsonObj = jsonPAV.getJSONObject(i);
			Room  room  = new  Room ();
			long id=0; 
			
			if (jsonObj.has("room _ID")) {
				id = jsonObj.getLong("room _ID");
				if (id!=0)
					room  = roomrepository.findOne(id);
			}
			
				if (id==0) {
				
				if (!jsonObj.has("roomtype_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "roomtype_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
				
				if (!jsonObj.has("building_ID")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "building_ID is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}	
				
				if (!jsonObj.has("room_CODE")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "room_CODE is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}			
	
				if (!jsonObj.has("room_NAME")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "room_NAME is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
	

				if (!jsonObj.has("room_CAPACITY")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "room_CAPACITY is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}

				if (!jsonObj.has("room_FLOOR")) {
					apiRequest = tableDataLogs.errorDataLog(apiRequest, "room", "room_FLOOR is missing");
					apirequestdatalogRepository.saveAndFlush(apiRequest);
					id = -1;
				}
			}

			if (id!=-1) {
				
	
				
				if (jsonObj.has("building_ID") && !jsonObj.isNull("building_ID"))
					room.setBUILDING_ID(buildingrepository.findOne(jsonObj.getLong("building_ID")));

				if (jsonObj.has("roomtype_ID") && !jsonObj.isNull("roomtype_ID"))
					room.setROOMTYPE_ID(jsonObj.getLong("roomtype_ID"));
				
				if (jsonObj.has("room_CODE") && !jsonObj.isNull("room_CODE"))
					room.setROOM_CODE(jsonObj.getString("room_CODE"));
				
				if (jsonObj.has("room_NAME") && !jsonObj.isNull("room_NAME"))
					room.setROOM_NAME(jsonObj.getString("room_NAME"));

				if (jsonObj.has("room_CAPACITY") && !jsonObj.isNull("room_CAPACITY"))
					room.setROOM_CAPACITY(jsonObj.getLong("room_CAPACITY"));

				if (jsonObj.has("room_FLOOR") && !jsonObj.isNull("room_FLOOR"))
					room.setROOM_FLOOR(jsonObj.getLong("room_FLOOR"));
					if (jsonObj.has("isactive"))
						room .setISACTIVE(jsonObj.getString("isactive"));
					else
						room .setISACTIVE("Y");

					room.setMODIFIED_BY(requestUser);
					room.setMODIFIED_WORKSTATION(workstation);
					room.setMODIFIED_WHEN(dateFormat1.format(date));
					room= roomrepository.saveAndFlush(room);
					
					rtn = mapper.writeValueAsString(rooms);
					tbldatalogrepository.saveAndFlush(
							tableDataLogs.TableSaveDataLog(room.getROOM_ID(), databaseTableID, requestUser, rtn));

					rooms.add(room );
				}
		
		}
		
		rtn = mapper.writeValueAsString(rooms);
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

		log.info("DELETE: /room/" + id);

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, (long) 0, "/room", null,
					workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/v", null,
				workstation);

		Room room = roomrepository.findOne(id);

		roomrepository.delete(room);
		rtn = mapper.writeValueAsString(room);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(room.getROOM_ID(), databaseTableID, requestUser, rtn));

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

		log.info("GET: /room/" + id + "/remove");

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());

		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, (long) 0,
					"/room" + id + "/remove", "", workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return new ResponseEntity(apiRequest.getREQUEST_OUTPUT(), HttpStatus.BAD_REQUEST);
		}

		ObjectMapper mapper = new ObjectMapper();
		Long requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/room" + id + "/remove", "", workstation);

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		Room room = roomrepository.findOne(id);

		room.setISACTIVE("N");
		room.setMODIFIED_BY(requestUser);
		room.setMODIFIED_WORKSTATION(workstation);
		room.setMODIFIED_WHEN(dateFormat1.format(date));
		room = roomrepository.saveAndFlush(room);
		rtn = mapper.writeValueAsString(room);
		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(room.getROOM_ID(), databaseTableID, requestUser, rtn));

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/room/search",
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, "/room/search/all",
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

		log.info("POST: room/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		ObjectMapper mapper = new ObjectMapper();
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/room/search" + ((active == true) ? "" : "/all"), null, workstation);

		List<Room> room = ((active == true)
				? roomrepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: roomrepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));

		rtn = mapper.writeValueAsString(room);

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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/room/advancedsearch", data, workstation);
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

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Room.getDatabaseTableID());
		if (checkTokenResponse.has("error")) {
			apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0,
					"/room/advancedsearch/all", data, workstation);
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

		log.info("POST: room/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long campus = 0, building = 0, university = 0;

		if (jsonObj.has("campus_ID"))
			campus = jsonObj.getLong("campus_ID");
		if (jsonObj.has("building_ID"))
			building = jsonObj.getLong("building_ID");
		if (jsonObj.has("university_ID"))
			university = jsonObj.getLong("university_ID");


		List<Room> room = ((active == true)
				? roomrepository.findByAdvancedSearch(campus, building, university)
				: roomrepository.findAllByAdvancedSearch(campus, building, university));

		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/room/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(room);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}
};
