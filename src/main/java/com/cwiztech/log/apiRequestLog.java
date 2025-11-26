package com.cwiztech.log;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class apiRequestLog {
	private static final Logger log = LoggerFactory.getLogger(apiRequestLog.class);
	private static String apiRequestLogPath;

	@Autowired
	public apiRequestLog(Environment env) {
		log.info("Trying to get apiRequestLogPath..........");
		apiRequestLog.apiRequestLogPath = env.getRequiredProperty("file_path.apiRequestLogPath");
	}

	public static JSONObject apiRequestErrorLog(JSONObject apiRequest, String error, String message)
			throws JsonProcessingException {
		JSONObject obj = new JSONObject();

		obj.put("status", 401);
		obj.put("error", error);
		obj.put("message", message);

		apiRequestSaveLog(apiRequest, obj.toString(), "Error");

		return obj;
	}

	public static JSONObject apiRequestCreateLog(String requestType, Long requestID, String path, String input, String workstation) throws JsonProcessingException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		JSONObject apiRequest = new JSONObject();
		
		apiRequest.put("log_DATE", dateFormat1.format(date));
		apiRequest.put("request_ID", requestID);
		apiRequest.put("request_TYPE", requestType);
		apiRequest.put("request_PATH", path);
		apiRequest.put("request_INPUT", input);
		apiRequest.put("request_DATETIME", dateFormat1.format(date));
		apiRequest.put("log_WORKSTATION", workstation);

		return apiRequest;
	}

	public static String apiRequestSaveLog(JSONObject apiRequest, String output, String status) throws JsonProcessingException {
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		RestTemplate restTemplate = new RestTemplate();
		String rtnAPIResponse="Invalid Resonse";
		
		apiRequest.put("request_OUTPUT", output);
		apiRequest.put("response_DATETIME", dateFormat1.format(date));
		apiRequest.put("request_STATUS", status);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");

		log.info("apiRequestLogPath: " + apiRequestLogPath);
		log.info("Body: " + apiRequest.toString());
		HttpEntity<String> entity = new HttpEntity<String>(apiRequest.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(apiRequestLogPath + "log", HttpMethod.POST, entity, String.class);
		rtnAPIResponse=response.getBody().toString();

		log.info("Output: " + rtnAPIResponse);
		log.info("--------------------------------------------------------");

		return rtnAPIResponse;
	}
}
