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
import com.itrade.model.Categoria;
import com.itrade.model.Producto;
import com.itrade.model.Usuario;
import com.itrade.pedidos.Login;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOProducto {

	private Activity window;
	
	public DAOProducto(Activity window){
		super();
		this.window = window;
	}

	public List<Producto> getAllProductos() {
		
		List<Producto> listaproducto = new ArrayList<Producto>();
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								
//		String route="dp2/itrade/ws/cobranza/get_all_products";
		String route="/ws/cobranza/get_all_products";
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
		listaproducto	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Producto>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/				
		return listaproducto;
	}	

	
	public List<Categoria> getAllCategorias() {
		List<Categoria> listacategoria = new ArrayList<Categoria>();

		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();								

		String route="/ws/cobranza/get_all_categorias";
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
		listacategoria	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Categoria>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/	
		
		return listacategoria;
	}
	
}
