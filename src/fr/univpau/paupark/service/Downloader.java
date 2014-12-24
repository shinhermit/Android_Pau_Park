package fr.univpau.paupark.service;


import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Downloader {
	
	public static String query(URI uri)
	{
		boolean success = true;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpEntity entity = null;
		HttpResponse response = null;
		String responseString = null; 
		
		try {
			//request 
			HttpGet get = new HttpGet(uri);
			//execute request
			response = client.execute(get);
			//handle response and store for later use.
			entity = response.getEntity();
			responseString = EntityUtils.toString(entity);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
 		}
		
		return responseString;
	}
}
