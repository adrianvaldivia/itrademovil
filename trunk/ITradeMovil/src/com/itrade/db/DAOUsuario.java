package com.itrade.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.model.Producto;
import com.itrade.model.Usuario;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOUsuario {
	// JSON Response node names
    JSONArray usuarios = null;
    private Activity window;
    
	public DAOUsuario(Activity window){
		super();
		this.window = window;
	}

	
	public Usuario confirmarLogin(String nombre, String password){

		List<Usuario> listausuario = new ArrayList<Usuario>();
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
		param.add(new BasicNameValuePair("username", nombre));
		param.add(new BasicNameValuePair("password", password));
		String route="/ws/login/get_user_by_username_password/";
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
		listausuario	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Usuario>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/		
		
		if (listausuario.size()>0) 
			return listausuario.get(0);
		else 
			return null;
    }
	
}
