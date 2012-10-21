package com.itrade.controller.cobranza;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;

import com.itrade.jsonParser.HiloConexion;

public class Syncronizar {
	
	private HiloConexion hilo;
	private Activity window;	
	//private ArrayList<Login> loginList= new ArrayList<Login>();
	private String response;
	
	
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public HiloConexion getHilo() {
		return hilo;
	}

	public void setHilo(HiloConexion hilo) {
		this.hilo = hilo;
	}
	
	public Activity getWindow() {
		return window;
	}

	public void setWindow(Activity window) {
		this.window = window;
	}

	public Syncronizar(Activity ventana ){
		this.window=ventana;		
	}
	
	public void conexion(List<NameValuePair> param, String route){		
		
		hilo= new HiloConexion(this,param,route);
		hilo.start();
		
	     
	}


	
	

}
