package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.metrosoft.jsonParser.UserFunctions;
import com.metrosoft.model.DatosEmpleado;


public class DAOUsuario extends DAOConexion{
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_USER = "user";
	
	public DAOUsuario(){
		super();
	}
	
//	public int confirmarLogin(String usuario, String pass){
//        //int res = 0;
//		int resul=-1;
//		int aux=0;
//		ResultSet rs=null;
//        try{
//				this.conectar();
//				st = getConn().createStatement();
//				String sql;
//				sql = "select * from empleado where NOMBRE='"+usuario+"' and PASSWORD='"+pass+"'";
//				rs = st.executeQuery(sql);
//				int rowCount = 0;
//				while ( rs.next() )  
//				{   
//					aux=rs.getInt("IDEMPLEADO");
//				    rowCount++;
//				} 
//                if(rowCount>0){//Si hay un usuario
//                    resul=aux;
//                }
//                else{
//                    resul= -1;
//                }
//	}catch(Exception e){               
//		System.out.println(e.getMessage());//Este c�digo se ejecuta cuando se produce una excepci�n 
//                resul =-1;
//	}finally{
//		if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
//		if (st != null) try { st.close(); } catch (SQLException ignore) {}
//        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}        
//	}
//        return resul;
//    }
	
	public int confirmarLogin(String user, String password){
        //int res = 0;
		int resul=-1;
		UserFunctions userFunction = new UserFunctions();
//		Log.d("Button", "Login");
		JSONObject json = userFunction.loginUser(user, password);
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				String uid = json.getString(KEY_UID);
				if(Integer.parseInt(res) == 1){					
					resul=new Integer(uid.toString());

					// user successfully logged in							
					JSONObject json_user = json.getJSONObject("user");						
					
				}else{
					// Error in login
					resul=-1;
//					loginErrorMsg.setText("Incorrect username/password");
				}
			}
			else{
				resul=-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			resul=-1;
		}
		
        return resul;
    }
	
	public DatosEmpleado buscarDatosEmpleado(int idempleado){
		DatosEmpleado datChofer = new DatosEmpleado(); 
		
		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            sql = "select * from empleado where idempleado ="+idempleado;
            
            rs = st.executeQuery(sql);
            
            if (rs.next()){
            	datChofer.setNombre(rs.getString("nombre"));//aca iba ruta o unidad
            	datChofer.setApellidos(rs.getString("Apellidos"));
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return datChofer;
	}
	
}
