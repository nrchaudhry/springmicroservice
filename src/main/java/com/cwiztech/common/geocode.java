package com.cwiztech.common;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

public class geocode {
	
	public static GeocodingResult[] getGeocode(String address) throws ApiException, InterruptedException, IOException {
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyAgOgGQ9BhLC5Udq2-9-d2ek8UZaqWUMNE").build();

		GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
//		String obj = gson.toJson(results[0].geometry.location);
		
		return results;
		
	}

}
