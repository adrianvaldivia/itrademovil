package com.itrade.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itrade.model.Pedido;
import com.itrade.model.PedidoLinea;
import com.itrade.controller.pedidos.UsuarioFunctions;




public class DAOPedido {
	// JSON Response node names
	JSONArray pedidos = null;
	private static String KEY_PEDIDOS = "pedidos";
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_IDPEDIDO = "IdPedido";
	private static String KEY_IDCLIENTE = "IdCliente";
	private static String KEY_IDESTADOPEDIDO = "IdEstadoPedido";
	private static String KEY_CHECKIN = "CheckIn";
	private static String KEY_FECHAPEDIDO = "FechaPedido";
	private static String KEY_FECHACOBRANZA = "FechaCobranza";
	private static String KEY_MONTOSINIGV = "MontoSinIGV";
	private static String KEY_IGV = "IGV";
	private static String KEY_MONTOTOTAL = "MontoTotal";

	public DAOPedido(){
		super();
	}
	DAOFuncionesAuxiliares daoFunc = null;
//	public List<Pedido> getAllPedidos(int idempleado) {
//		List<Pedido> listaPedido = new ArrayList<Pedido>();
//
//		ResultSet rs=null;
//		try {
//			this.conectar();
//			st = getConn().createStatement();
//            String sql;
//            
////            sql = "select * from pedido where idempleado =" + idempleado+" order by idpedido";
//            sql = "select * from pedido p, cliente c where p.idempleado =" + idempleado+" and p.idcliente=c.idcliente order by idpedido";
//            
//            rs = st.executeQuery(sql);
//            
//            int cont=0;
//            while (rs.next()) {
//                if (cont<5){
//	            	Pedido pedido = new Pedido();
//	                long aux=0+rs.getInt("idpedido");	               
//	                pedido.setId(aux);
//	                pedido.setFechaPedido(rs.getDate("fecha"));	              
//	                pedido.setIdCliente(rs.getInt("idcliente"));
//	                listaPedido.add(pedido);
//                }
//                else break;
//                cont++;
//            }
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
//		return listaPedido;
//	}

	public List<Pedido> getAllPedidos(long idUsuario) {
		List<Pedido> listaPedido = new ArrayList<Pedido>();
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json = userFunction.getAllPedidos();
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
//				String uid = json.getString(KEY_IDCLIENTE);
				
				if(Integer.parseInt(res) == 1){					
					pedidos = json.getJSONArray(KEY_PEDIDOS);						
//					JSONObject json_user = json.getJSONObject("user");
                    for (int i = 0; i < pedidos.length(); i++) {
                        JSONObject json_cliente = pedidos.getJSONObject(i);
                        
                        
                      Pedido pedido = new Pedido();
                      // Storing each json item in variable 
                  	                	                                      
                      String strIdPedido=json_cliente.getString(KEY_IDPEDIDO);
                      String strIdCliente=json_cliente.getString(KEY_IDCLIENTE);
                      String strIdEstadoPedido=json_cliente.getString(KEY_IDESTADOPEDIDO);
                      String strCheckIn=json_cliente.getString(KEY_CHECKIN);
                      String strFechaPedido=json_cliente.getString(KEY_FECHAPEDIDO);
                      String strFechaCobranza=json_cliente.getString(KEY_FECHACOBRANZA);
                      String strMontoSinIgv=json_cliente.getString(KEY_MONTOSINIGV);
                      String strIGV=json_cliente.getString(KEY_IGV);
                      String strMontoTotal=json_cliente.getString(KEY_MONTOTOTAL);

	                  Date fechaPedido=null;
	                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                  try {	                	  
	                	  fechaPedido = dateFormat.parse(strFechaPedido);
	                  } catch (ParseException e) {						
						e.printStackTrace();
	                  }
	                  Date fechaCobranza=null;	                  
	                  try {	                	  
	                	  fechaCobranza = dateFormat.parse(strFechaCobranza);
	                  } catch (ParseException e) {						
						e.printStackTrace();
	                  }
	                  
                      pedido.setId(new Long(strIdPedido.toString()));
                      pedido.setIdCliente(Integer.parseInt(strIdCliente));
                      pedido.setIdEstadoPedido(Integer.parseInt(strIdEstadoPedido));
                      pedido.setCheckIn(Integer.parseInt(strCheckIn));
                      pedido.setFechaPedido(fechaPedido);
                      pedido.setFechaCobranza(fechaCobranza);
                      pedido.setMontoSinIGV(Double.parseDouble(strMontoSinIgv.toString()));
                      pedido.setIGV(Double.parseDouble(strIGV.toString()));
                      pedido.setMontoTotal(Double.parseDouble(strMontoTotal.toString()));
                      // adding object to ArrayList
                      listaPedido.add(pedido);
                    }
					
				}else{
					// Error when trying to getAllClientes
//					resul=-1;
//					loginErrorMsg.setText("Incorrect username/password");
				}
			}
			else{
//				resul=-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();			
		}		
		return listaPedido;
	}


	public int registrarPedido(Pedido pedido){
		int idPedido=-1;
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json=userFunction.registrarPedido(pedido.getIdCliente(), 0, 0, pedido.getMontoTotal());
		
		try{
			
			if(json.getString(KEY_SUCCESS)!=null){
				String res = json.getString(KEY_SUCCESS);				
				if(Integer.parseInt(res) == 1){		
					String pedidoid = json.getString(KEY_IDPEDIDO);
					idPedido=new Integer(pedidoid.toString());									
				}		
			}
		}catch(JSONException e){			
			e.printStackTrace();
		}
		return idPedido;
    }


	public int registrarPedidoLinea(PedidoLinea pedidoLinea) {
		UsuarioFunctions userFunction = new UsuarioFunctions();
		int salida=-1;
//		pedidoLinea.setMontoLinea(666.0);
		JSONObject json=userFunction.registrarLineaPedido(pedidoLinea.getIdPedido(), pedidoLinea.getIdProducto(), pedidoLinea.getMontoLinea(), pedidoLinea.getCantidad());
		
		try{
			if(json.getString(KEY_SUCCESS)!=null){
				String res = json.getString(KEY_SUCCESS);				
				if(Integer.parseInt(res) == 1){		
					salida=new Integer(res);									
				}		
			}
			
		}catch(JSONException e){
			
			e.printStackTrace();
		}        
		return salida;
	}
	public static int safeLongToInt(long l) {
	    return (int) Math.max(Math.min(Integer.MAX_VALUE, l), Integer.MIN_VALUE);
	}

}
