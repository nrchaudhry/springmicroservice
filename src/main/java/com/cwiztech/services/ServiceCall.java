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
public class ServiceCall {
	private static final Logger log = LoggerFactory.getLogger(ServiceCall.class);
	private static String servicePath;
	private static String commonServicePath;

	public ServiceCall(Environment env) {
		ServiceCall.servicePath = env.getRequiredProperty("file_path.servicePath");
		ServiceCall.commonServicePath = env.getRequiredProperty("file_path.commonServicePath");
	}

	public static String GET(String URI, String accessToken, boolean isCommon)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
		
		String[] parts = URI.split("/");
		String serviceURI = "";
		if (isCommon == true) {
			serviceURI = ServiceCall.commonServicePath + "/" + parts[0];
		} else {
			serviceURI = ServiceCall.servicePath + "/" + parts[0];
		}
		String servicePath = AccessToken.findServiceDetail(serviceURI, "GET", headers);

		log.info("GET: " + servicePath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(servicePath + URI, HttpMethod.GET, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		return rtnAPIResponse;
	}

	public static String POST(String URI, String body, String accessToken, boolean isCommon)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
		
		String[] parts = URI.split("/");
		String serviceURI = "";
		if (isCommon == true) {
			serviceURI = ServiceCall.commonServicePath + "/" + parts[0];
		} else {
			serviceURI = ServiceCall.servicePath + "/" + parts[0];
		}
		String servicePath = AccessToken.findServiceDetail(serviceURI, "GET", headers);

		log.info("POST: " + servicePath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(servicePath + URI, HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		return rtnAPIResponse;
	}

	public static String PUT(String URI, String body, String accessToken, boolean isCommon)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
		
		String[] parts = URI.split("/");
		String serviceURI = "";
		if (isCommon == true) {
			serviceURI = ServiceCall.commonServicePath + "/" + parts[0];
		} else {
			serviceURI = ServiceCall.servicePath + "/" + parts[0];
		}
		String servicePath = AccessToken.findServiceDetail(serviceURI, "GET", headers);

		log.info("PUT: " + servicePath + URI);
		log.info("Body: " + body);

		HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(servicePath + URI, HttpMethod.PUT, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		return rtnAPIResponse;
	}

	public static String DELETE(String URI, String accessToken, boolean isCommon)
			throws JsonProcessingException, JSONException, ParseException {
		String rtnAPIResponse="Invalid Resonse";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
		
		String[] parts = URI.split("/");
		String serviceURI = "";
		if (isCommon == true) {
			serviceURI = ServiceCall.commonServicePath + "/" + parts[0];
		} else {
			serviceURI = ServiceCall.servicePath + "/" + parts[0];
		}
		String servicePath = AccessToken.findServiceDetail(serviceURI, "GET", headers);

		log.info("DELETE: " + servicePath + URI);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(servicePath + URI, HttpMethod.DELETE, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		return rtnAPIResponse;
	}
}

//if(calling.getCUSTOMER_ID() != null) {
	//JSONObject customer = new JSONObject(ServiceCall.GET("customer/"+calling.getCUSTOMER_ID(), apiRequest.getREQUEST_OUTPUT()));
	//calling.setCUSTOMER_DETAIL(customer.toString());
//}





//if (callings.size()>0) {
//	List<Integer> customerList = new ArrayList<Integer>();
//	for (int i=0; i<callings.size(); i++) {
//		customerList.add(Integer.parseInt(callings.get(i).getCUSTOMER_ID().toString()));
//	}
//	JSONArray customerObject = new JSONArray(ServiceCall.POST("customer/ids", "{customers: "+customerList+"}", apiRequest.getREQUEST_OUTPUT()));
	
//	for (int i=0; i<callings.size(); i++) {
//		for (int j=0; j<customerObject.length(); j++) {
//			JSONObject customer = customerObject.getJSONObject(j);
//			if(callings.get(i).getCUSTOMER_ID() == customer.getLong("customer_ID") ) {
//				callings.get(i).setCUSTOMER_DETAIL(customer.toString());
//			}
//		}
//	}
//}


