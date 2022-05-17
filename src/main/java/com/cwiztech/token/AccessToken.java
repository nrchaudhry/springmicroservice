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

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class AccessToken {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String oauthapplicationPath;

	@Autowired
	public AccessToken(Environment env) {
		log.info("Trying to get Application Path..........");
		AccessToken.oauthapplicationPath = env.getRequiredProperty("file_path.oauthapplicationPath");
	}

	public static HttpHeaders getHttpHeader(String accessToken) throws JsonProcessingException, JSONException, ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", accessToken);
		headers.add("Grant_Type", "password");
		return headers;
	}

	public static JSONObject checkToken(String accessToken) {
		log.info("----------------------------------------------------------------------------------");
		log.info("Check Toeken Detail By Token Service");
		log.info("Application Path: " + oauthapplicationPath);
		log.info("accessToken: " + accessToken);
		log.info("----------------------------------------------------------------------------------");
		RestTemplate restTemplate = new RestTemplate();
		JSONObject myobj = new JSONObject();
		
		String token = accessToken;
		String[] parts = token.split(" ");
		String OauthToken = parts[1];
		log.info(OauthToken);
		
		try {
			ResponseEntity<String> getToken = restTemplate.exchange(oauthapplicationPath + "oauth/check_token?token=" + OauthToken, HttpMethod.GET, null, String.class);
			myobj = new JSONObject(getToken.getBody().toString());
		} catch (Exception e) {
			myobj.put("error", "invalid_token");
			myobj.put("error_description", "Token was not recognised");
		}
		log.info("----------------------------------------------------------------------------------");
		
		return myobj;
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

		return applicationDetail.getString("applicationservice_PATH");
	}
}
