package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import com.metrosoft.jsonParser.UserFunctions;
import com.metrosoft.model.Cliente;

public class DAOCliente extends DAOConexion{


	
	//    private final float factor=1000000;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_IDCLIENTE = "idcliente";
	private static String KEY_CLIENTES = "clientes";
	private static String KEY_NOMBRE = "nombre";
	private static String KEY_LONGITUDE = "longitude";
	private static String KEY_LATITUDE = "latitude";
	private static String KEY_APELLIDOS = "apellidos";
    // products JSONArray
    JSONArray clientes = null;
	
	public DAOCliente(){
		super();
	}
	public List<Cliente> getAllClientes(int idRuta) {
		List<Cliente> liscaCliente = new ArrayList<Cliente>();

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            //select distinct p.idparadero, p.nombre, p.posx, p.posy, p.estado, pxr.orden from paradero as p , paraderoxruta as pxr where p.idparadero=pxr.idparadero and pxr.idruta=9 and p.estado=1 order by pxr.orden
//            sql = "select * from \"public\".paradero";
//            sql = "select distinct p.idparadero, p.nombre, p.posx, p.posy, p.estado, pxr.orden from paradero as p , paraderoxruta as pxr where p.idparadero=pxr.idparadero and pxr.idruta="+idRuta + " and p.estado=1 order by pxr.orden";
            sql = "select * from cliente order by idcliente";
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdcliente(rs.getInt("idcliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setLatitude(rs.getDouble("latitude"));
                cliente.setLongitude(rs.getDouble("longitude"));                
                cliente.setEstado(1);                
                liscaCliente.add(cliente);
            }

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}

		}
		return liscaCliente;
	}
//	public List<Cliente> getAllClientes(int idRuta) {
//		List<Cliente> listaCliente = new ArrayList<Cliente>();
//		UserFunctions userFunction = new UserFunctions();
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
//                        JSONObject c = clientes.getJSONObject(i);
//                        
//                        
//                      Cliente cliente = new Cliente();
//                      // Storing each json item in variable 
//                      String strIdCliente=c.getString(KEY_IDCLIENTE);
//                      cliente.setIdcliente(new Integer(strIdCliente.toString()));
//                      cliente.setNombre(c.getString(KEY_NOMBRE));
//                      cliente.setApellidos(c.getString(KEY_APELLIDOS));
////                      cliente.setLatitude(c.getString(KEY_LATITUDE));
////                      cliente.setLongitude(c.getString(KEY_LONGITUDE));                
//                      cliente.setEstado(1);                                                 
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
//	}
	public List<String> getCliente(int i) {

		return null;
	}
	
}

