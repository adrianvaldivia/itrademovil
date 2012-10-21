package com.itrade.controller.pedidos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;

import com.itrade.jsonParser.HiloConexion;
import com.itrade.model.Pedido;


public class SyncronizarPedidos {
	
	private HiloConexion hilo;
	private Activity window;	
	//private ArrayList<Login> loginList= new ArrayList<Login>();
	private ArrayList<Pedido> pedidoList;
	
	
	
	public HiloConexion getHilo() {
		return hilo;
	}

	public void setHilo(HiloConexion hilo) {
		this.hilo = hilo;
	}

	public ArrayList<Pedido> getLoginList() {
		return pedidoList;
	}

	public void setLoginList(ArrayList<Pedido> loginList) {
		this.pedidoList = loginList;
	}
	
	public Activity getWindow() {
		return window;
	}

	public void setWindow(Activity window) {
		this.window = window;
	}

	public SyncronizarPedidos(Activity ventana ){
		this.window=ventana;		
	}
	
	public void conexion(List<NameValuePair> param, String route){		
		
		//hilo= new HiloConexion(this, param, route);
		//hilo.start();
		
	     
	}


	
	

}
