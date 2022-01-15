package com.cwiztech.services;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class UserLoginService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String userloginService;

	public UserLoginService(Environment env) {
		UserLoginService.userloginService = env.getRequiredProperty("file_path.USERLOGINSERVICE");
	}

	private static HttpHeaders getHttpHeader(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("USER LOGIN Service");
		log.info("----------------------------------------------------------------------------------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "bearer " + accessToken);
		headers.add("Grant_Type", "password");
		return headers;
	}

	public static String GET(String URI, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(userloginService, UserName, Password));
		HttpHeaders headers = getHttpHeader(serviceToken.getString("access_token"));
		String appPath = serviceToken.getString("applicationservice_PATH");

		log.info("GET: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI,
				HttpMethod.GET, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String POST(String URI, String body, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(userloginService, UserName, Password));
		HttpHeaders headers = getHttpHeader(serviceToken.getString("access_token"));
		String appPath = serviceToken.getString("applicationservice_PATH");

		log.info("POST: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI,
				HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String PUT(String URI, String body, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(userloginService, UserName, Password));
		HttpHeaders headers = getHttpHeader(serviceToken.getString("access_token"));
		String appPath = serviceToken.getString("applicationservice_PATH");

		log.info("PUT: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI,
				HttpMethod.PUT, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String DELETE(String URI, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(userloginService, UserName, Password));
		HttpHeaders headers = getHttpHeader(serviceToken.getString("access_token"));
		String appPath = serviceToken.getString("applicationservice_PATH");

		log.info("DELETE: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI,
				HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}
}
