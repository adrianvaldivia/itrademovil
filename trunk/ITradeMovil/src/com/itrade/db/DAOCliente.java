package com.itrade.db;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itrade.model.Cliente;
import com.itrade.controller.cobranza.Syncronizar;
import com.itrade.controller.pedidos.SyncronizarPedidos;
import com.itrade.controller.pedidos.UsuarioFunctions;

public class DAOCliente {

	private Activity window;
	
	//    private final float factor=1000000;
	// JSON Response node names
//	private static String KEY_SUCCESS = "success";
//	private static String KEY_ERROR = "error";
//	private static String KEY_ERROR_MSG = "error_msg";
//	private static String KEY_IDCLIENTE = "IdCliente";
//	private static String KEY_CLIENTES = "clientes";
//	private static String KEY_RAZON_SOCIAL = "Razon_Social";
//	private static String KEY_LONGITUDE = "Longitud";
//	private static String KEY_LATITUDE = "Latitud";
//	private static String KEY_RUC = "RUC";
    // products JSONArray
    JSONArray clientes = null;
    
    String idusuario;
	
	public DAOCliente(Activity window){
		super();
		this.window=window;
	}
//	public List<Cliente> getAllClientes(int idRuta) {
//		List<Cliente> liscaCliente = new ArrayList<Cliente>();
//
//		ResultSet rs=null;
//		try {
//			this.conectar();
//			st = getConn().createStatement();
//            String sql;
//            //select distinct p.idparadero, p.nombre, p.posx, p.posy, p.estado, pxr.orden from paradero as p , paraderoxruta as pxr where p.idparadero=pxr.idparadero and pxr.idruta=9 and p.estado=1 order by pxr.orden
////            sql = "select * from \"public\".paradero";
////            sql = "select distinct p.idparadero, p.nombre, p.posx, p.posy, p.estado, pxr.orden from paradero as p , paraderoxruta as pxr where p.idparadero=pxr.idparadero and pxr.idruta="+idRuta + " and p.estado=1 order by pxr.orden";
//            sql = "select * from cliente order by idcliente";
//            rs = st.executeQuery(sql);
//            while (rs.next()) {
//                Cliente cliente = new Cliente();
//                cliente.setIdcliente(rs.getInt("idcliente"));
//                cliente.setNombre(rs.getString("nombre"));
//                cliente.setApellidos(rs.getString("apellidos"));
//                cliente.setLatitude(rs.getDouble("latitude"));
//                cliente.setLongitude(rs.getDouble("longitude"));                
//                cliente.setEstado(1);                
//                liscaCliente.add(cliente);
//            }
//
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally{
//			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
//			if (st != null) try { st.close(); } catch (SQLException ignore) {}
//            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
//
//		}
//		return liscaCliente;
//	}
	public List<Cliente> getAllClientes(int idUsuario) {
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		
		Intent i = this.window.getIntent();                
		idusuario=String.valueOf(idUsuario);	
		Log.d("IDUSUARIO", "usuario="+idusuario.toString());
		
		/*****ws****/
		
		
		Syncronizar sync = new Syncronizar(window);
		List<NameValuePair> param = new ArrayList<NameValuePair>();	
		param.add(new BasicNameValuePair("idvendedor", idusuario));
		
//		String route="dp2/itrade/ws/cobranza/get_all_products";
		String route="/ws/clientes/get_clients_by_idvendedor_p/";
		
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
		listaCliente	=	gson.fromJson(sync.getResponse(), new TypeToken<List<Cliente>>(){}.getType());				    

		    /*****************************WEBSERVICE END**********************************/				
		return listaCliente;
		
		
//		UsuarioFunctions userFunction = new UsuarioFunctions();
//		
//		JSONObject json = userFunction.getAllClientes();
//		
//		try {
//			if (json.getString(KEY_SUCCESS) != null) {				
//				String res = json.getString(KEY_SUCCESS);
////				String uid = json.getString(KEY_IDCLIENTE);
//				
//				if(Integer.parseInt(res) == 1){					
//					clientes = json.getJSONArray(KEY_CLIENTES);						
////					JSONObject json_user = json.getJSONObject("user");
//                    for (int i = 0; i < clientes.length(); i++) {
//                        JSONObject json_cliente = clientes.getJSONObject(i);
//                        
//                        
//                      Cliente cliente = new Cliente();
//                      // Storing each json item in variable 
//                      String strIdCliente=json_cliente.getString(KEY_IDCLIENTE);
//                      String strLatitud=json_cliente.getString(KEY_LATITUDE);
//                      String strLongitud=json_cliente.getString(KEY_LONGITUDE);
//                      cliente.setId(new Long(strIdCliente.toString()));
//                      cliente.setRazon_Social(json_cliente.getString(KEY_RAZON_SOCIAL));
//                      cliente.setRUC(json_cliente.getString(KEY_RUC));                   
//
//                      cliente.setLatitud(Double.parseDouble(strLatitud.toString()));
//                      cliente.setLongitud(Double.parseDouble(strLongitud.toString()));                                                                
//                      // adding object to ArrayList
//                      listaCliente.add(cliente);
//                    }
//					
//				}else{
//					// Error when trying to getAllClientes
////					resul=-1;
////					loginErrorMsg.setText("Incorrect username/password");
//				}
//			}
//			else{
////				resul=-1;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();			
//		}		
//		return listaCliente;
	}
	
}

