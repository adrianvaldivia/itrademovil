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
import com.itrade.pedidos.Login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsTaskConexion extends AsyncTask<String, Void, String>
{
	 private ProgressDialog dialog;
	 private Activity activity;
	 private List<NameValuePair> params;
	 public String route;
	 public Syncronizar sync;
	 private OnTaskCompleted listener;
	 String responseBody;

	 public AsTaskConexion(Syncronizar syn, List<NameValuePair> param, 
			 				String route,Activity activ,OnTaskCompleted listenr) {
		 	this.listener=listenr;
	        this.activity = activ;
			this.sync=syn;
			this.params=param;
			this.route=route;
			Log.d("TAG","LLEGA TERCERO AKI");
	        this.dialog = new ProgressDialog(activity);
	    }

	    @Override
	     protected void onPreExecute() {
	            this.dialog.setMessage("Conectando");
	            this.dialog.show();
	        }



	@Override
	protected String doInBackground(String... params) {
		String str="Hola";
		////////////////////////////////////

		try{		       
			WBhelper helper = new WBhelper("http://200.16.7.111/dp2/itrade/");
			
			responseBody=helper.obtainResponse(this.route,this.params);			
			
			Log.e("log_tag", responseBody );
			//result=responseBody;
//			if (responseBody!="error"){
//				
//				this.sync.setResponse(responseBody);
//			}else{
//				Log.e("log_tag", "Error in webservice");
////			     bandera=false;
//			}				
		}catch(Exception e){
		     Log.e("log_tag", "Error in http connection "+e.toString());
//		     bandera=false;
		}
//		bandera=true;						
	    return str;
	}



	 @Override
	protected void onPostExecute(String result) {
			if (responseBody!="error"){
				
				this.sync.setResponse(responseBody);
			}else{
				Log.e("log_tag", "Error in webservice");
//			     bandera=false;
			}
	     if (this.dialog.isShowing()) {
	           this.dialog.dismiss();
	     }
	}
	}