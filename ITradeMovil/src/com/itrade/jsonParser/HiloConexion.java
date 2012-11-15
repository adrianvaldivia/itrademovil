package com.itrade.jsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.controller.cobranza.Syncronizar;


import android.app.ProgressDialog;
import android.util.Log;

public class HiloConexion extends Thread{
	public Syncronizar sync;
	private String resultadoConexion = "";
	private boolean bandera=false;
//	ProgressDialog pd;
	private List<NameValuePair> params;
	public String route;
	
	public HiloConexion(Syncronizar syn, List<NameValuePair> param, String route){
		this.sync=syn;
		this.params=param;
		this.route=route;
		Log.d("TAG","LLEGA TERCERO AKI");
//	    pd = ProgressDialog.show(syn.getWindow(), "Conectando...", "Conectando con el servidor");
	}
		
	public void run(){
		InputStream is = null;
		String result = ""; 

		try{		       
			//this.sync.getDial().show(this.sync.getWindow(), "Conectando...", "Conectando con el servidor");
			//WBhelper helper = new WBhelper("http://10.0.2.2/itrade_web/");
			WBhelper helper = new WBhelper("http://200.16.7.111/dp2/itrade/");
			
			//List<NameValuePair> params = new ArrayList<NameValuePair>();			
			
			///params.add(new BasicNameValuePair("username", "admin"));
			//params.add(new BasicNameValuePair("password", "1234"));
			
			//String responseBody=helper.obtainResponse("itrade_web/ws/Login/get_user_by_username_password/",params);
			//String route = "dp2/itrade/ws/login/get_user_by_username_password/";
			String responseBody=helper.obtainResponse(this.route,this.params);			
			
			Log.e("log_tag", responseBody );
			//result=responseBody;
			if (responseBody!="error"){
				
				this.sync.setResponse(responseBody);
			}else{
				Log.e("log_tag", "Error in webservice");
//			     pd.dismiss();
			     bandera=false;
			}				
		}catch(Exception e){
		     Log.e("log_tag", "Error in http connection "+e.toString());
//		     pd.dismiss();
		     bandera=false;
		}
		bandera=true;
//		pd.dismiss();
	}

//	private Runnable terminarConexion = new Runnable() {
//			
//			public void run() {
//				//tvi.setText(resultadoConexion);
//				Log.e("log_tag", "HIJO DEL HIJO");
//				sync.getDial().dismiss();
//			}
//		};
//	
//		

}
