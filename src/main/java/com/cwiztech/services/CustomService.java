package com.cwiztech.services;

import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
	import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class CustomService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);

	private static HttpHeaders getHttpHeader(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("Custom Service");
		log.info("----------------------------------------------------------------------------------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "bearer " + accessToken);
		headers.add("Grant_Type", "password");
		return headers;
	}

	public static String GET(String customService, String URI, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(customService, UserName, Password));
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

	public static String POST(String customService, String URI, String body, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(customService, UserName, Password));
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

	public static String PUT(String customService, String URI, String body, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(customService, UserName, Password));
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

	public static String DELETE(String customService, String URI, String UserName, String Password)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(customService, UserName, Password));
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
