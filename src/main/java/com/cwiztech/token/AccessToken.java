package com.cwiztech.token;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.log.apiRequestLog;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AccessToken {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String oauthapplicationPath;
	public static long applicationID;
	public static String refreshToken;
	public static String oauthservicePath;

	@Autowired
	public AccessToken(Environment env) {
		log.info("Trying to get Application Path..........");
		AccessToken.oauthapplicationPath = env.getRequiredProperty("file_path.oauthapplicationPath");
	}

	public static HttpHeaders getHttpHeader(String accessToken, long LimitGrant) throws JsonProcessingException, JSONException, ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", accessToken);
		headers.add("Grant_Type", "password");
		headers.add("LimitGrant", ""+LimitGrant);
		return headers;
	}

	public static JSONObject checkToken(String requestType, String requestURI, String requestBody, String workstation, String accessToken) throws JsonProcessingException {
		log.info("----------------------------------------------------------------------------------");
		log.info("Check Toeken Detail By Token Service");
		log.info("Application Path: " + oauthapplicationPath);
		log.info("accessToken: " + accessToken);
		log.info("----------------------------------------------------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject checkTokenResponse = new JSONObject();
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		log.info(OauthToken);
		
		try {
			ResponseEntity<String> getToken = restTemplate.exchange(oauthapplicationPath + "oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
			checkTokenResponse = new JSONObject(getToken.getBody().toString());
		} catch (Exception e) {
			checkTokenResponse.put("error", "invalid_token");
			checkTokenResponse.put("error_description", "Token was not recognised");
		}
		log.info("----------------------------------------------------------------------------------");
		
		JSONObject apiRequest = new JSONObject();
		
		log.info(requestType + ": " + requestURI);
		if (requestBody != null)
			log.info("Input: " + requestBody);

		if (checkTokenResponse.has("error")) {
			apiRequest = apiRequestLog.apiRequestCreateLog(requestType, (long) 0, requestURI, requestBody, workstation);
			apiRequest = apiRequestLog.apiRequestErrorLog(apiRequest, "invalid_token", "Token was not recognised");
			return apiRequest ;
		}

		Long requestUser = (long) 0;
		if (accessToken != null && accessToken != "")
			requestUser = checkTokenResponse.getLong("user_ID");
		apiRequest = apiRequestLog.apiRequestCreateLog(requestType, requestUser, requestURI, requestBody, workstation);
		apiRequest.put("access_TOKEN", accessToken);
		if (workstation == null) {
			apiRequest.put("log_WORKSTATION", "Unknown");
			
		} else {
			apiRequest.put("log_WORKSTATION", workstation);
		}

		if (checkTokenResponse.has("employee_ID") && !checkTokenResponse.isNull("employee_ID"))
			apiRequest.put("employee_ID", checkTokenResponse.getLong("employee_ID"));

		return apiRequest;
	}


	public static String findServiceDetail(String serviceURI, String serviceMethod, HttpHeaders headers)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject jsonObjmain = new JSONObject();
		String service_PATH = "";
		jsonObjmain.put("microservice_URI", serviceURI);
		jsonObjmain.put("microservice_METHOD", serviceMethod);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entityformudel = new HttpEntity<String>(jsonObjmain.toString(), headers);
		ResponseEntity<String> service = restTemplate.exchange(oauthapplicationPath + "microservice/byuri", HttpMethod.POST, entityformudel, String.class);

		if (service != null) {
	        JSONObject serviceDetail = new JSONObject(service.getBody());
	        
	        log.info("Output: " + service.getBody());
	        log.info("--------------------------------------------------------");

	        service_PATH = serviceDetail.getString("microservice_PATH");
		}
		return service_PATH;
	}

	public static String findApplicationDetail(String apllication_ID, HttpHeaders headers)
			throws JsonProcessingException, JSONException, ParseException {
		JSONObject jsonObjmain = new JSONObject();
		jsonObjmain.put("code", apllication_ID);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entityformudel = new HttpEntity<String>(jsonObjmain.toString(), headers);
		ResponseEntity<String> application = restTemplate.exchange(oauthapplicationPath + "application/bycode", HttpMethod.POST, entityformudel, String.class);
		JSONObject applicationDetail = new JSONObject(application.getBody());
		
		log.info("Output: " + application.getBody());
		log.info("--------------------------------------------------------");

		applicationID = applicationDetail.getLong("application_ID");
		refreshToken = applicationDetail.getString("authorization_CODE");
		oauthservicePath = applicationDetail.getString("oauthservice_PATH");
		return applicationDetail.getString("applicationservice_PATH");
	}
}
