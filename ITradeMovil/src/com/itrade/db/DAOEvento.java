package com.itrade.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.itrade.model.Evento;
import com.itrade.controller.cobranza.Syncronizar;


public class DAOEvento {
	// JSON Response node names
	JSONArray eventos = null;
	public Activity window;

	public DAOEvento(Activity window){
		super();
		this.window = window;
	}
	DAOFuncionesAuxiliares daoFunc = null;


	public List<Evento> getAllEventos(long idUsuario,String fecha) {
		
		List<Evento> listaevento = new ArrayList<Evento>();
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
//		String idusuario=String.valueOf(idUsuario);
//		param.add(new BasicNameValuePair("idvendedor", idusuario));
		String route="/ws/eventos/get_eventos_idusuario_month/";
		String strIdUsuario =String.valueOf(idUsuario);	
		
		param.add(new BasicNameValuePair("idusuario", strIdUsuario));
		param.add(new BasicNameValuePair("fecha", fecha));
				
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
		listaevento	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Evento>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/				
		return listaevento;
		
	}
	
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}

}
