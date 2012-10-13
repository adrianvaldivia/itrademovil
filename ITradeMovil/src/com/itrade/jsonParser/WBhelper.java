package com.itrade.jsonParser;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class WBhelper {
	private String direccion;

	public WBhelper(String direccion) {
		super();
		this.direccion = direccion;
	}
	public String obtainResponse(String route, List<NameValuePair> params){
		HttpClient httpclient = new DefaultHttpClient(); 
		HttpPost httppost = new HttpPost(direccion+route);					      
        try {
			httppost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpclient.execute(httppost);
			String responseBody= EntityUtils.toString(response.getEntity());
			return responseBody;
        } catch (ClientProtocolException e) {
        	Log.e("JSON",e.getMessage());
        	return "error";
        } catch (IOException e) {
        	Log.e("JSON",e.getMessage());
        	return "error";
        }											        	        	      
	}
}
