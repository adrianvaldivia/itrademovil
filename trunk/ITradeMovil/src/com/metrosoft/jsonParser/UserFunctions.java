package com.metrosoft.jsonParser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	//private static String loginURL = "http://10.0.2.2/webservices/Login/";
	private static String URL = "http://190.222.214.208/webservices/Login/";
	
	private static String login_tag = "login";
	private static String cliente_tag = "cliente";
	
	// constructor
	public UserFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param user
	 * @param password
	 * */
	public JSONObject loginUser(String user, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("user", user));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	public JSONObject getAllClientes(){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", cliente_tag));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		// return json
		 Log.e("JSON", json.toString());
		return json;
	}
}
