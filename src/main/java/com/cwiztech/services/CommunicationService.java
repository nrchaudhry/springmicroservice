package com.cwiztech.services;

import java.text.ParseException;

import org.json.JSONArray;
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
public class CommunicationService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String communicationService;

	public CommunicationService(Environment env) {
		CommunicationService.communicationService = env.getRequiredProperty("file_path.COMMUNICATIONSERVICE");
	}

	private static HttpHeaders getHttpHeader(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("COMMUNICATION Service");
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

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(communicationService, UserName, Password));
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

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(communicationService, UserName, Password));
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

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(communicationService, UserName, Password));
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

		JSONObject serviceToken = new JSONObject(AccessToken.findToken(communicationService, UserName, Password));
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


	public static String GenerateAndSendLetter(String letterCode, long applicationID, Long loginuser, boolean sendEmail, Long emailUser)
			throws JsonProcessingException, JSONException, ParseException {
		String letterBody = null;

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("letter_CODE", letterCode);
		jsonObj.put("applicationID", applicationID);
		jsonObj.put("sendemail", sendEmail);
//		jsonObj.put("person_ID", emailUser.getPERSON_ID());
//		jsonObj.put("user_NAME", emailUser.getUSER_NAME());

		String rtnTemplate = ""; //POST("lettertemplate/search", jsonObj.toString(), loginuser.getUSER_NAME(), loginuser.getPassword());

		JSONArray letterTemplates = new JSONArray(rtnTemplate);
		
		JSONObject letterTemplate = letterTemplates.getJSONObject(0);
		letterBody = letterTemplate.getJSONObject("letterhead_ID").getString("description");

		letterBody = letterBody.replace("$LETTERBODY$", letterTemplate.getString("letter_TEXT"));
		letterBody = letterBody.replace("$LETTERSUBJECT$", letterTemplate.getString("letter_SUBJECT"));
		
		
//		letterBody = letterBody.replace("$USERNAME$", emailUser.getUSER_NAME());
//		letterBody = letterBody.replace("$USEREMAIL$", emailUser.getEMAIL());

		jsonObj.put("letter_ID", letterTemplate.getLong("letter_ID"));
		jsonObj.put("letter_SUBJECT", letterTemplate.getString("letter_SUBJECT"));
		jsonObj.put("bodytext", letterBody);

		letterBody = ""; //PersonService.POST("personcommunicationletter/create", jsonObj.toString(), loginuser.getUSER_NAME(), loginuser.getPassword());

		return letterBody;
	}
}
