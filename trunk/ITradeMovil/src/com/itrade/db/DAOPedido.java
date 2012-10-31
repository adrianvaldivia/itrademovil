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
import com.itrade.model.Pedido;
import com.itrade.model.PedidoLinea;
import com.itrade.model.Producto;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOPedido {
	// JSON Response node names
	JSONArray pedidos = null;
	public Activity window;

	public DAOPedido(Activity window){
		super();
		this.window = window;
	}
	DAOFuncionesAuxiliares daoFunc = null;


	public List<Pedido> getAllPedidos(long idUsuario) {
		
		List<Pedido> listapedido = new ArrayList<Pedido>();
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		String idusuario=String.valueOf(idUsuario);
//		param.add(new BasicNameValuePair("idvendedor", idusuario));
		String route="/ws/pedido/ultimos_pedidos/"+idusuario;
		
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
		listapedido	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Pedido>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/				
		return listapedido;
		
	}


	public int registrarPedido(Pedido pedido){
		int idPedido=-1;
		
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		
		String idcliente =String.valueOf(pedido.getIdCliente());
		String mtopedido =String.valueOf(pedido.getMontoTotal());
		
		param.add(new BasicNameValuePair("idcliente", idcliente));
		param.add(new BasicNameValuePair("montototalpedidosinigv", mtopedido));
		
		String route="/ws/pedido/registrar_pedido/";
		
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
		idPedido	=	gson.fromJson(sync.getResponse(), new TypeToken<Integer>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/		
		
		return idPedido;
    }


	public int registrarPedidoLinea(PedidoLinea pedidolinea) {
		UsuarioFunctions userFunction = new UsuarioFunctions();
		int salida=-1;
		
		/**************************WEBSERVICE BEGIN******************************/
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		
		String idpedido =String.valueOf(pedidolinea.getIdPedido());
		String idproducto =String.valueOf(pedidolinea.getIdProducto());
		String mtolinea =String.valueOf(pedidolinea.getMontoLinea());
		String ctdad =String.valueOf(pedidolinea.getCantidad());
		
		param.add(new BasicNameValuePair("idpedido", idpedido));
		param.add(new BasicNameValuePair("idproducto", idproducto));
		param.add(new BasicNameValuePair("montolinea", mtolinea));
		param.add(new BasicNameValuePair("cantidad", ctdad));
		
		String route="/ws/pedido/registrar_pedido_linea/";
		
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
		salida	=	gson.fromJson(sync.getResponse(), new TypeToken<Integer>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/		
		
		return salida;
	}
	
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}

}
