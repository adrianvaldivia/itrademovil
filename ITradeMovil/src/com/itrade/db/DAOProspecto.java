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
	

public int registrarProspecto(Prospecto client, long idu) {
		int idProspecto=-1;
		Intent i = this.window.getIntent();                
		idusuario=String.valueOf(idu);	
	//	Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		/*****ws****/
		
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();	
		
		String idu2=""+idu;
		
		 param.add(new BasicNameValuePair("idvendedor", idu2));
         
         param.add(new BasicNameValuePair("dni", client.getDNI()));
         param.add(new BasicNameValuePair("nombre", client.getNombre()));
         param.add(new BasicNameValuePair("apepaterno", client.getApePaterno()));
         param.add(new BasicNameValuePair("apematerno", client.getApeMaterno()));
         param.add(new BasicNameValuePair("telefono",  client.getTelefono()));
         param.add(new BasicNameValuePair("email",  client.getEmail()));
         param.add(new BasicNameValuePair("fechanac", client.getFechNac())); // Revisar Nombre de FechaNac
    
         param.add(new BasicNameValuePair("ruc", client.getRUC()));
         param.add(new BasicNameValuePair("razon_social", client.getRazon_Social()));
         param.add(new BasicNameValuePair("direccion", client.getDireccion()));
         param.add(new BasicNameValuePair("latitud", client.getLatitud().toString() ));
         param.add(new BasicNameValuePair("longitud", client.getLongitud().toString()));
        
         param.add(new BasicNameValuePair("montosolicitado", ""+client.getMontoActual() ));
		
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
	    Log.d("TAG","LLEGA SEGUNDO AKI");
	    Gson gson = new Gson();
							
		Log.e("log_tag", "se cayo5" );
		idProspecto	=	gson.fromJson(sync.getResponse(), new TypeToken<Integer>(){}.getType());	
		
		return idProspecto;

}

	
}
