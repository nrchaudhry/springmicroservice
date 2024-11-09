package com.cwiztech.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cwiztech.datalogs.model.APIRequestDataLog;
import com.cwiztech.datalogs.model.DatabaseTables;
import com.cwiztech.datalogs.model.tableDataLogs;
import com.cwiztech.token.AccessToken;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class SageService {
    private static final Logger log = LoggerFactory.getLogger(AccessToken.class);
    private static String sageService;
    private static String clientID = "0d1d7691-5b14-4610-8ab5-b87478a10e2a/b9ccc1b3-cc20-4f34-ba35-c25993680a7b";
    private static String clientSecret = "j*5ll~CCn$}]+kP-]n!M";
    private static String grantType = "refresh_token";

    public SageService(Environment env) {
        SageService.sageService = env.getRequiredProperty("file_path.SAGESERVICE");
    }

    public static APIRequestDataLog GET(String URI, String accessToken, DatabaseTables databaseTableID)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
        
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);
        headers = AccessToken.getHttpHeader(generateToken(AccessToken.applicationID, AccessToken.oauthservicePath, AccessToken.refreshToken, accessToken), (long) 0);

        log.info("GET: " + appPath + URI);
		
        APIRequestDataLog apiRequest;
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, URI, null, "");

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.GET, entity, String.class);
            rtnAPIResponse = response.getBody().toString();
        } catch (RestClientException e) {
        	if(e instanceof HttpStatusCodeException) {
	        	JSONArray response = new JSONArray(((HttpStatusCodeException)e).getResponseBodyAsString());
	        	rtnAPIResponse = response.get(0).toString();
        	}
        }

		apiRequest.setREQUEST_OUTPUT(rtnAPIResponse);
		apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
		apiRequest.setREQUEST_STATUS("Success");

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return apiRequest;
    }

    public static APIRequestDataLog POST(String URI, String body, String accessToken, DatabaseTables databaseTableID)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
        
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);
        headers = AccessToken.getHttpHeader(generateToken(AccessToken.applicationID, AccessToken.oauthservicePath, AccessToken.refreshToken, accessToken), (long) 0);

        log.info("POST: " + appPath + URI);
        log.info("Body: " + body);

		APIRequestDataLog apiRequest;
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, URI, body, "");

        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.POST, entity, String.class);
            rtnAPIResponse=response.getBody().toString();
        } catch (RestClientException e) {
        	if(e instanceof HttpStatusCodeException) {
	        	JSONArray response = new JSONArray(((HttpStatusCodeException)e).getResponseBodyAsString());
	        	rtnAPIResponse = response.get(0).toString();
        	}
        }

		apiRequest.setREQUEST_OUTPUT(rtnAPIResponse);
		apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
		apiRequest.setREQUEST_STATUS("Success");

		log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return apiRequest;
    }

    public static APIRequestDataLog PUT(String URI, String body, String accessToken, DatabaseTables databaseTableID)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
        
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);
        headers = AccessToken.getHttpHeader(generateToken(AccessToken.applicationID, AccessToken.oauthservicePath, AccessToken.refreshToken, accessToken), (long) 0);

        log.info("PUT: " + appPath + URI);
        log.info("Body: " + body);

		APIRequestDataLog apiRequest;
		apiRequest = tableDataLogs.apiRequestDataLog("POST", databaseTableID, (long) 0, URI, body, "");

        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.PUT, entity, String.class);
            rtnAPIResponse=response.getBody().toString();
        } catch (RestClientException e) {
        	if(e instanceof HttpStatusCodeException) {
	        	JSONArray response = new JSONArray(((HttpStatusCodeException)e).getResponseBodyAsString());
	        	rtnAPIResponse = response.get(0).toString();
        	}
        }

		apiRequest.setREQUEST_OUTPUT(rtnAPIResponse);
		apiRequest.setRESPONSE_DATETIME(dateFormat1.format(date));
		apiRequest.setREQUEST_STATUS("Success");

		log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return apiRequest;
    }

    public static String DELETE(String URI, String accessToken)
            throws JsonProcessingException, JSONException, ParseException {
        String rtnAPIResponse="Invalid Resonse";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = AccessToken.getHttpHeader(accessToken, (long) 0);
        String appPath = AccessToken.findApplicationDetail(sageService, headers);

        log.info("DELETE: " + appPath + URI);

        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(appPath + URI, HttpMethod.DELETE, entity, String.class);
        rtnAPIResponse=response.getBody().toString();

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        return rtnAPIResponse;
    }

    public static String generateToken(long applicationID, String oauthservicePath, String refreshToken, String accessToken)
            throws JsonProcessingException, JSONException, ParseException, InterruptedException {
        String rtnAPIResponse="Invalid Resonse";
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        log.info("Generate Token");

        HttpHeaders headers = new HttpHeaders();

        JSONObject body = new JSONObject();
        body.put("client_id", clientID);
        body.put("client_secret", clientSecret);
        body.put("grant_type", grantType);
        body.put("refresh_token", refreshToken);
        
        headers.add("Content-type", "application/json");
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);

        log.info("oauthservicePath: " + oauthservicePath);
        log.info("Body: " + body.toString());
        log.info("entity: " + entity.toString());

        
        int getDataCount = 1;
        while (getDataCount <= 20) {
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(oauthservicePath, entity, String.class);
                rtnAPIResponse = response.getBody().toString();
                getDataCount = 21;
            } catch (Exception e) {
                log.info(e.getMessage());
                Thread.sleep(5000);
                getDataCount = getDataCount + 1;
            }
        }
        
        body = new JSONObject(rtnAPIResponse);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("application_ID", applicationID);
        jsonObj.put("authorization_CODE", body.getString("refresh_token"));
        
        ServiceCall.POST("application", jsonObj.toString(), accessToken, true);

        log.info("Response: " + rtnAPIResponse);
        log.info("----------------------------------------------------------------------------------");
        log.info("Bearer " + body.getString("access_token"));
        log.info("----------------------------------------------------------------------------------");
        return body.getString("access_token");
    }
}




