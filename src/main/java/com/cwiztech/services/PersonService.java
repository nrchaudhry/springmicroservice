package com.cwiztech.services;

import java.text.ParseException;

import org.json.JSONException;
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
public class PersonService {
	private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
	private static String personService;

	public PersonService(Environment env) {
		PersonService.personService = env.getRequiredProperty("file_path.PERSONSERVICE");
	}

	public static String GET(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(personService, headers);

		log.info("GET: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.GET, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String POST(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(personService, headers);

		log.info("POST: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String PUT(String URI, String body, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(personService, headers);

		log.info("PUT: " + appPath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.PUT, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}

	public static String DELETE(String URI, String accessToken)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken);
		String appPath = AccessToken.findApplicationDetail(personService, headers);

		log.info("DELETE: " + appPath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Response: " + rtnAPIResponse);
		log.info("----------------------------------------------------------------------------------");
		return rtnAPIResponse;
	}
}

//if(calling.getPERSON_ID() != null) {
	//JSONObject person = new JSONObject(PersonService.GET("person/"+calling.getPERSON_ID(), apiRequest.getREQUEST_OUTPUT()));
	//calling.setPERSON_DETAIL(person.toString());
//}





//if (callings.size()>0) {
//	List<Integer> personList = new ArrayList<Integer>();
//	for (int i=0; i<callings.size(); i++) {
//		personList.add(Integer.parseInt(callings.get(i).getPERSON_ID().toString()));
//	}
//	JSONArray personObject = new JSONArray(PersonService.POST("person/ids", "{persons: "+personList+"}", apiRequest.getREQUEST_OUTPUT()));
	
//	for (int i=0; i<callings.size(); i++) {
//		for (int j=0; j<personObject.length(); j++) {
//			JSONObject person = personObject.getJSONObject(j);
//			if(callings.get(i).getPERSON_ID() == person.getLong("person_ID") ) {
//				callings.get(i).setPERSON_DETAIL(person.toString());
//			}
//		}
//	}
//}


