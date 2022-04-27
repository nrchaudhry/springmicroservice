package com.cwiztech.token;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.datalogs.repository.apiRequestDataLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AccessToken {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String apigateway;
	private static String applicationPathUM;

	@Autowired
	private static apiRequestDataLogRepository apirequestdatalogRepository;
	
	@Autowired
	public AccessToken(Environment env) {
		log.info("Trying to get Application Path..........");
		AccessToken.apigateway = env.getRequiredProperty("file_path.apigatewayPath");
		AccessToken.applicationPathUM = env.getRequiredProperty("file_path.applicationPathUM");
	}

	@SuppressWarnings("unused")
	public static String findToken(String apllication_ID, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {

		JSONObject jsonObjmain = new JSONObject();
		jsonObjmain.put("user_NAME", UserName);
		jsonObjmain.put("password", Password);
		jsonObjmain.put("code", apllication_ID);

		log.info("Application Code for Token: " + apllication_ID);

		String rtnToken = null, appPath = null;

		RestTemplate restTemplate = new RestTemplate();

		HttpEntity<String> entityformudel = new HttpEntity<String>(jsonObjmain.toString());
		ResponseEntity<String> application = restTemplate.exchange(applicationPathUM + "application/bycode", HttpMethod.POST,
				entityformudel, String.class);
		JSONObject applicationSetting = new JSONObject(application.getBody());
		if (applicationSetting != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", applicationSetting.getString("content_TYPE"));
			headers.add("Authorization", applicationSetting.getString("authorization_CODE"));
			appPath = applicationSetting.getString("applicationservice_PATH");
			log.info(applicationSetting.toString());
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			rtnToken = restTemplate.postForObject(
					appPath + "oauth/token?grant_type=password&username=" + UserName + "&password=" + Password, entity,
					String.class);
			JSONObject jsonFinalRtnObject = new JSONObject(rtnToken);
			jsonFinalRtnObject.put("application_ID", applicationSetting.getLong("application_ID"));
			jsonFinalRtnObject.put("applicationservice_PATH", applicationSetting.getString("applicationservice_PATH"));
			if (!applicationSetting.isNull("applicationpath_FRONTEND"))
				jsonFinalRtnObject.put("applicationpath_FRONTEND", applicationSetting.getString("applicationpath_FRONTEND"));
			else
				jsonFinalRtnObject.put("applicationpath_FRONTEND", "");
			if (!applicationSetting.isNull("applicationlogo_PATH"))
				jsonFinalRtnObject.put("applicationlogo_PATH", applicationSetting.getString("applicationlogo_PATH"));
			else
				jsonFinalRtnObject.put("applicationlogo_PATH", "");
			if (!applicationSetting.isNull("oauthservice_PATH"))
				jsonFinalRtnObject.put("oauthservice_PATH", applicationSetting.getString("oauthservice_PATH"));
			else
				jsonFinalRtnObject.put("oauthservice_PATH", "");
			if (!applicationSetting.isNull("headername"))
				jsonFinalRtnObject.put("HeaderName", applicationSetting.getString("headername"));
			else
				jsonFinalRtnObject.put("HeaderName", "");
			if (!applicationSetting.isNull("projecttitle"))
				jsonFinalRtnObject.put("ProjectTitle", applicationSetting.getString("projecttitle"));
			else
				jsonFinalRtnObject.put("ProjectTitle", "");
			if (!applicationSetting.isNull("copyrights_YEAR"))
				jsonFinalRtnObject.put("CopyRights", applicationSetting.getString("copyrights_YEAR"));
			else
				jsonFinalRtnObject.put("CopyRights", "");
			if (!applicationSetting.isNull("companylink"))
				jsonFinalRtnObject.put("CompanyLink", applicationSetting.getString("companylink"));
			else
				jsonFinalRtnObject.put("CompanyLink", "");
			if (!applicationSetting.isNull("companyname"))
				jsonFinalRtnObject.put("CompanyName", applicationSetting.getString("companyname"));
			else
				jsonFinalRtnObject.put("CompanyName", "");

			rtnToken = jsonFinalRtnObject.toString();
		} else {
			jsonObjmain.put("message", "Application does't exist");
			rtnToken = jsonObjmain.toString();
		}

		log.info("Output: " + rtnToken);
		log.info("--------------------------------------------------------");

		return rtnToken;
	}
	
	public static JSONObject checkToken(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("Check Toeken Detail By Token Service");
		log.info("Application Path: " + apigateway);
		log.info("accessToken: " + accessToken);
		log.info("----------------------------------------------------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject myobj = new JSONObject();
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		log.info(OauthToken);
		
		try {
			ResponseEntity<String> getToken = restTemplate.exchange(applicationPathUM + "oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
			myobj = new JSONObject(getToken.getBody().toString());
		} catch (Exception e) {
			myobj.put("error", "invalid_token");
			myobj.put("error_description", "Token was not recognised");
		}
		log.info("----------------------------------------------------------------------------------");
		
		return myobj;
	}

	public static JSONObject checkToken(String requestType, String requestURI, String requestBody, DatabaseTables databaseTableID, String workstation, String accessToken) throws JsonProcessingException {
		log.info("----------------------------------------------------------------------------------");
		log.info("Check Toeken Detail By Token Service");
		log.info("Application Path: " + apigateway);
		log.info("accessToken: " + accessToken);
		log.info("----------------------------------------------------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject myobj = new JSONObject();
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		log.info(OauthToken);
		
		try {
			ResponseEntity<String> getToken = restTemplate.exchange(applicationPathUM + "oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
			myobj = new JSONObject(getToken.getBody().toString());
		} catch (Exception e) {
			 APIRequestDataLog apiRequest = tableDataLogs.apiRequestDataLog("PUT", databaseTableID, (long) 0, "/salesorderdetail", requestBody, workstation);
			apiRequest = tableDataLogs.errorDataLog(apiRequest, "invalid_token", "Token was not recognised");
			apirequestdatalogRepository.saveAndFlush(apiRequest);
			
			myobj.put("error", "invalid_token");
			myobj.put("error_description", "Token was not recognised");
		}
		log.info("----------------------------------------------------------------------------------");
		
		return myobj;
	}

	public static HttpHeaders getHttpHeader(String accessToken) throws JsonProcessingException, JSONException, ParseException {
		JSONObject jsonObjtoken = null;
		try {
			jsonObjtoken = new JSONObject(findTokenAPIGateway());
		} catch (Exception e) {
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "bearer " + jsonObjtoken.getString("access_token"));
//		headers.add("Authorization", accessToken);
		headers.add("Grant_Type", "password");
		return headers;
	}

	public static String findTokenAPIGateway() throws JsonProcessingException, JSONException, ParseException {

		log.info("Process to get Token.......");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded");
		headers.add("Authorization", "Basic c2VydmljZTpzZWNyZXQ=");

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		log.info("Application Path: "+apigateway);
		String rtnToken = restTemplate.postForObject(
				apigateway + "oauth/token?grant_type=password&username=nouser&password=123Magna$%^", entity,
				String.class);
		log.info("Output: " + rtnToken);
		log.info("--------------------------------------------------------");

		return rtnToken;
	}
}
