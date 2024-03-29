package com.itrade.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Meta;
import com.itrade.model.Usuario;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class DAOMeta {

	private Activity window;
	
    JSONArray clientes = null;
    
    String idusuario;
	
	public DAOMeta(Activity window){
		super();
		this.window=window;
	}

	public Meta buscarMetaxVendedor(String idusuario) {
		
		List<Meta> listaMeta = new ArrayList<Meta>();
		Meta metav= new Meta();
		
		Intent i = this.window.getIntent();                
		//idusuario=String.valueOf(idUsuario);	
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		/*****ws****/
		
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();	
		param.add(new BasicNameValuePair("idvendedor", idusuario));
		//param.add(new BasicNameValuePair("razon_social", razonSocial));
		
//		String route="dp2/itrade/ws/cobranza/get_all_products";
		//String route="/ws/clientes/get_clients_by_idvendedor_p/";
		//String route="/ws/clientes/get_prospecto_by_vendedor/";
		String route="/ws/pedido/meta_periodo/";
		
		sync.conexion(param,route);
		
		try {
			sync.getHilo().join();
			Log.d("TAG","LLEGA PRIMERO AKI");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Log.d("TAG","LLEGA SEGUNDO AKI");
	    Gson gson = new Gson();
							
		Log.e("log_tag", "se cayo5" );
		try {
			metav	=	gson.fromJson(sync.getResponse(), Meta.class);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			metav.setMeta(0.0);
			metav.setNombre("Error Web Service");
			metav.setSuma(0.0);
			e.printStackTrace();
		}				    

		    /*****************************WEBSERVICE END**********************************/				
		return metav;
		
	}
}
