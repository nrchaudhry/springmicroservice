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
public class CommunicationService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String apigateway;
	private static String communicationService;

	public CommunicationService(Environment env) {
		CommunicationService.apigateway = env.getRequiredProperty("file_path.apigatewayPath")+"service/apigateway";
		CommunicationService.communicationService = env.getRequiredProperty("file_path.COMMUNICATIONSERVICE");
	}

	private static HttpHeaders getHttpHeader(String accessToken) throws JsonProcessingException, JSONException, ParseException {
		log.info("----------------------------------------------------------------------------------");
		log.info("COMMUNICATION Service");
		log.info("----------------------------------------------------------------------------------");
		JSONObject jsonObjtoken = null;
		try {
			jsonObjtoken = new JSONObject(AccessToken.findTokenAPIGateway());
		} catch (Exception e) {
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "bearer " + jsonObjtoken.getString("access_token"));
		headers.add("Grant_Type", "password");
		return headers;
	}

	public static String GET(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getHttpHeader(accessToken);

		log.info("GET: " + apigateway + ": " + URI);

		String body = "{'service_NAME': '" + communicationService + "', 'request_TYPE': 'GET', " +
			  "'request_URI': '" + URI + "', 'request_BODY': ''}";
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(apigateway,
				HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String POST(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getHttpHeader(accessToken);

		log.info("POST: " + apigateway + ": " + URI);
		log.info("Body: " + body);

		body = "{\"service_NAME\": \"" + communicationService + "\", \"request_TYPE\": \"POST\", " +
				  "\"request_URI\": \"" + URI + "\", \"request_BODY\": \"" + body + "\"}";
			
			HttpEntity<String> entity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> response = restTemplate.exchange(apigateway,
					HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String PUT(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getHttpHeader(accessToken);

		log.info("PUT: " + apigateway + ": " + URI);
		log.info("Body: " + body);

		body = "{\"service_NAME\": \"" + communicationService + "\", \"request_TYPE\": \"PUT\", " +
				  "\"request_URI\": \"" + URI + "\", \"request_BODY\": \"" + body + "\"}";
			
			HttpEntity<String> entity = new HttpEntity<String>(body, headers);
			ResponseEntity<String> response = restTemplate.exchange(apigateway,
					HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String DELETE(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getHttpHeader(accessToken);

		log.info("GET: " + apigateway + ": " + URI);

		String body = "{'service_NAME': '" + communicationService + "', 'request_TYPE': 'DELETE', " +
			  "'request_URI': '" + URI + "', 'request_BODY': ''}";
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(apigateway,
				HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

}
