package com.cwiztech.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {
	private static String servicePath;
	private static String oauthapplicationPath;

	public EndpointsListener(Environment env) {
		EndpointsListener.servicePath = env.getRequiredProperty("file_path.servicePath");
		EndpointsListener.oauthapplicationPath = env.getRequiredProperty("file_path.oauthapplicationPath");
	}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	List<String> endpoints = new ArrayList<String>();
    	JSONArray applicationServices = new JSONArray();
    	Socket socket = new Socket();
    	try {
			socket.connect(new InetSocketAddress("google.com", 80));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        ApplicationContext applicationContext = event.getApplicationContext();
        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
             .forEach((key, value) -> endpoints.add(key.toString()));
        
        for (int i=0; i<endpoints.size(); i++) {
        	JSONObject applicationService = new JSONObject();
        	String services[] = endpoints.get(i).substring(1, endpoints.get(i).length()-1).split(",");
        	
        	if (endpoints.get(i).contains("[/error]") == false) {
	        	applicationService.put("microservice_URI", EndpointsListener.servicePath+services[0].replace("[", "").replace("]", ""));
	        	applicationService.put("microservice_PATH", "http:/"+socket.getLocalAddress()+":8080/");
	        	applicationService.put("microservice_METHOD", services[1].replace("methods=", "").replace("[", "").replace("]", ""));
	        	if (endpoints.get(i).contains("[GET]") && services[0].replace("[", "").replace("]", "").lastIndexOf("/") == 0)
		        	applicationService.put("ismainpath", "Y");
	        	else
		        	applicationService.put("ismainpath", "N");
	        	applicationServices.put(applicationService);
        	}
        }
        
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		headers.add("Authorization", "serviceregistry");
		headers.add("Grant_Type", "password");
		String appPath = EndpointsListener.oauthapplicationPath;

		HttpEntity<String> entity = new HttpEntity<String>(applicationServices.toString().replace("\"", "'"), headers);
		restTemplate.exchange(appPath + "microservice", HttpMethod.PUT, entity, String.class);
    }
}
