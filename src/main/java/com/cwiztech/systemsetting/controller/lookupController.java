package com.cwiztech.systemsetting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.cwiztech.datalogs.repository.databaseTablesRepository;
import com.cwiztech.datalogs.repository.tableDataLogRepository;
import com.cwiztech.systemsetting.model.Lookup;
import com.cwiztech.systemsetting.repository.lookupRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin
@RequestMapping("/lookup")
public class lookupController{
	
	private static final Logger log = LoggerFactory.getLogger(lookupController.class);
	
	@Autowired
	private lookupRepository lookuprepository;
	
	@Autowired
	private apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	private tableDataLogRepository tbldatalogrepository;
	
	@Autowired
	private databaseTablesRepository databasetablesrepository;
		
	@RequestMapping(method = RequestMethod.GET)
	public String get(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /lookup");

		List<Lookup> lookup = lookuprepository.findActive();
		String rtn, workstation = null;
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/lookup", null,
				workstation);

		rtn = mapper.writeValueAsString(lookup);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getOne(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /lookup/" + id);

		Lookup lookup = lookuprepository.findOne(id);
		String rtn, workstation = null;

		Long requestUser;
		requestUser = (long) 0;
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser, "/lookup/" + id,
				null, workstation);

		rtn = mapper.writeValueAsString(lookup);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("GET: /lookup/all");

		List<Lookup> lookup = lookuprepository.findAll();
		String rtn, workstation = null;
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/lookup/all", null, workstation);

		rtn = mapper.writeValueAsString(lookup);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String insert(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		

		log.info("POST: /lookup");
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		Lookup lookup = new Lookup();
		String rtn, workstation = null;
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser, "/lookup", data,
				workstation);

		if (!jsonObj.has("entityname")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Entityname  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setENTITYNAME(jsonObj.getString("entityname"));
		
		if (!jsonObj.has("code")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Code  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setCODE(jsonObj.getString("code"));
		
		if (!jsonObj.has("description")) {
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "Lookup", "Description  is missing");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			return apiRequest.getREQUEST_OUTPUT();
		}
		lookup.setDESCRIPTION(jsonObj.getString("description"));
		
		if (jsonObj.has("entity_STATUS") && !jsonObj.isNull("entity_STATUS")) 
		lookup.setENTITY_STATUS(jsonObj.getString("entity_STATUS").toUpperCase());
		
		lookup.setISACTIVE("Y");
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtn = mapper.writeValueAsString(lookup);

		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, @RequestBody String data,@RequestHeader(value = "Authorization") String headToken)
			throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("PUT: /lookup/" + id);
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		Lookup lookup = lookuprepository.findOne(id);
		String rtn, workstation = null;
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		if (jsonObj.has("workstation"))
			workstation = jsonObj.getString("workstation");
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, requestUser, "/lookup", data,
				workstation);

		if (jsonObj.has("entityname") && !jsonObj.isNull("entityname"))
			lookup.setENTITYNAME(jsonObj.getString("entityname"));
		if (jsonObj.has("code") && !jsonObj.isNull("code"))
			lookup.setCODE(jsonObj.getString("code"));
		if (jsonObj.has("description") && !jsonObj.isNull("description")) 
			lookup.setDESCRIPTION(jsonObj.getString("description"));
		if (jsonObj.has("entity_STATUS")) 
			lookup.setENTITY_STATUS(jsonObj.getString("entity_STATUS"));
	
		if (jsonObj.has("isactive"))
			lookup.setISACTIVE(jsonObj.getString("isactive"));
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtn = mapper.writeValueAsString(lookup);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		log.info("DELETE: /lookup/" + id);

		Lookup lookup = lookuprepository.findOne(id);
		String rtn, workstation = null;

		Long requestUser;
		requestUser = (long) 0;
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("DELETE", databaseTableID, requestUser, "/lookup", null,
				workstation);

		lookuprepository.delete(lookup);
		rtn = mapper.writeValueAsString(lookup);

		tbldatalogrepository.saveAndFlush(
				tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String remove(@PathVariable Long id,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException, JSONException, ParseException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		ObjectMapper mapper = new ObjectMapper();
		
		log.info("GET: /lookup/" + id + "/remove");

		Lookup lookup = lookuprepository.findOne(id);
		String rtn, workstation = null;

		Long requestUser;
		requestUser = (long) 0;
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/lookup" + id + "/remove", "", workstation);
		lookup.setISACTIVE("N");
		lookup.setMODIFIED_BY(requestUser);
		lookup.setMODIFIED_WORKSTATION(workstation);
		lookup.setMODIFIED_WHEN(dateFormat1.format(date));
		lookup = lookuprepository.saveAndFlush(lookup);
		rtn = mapper.writeValueAsString(lookup);
		tbldatalogrepository
				.saveAndFlush(tableDataLogs.TableSaveDataLog(lookup.getID(), databaseTableID, requestUser, rtn));

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String getBySearch(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		return BySearch(data, true, headToken);

	}

	@RequestMapping(value = "/search/all", method = RequestMethod.POST)
	public String getAllBySearch(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		return BySearch(data, false, headToken);

	}

	public String BySearch(String data, boolean active, String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("POST: lookup/search" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		String rtn = null, workstation = null;

		List<Lookup> lookup = ((active == true)
				? lookuprepository.findBySearch("%" + jsonObj.getString("search") + "%")
				: lookuprepository.findAllBySearch("%" + jsonObj.getString("search") + "%"));
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/lookup/search" + ((active == true) ? "" : "/all"), null, workstation);

		rtn = mapper.writeValueAsString(lookup);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;

	}

	@RequestMapping(value = "/advancedsearch", method = RequestMethod.POST)
	public String getByAdvancedSearch(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		return ByAdvancedSearch(data, true, headToken);
	}

	@RequestMapping(value = "/advancedsearch/all", method = RequestMethod.POST)
	public String getAllByAdvancedSearch(@RequestBody String data,@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		return ByAdvancedSearch(data, false, headToken);
	}

	public String ByAdvancedSearch(String data, boolean active, String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("POST: lookup/advancedsearch" + ((active == true) ? "" : "/all"));
		log.info("Input: " + data);

		JSONObject jsonObj = new JSONObject(data);
		long lookup_ID = 0;

		if (jsonObj.has("lookup_ID"))
			lookup_ID = jsonObj.getLong("lookup_ID");

		List<Lookup> lookup = ((active == true)
				? lookuprepository.findByAdvancedSearch(lookup_ID)
				: lookuprepository.findAllByAdvancedSearch(lookup_ID));
		String rtn, workstation = null;

		Long requestUser;
		requestUser = (long) 0;
		
		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, requestUser,
				"/lookup/advancedsearch" + ((active == true) ? "" : "/all"), data, workstation);

		rtn = mapper.writeValueAsString(lookup);

		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);

		log.info("Output: " + rtn);
		log.info("--------------------------------------------------------");

		return rtn;
	}

	@RequestMapping(value="/entitylist",method=RequestMethod.GET)
	public String findEntityList(@RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		log.info("GET: /lookup/entitylist");
		
		List<Object> lookup =  lookuprepository.findEntityList();
		String rtn, workstation=null;
		
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/lookup/entitylist", null, workstation);
		
		rtn = mapper.writeValueAsString(lookup);
		
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtn);
		log.info("--------------------------------------------------------");
		
		return rtn;
	}
	
	@RequestMapping(value="/entity",method=RequestMethod.POST)
	public String findActiveByEntityName(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		log.info("POST: lookup/entity");
		log.info("Input: " + data);
		
		JSONObject jsonObj = new JSONObject(data);
		String entity=(String) jsonObj.get("entityname");
		List<Lookup> lookup =  lookuprepository.findActiveByEntityName(entity);

		String rtn, workstation = null;
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/lookup/entity", data, workstation);
		
		rtn = mapper.writeValueAsString(lookup);
		
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtn);
		log.info("--------------------------------------------------------");
		
		return rtn;
	}
	
	@RequestMapping(value="/entity/all",method=RequestMethod.POST)
	public String findAllByEntityName(@RequestBody String data, @RequestHeader(value = "Authorization") String headToken) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		log.info("POST: lookup/entity/all");
		log.info("Input: " + data);
		
		JSONObject jsonObj = new JSONObject(data);
		String entity=(String) jsonObj.get("entityname");

		String rtn, workstation = null;
		Long requestUser;
		requestUser = (long) 0;

		DatabaseTables databaseTableID = databasetablesrepository.findOne(Lookup.getDatabaseTableID());
		APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("GET", databaseTableID, requestUser,
				"/lookup/entity/all", data, workstation);
		
		List<Lookup> lookup =  lookuprepository.findAllByEntityName(entity);
		rtn = mapper.writeValueAsString(lookup);
		
		apiRequest.setREQUEST_OUTPUT(rtn);
		apiRequest.setREQUEST_STATUS("Success");
		apirequestdatalogRepository.saveAndFlush(apiRequest);
		
		log.info("Output: "+rtn);
		log.info("--------------------------------------------------------");
		
		return rtn;
	}
};
