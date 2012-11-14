package com.itrade.db;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.model.Prospecto;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class DAOProspecto {

private Activity window;
	
    JSONArray clientes = null;
    
    String idusuario;
	
	public DAOProspecto(Activity window){
		super();
		this.window=window;
	}

	public List<Prospecto> buscarProspectosxVendedor(String idusuario, String razonSocial) {
		
		List<Prospecto> listaProspectos = new ArrayList<Prospecto>();
		
		Intent i = this.window.getIntent();                
		//idusuario=String.valueOf(idUsuario);	
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		/*****ws****/
		
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();	
		param.add(new BasicNameValuePair("idvendedor", idusuario));
		param.add(new BasicNameValuePair("razon_social", razonSocial));
		
//		String route="dp2/itrade/ws/cobranza/get_all_products";
		//String route="/ws/clientes/get_clients_by_idvendedor_p/";
		String route="/ws/clientes/get_prospecto_by_vendedor/";
		
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
		listaProspectos	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Prospecto>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/				
		return listaProspectos;
		
	}
	

public void registrarProspecto(String idusuario, String dni, String nombre, String paterno, String materno, String fono,
									String correo, String fecha, String ruc, String rsocial, String direcc, String latitud,
									String longitud) {
		
		Intent i = this.window.getIntent();                
		//idusuario=String.valueOf(idUsuario);	
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		/*****ws****/
		
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();	
		
		param.add(new BasicNameValuePair("idvendedor", idusuario));
        
        param.add(new BasicNameValuePair("dni", dni));
        param.add(new BasicNameValuePair("nombre", nombre));
        param.add(new BasicNameValuePair("apepaterno", paterno));
        param.add(new BasicNameValuePair("apematerno", materno));
        param.add(new BasicNameValuePair("telefono",  fono));
        param.add(new BasicNameValuePair("email",  correo));
        param.add(new BasicNameValuePair("fechanac", fecha)); // Revisar Nombre de FechaNac
   
        param.add(new BasicNameValuePair("ruc", ruc));
        param.add(new BasicNameValuePair("razon_social", rsocial));
        param.add(new BasicNameValuePair("direccion", direcc));
        param.add(new BasicNameValuePair("latitud", latitud));
        param.add(new BasicNameValuePair("longitud", longitud));
		
//		String route="dp2/itrade/ws/cobranza/get_all_products";
		//String route="/ws/clientes/get_clients_by_idvendedor_p/";
		String route="/ws/clientes/registrar_prospecto/";
		
		sync.conexion(param,route);
		
		try {
			sync.getHilo().join();
			Log.d("TAG","LLEGA PRIMERO AKI");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}

	
}
