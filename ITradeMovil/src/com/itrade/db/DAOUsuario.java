package com.itrade.db;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itrade.model.Usuario;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOUsuario {
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_IDUSUARIO = "IdUsuario";
	private static String KEY_PERSONA = "persona";
	private static String KEY_NOMBRE = "Nombre";
	private static String KEY_PASSWORD = "Password";
	private static String KEY_APELLIDO = "ApePaterno";
	private static String KEY_IDPERFIL = "IdPerfil";
	private static String KEY_IDPERSONA = "IdPersona";
	private static String KEY_ACTIVO = "Activo";
	private static String KEY_IDJERARQUIA = "IdJerarquia";
	private static String KEY_IDZONA = "IdZona";
	private static String KEY_IDDISTRITO = "IdDistrito";
	private static String KEY_IDCIUDAD = "IdCiudad";
	private static String KEY_IDPAIS = "IdPais";
	private static String KEY_USUARIOS = "usuarios";
    JSONArray usuarios = null;
	
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
	
	public int confirmarLogin(String nombre, String password){
        //int res = 0;
		int resul=-1;
		UsuarioFunctions userFunction = new UsuarioFunctions();
//		Log.d("Button", "Login");
		JSONObject json = userFunction.loginUser(nombre, password);
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				String uid = json.getString(KEY_IDUSUARIO);
				if(Integer.parseInt(res) == 1){					
					resul=new Integer(uid.toString());

					// user successfully logged in							
					JSONObject json_user = json.getJSONObject("usuario");						
					
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
	
//	public DatosEmpleado buscarDatosEmpleado(int idusuario){
//		DatosEmpleado datEmpl = new DatosEmpleado(); 
//		
//		ResultSet rs=null;
//		try {
//			this.conectar();
//			st = getConn().createStatement();
//            String sql;
//            sql = "select * from empleado where idempleado ="+idusuario;
//            
//            rs = st.executeQuery(sql);
//            
//            if (rs.next()){
//            	datEmpl.setNombre(rs.getString("nombre"));
//            	datEmpl.setApellidos(rs.getString("Apellidos"));
//            }
//            
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally{
//			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
//			if (st != null) try { st.close(); } catch (SQLException ignore) {}
//            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
//		}
//		return datEmpl;
//	}
	public List<Usuario> getAllUsuarios() {
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json = userFunction.getAllUsuarios();
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				
				if(Integer.parseInt(res) == 1){					
					usuarios = json.getJSONArray(KEY_USUARIOS);						
//					JSONObject json_user = json.getJSONObject("user");
                    for (int i = 0; i < usuarios.length(); i++) {
                        JSONObject json_usuario = usuarios.getJSONObject(i);
                        
                        
                      Usuario usuario = new Usuario();
                      // Storing each json item in variable 
                      String strIdUsuario=json_usuario.getString(KEY_IDUSUARIO);
                      String strIdPerfil=json_usuario.getString(KEY_IDPERFIL);
                      String strIdPersona=json_usuario.getString(KEY_IDPERSONA);
                      String strIdJerarquia=json_usuario.getString(KEY_IDJERARQUIA);
                      String strIdZona=json_usuario.getString(KEY_IDZONA);
                      String strIdDistrito=json_usuario.getString(KEY_IDDISTRITO);
                      String strIdCiudad=json_usuario.getString(KEY_IDCIUDAD);
                      String strIdPais=json_usuario.getString(KEY_IDPAIS);
                      
                      usuario.setId(new Long(strIdUsuario));
                      usuario.setNombre(json_usuario.getString(KEY_NOMBRE));
                      usuario.setPassword(json_usuario.getString(KEY_PASSWORD));                   

                      usuario.setIdPerfil(new Integer(strIdPerfil));
                      usuario.setIdPersona(new Integer(strIdPersona));
                      usuario.setActivo(json_usuario.getString(KEY_ACTIVO));
                      usuario.setIdJerarquia(new Integer(strIdJerarquia));
                      usuario.setIdZona(new Integer(strIdZona));
                      usuario.setIdDistrito(new Integer(strIdDistrito));
                      usuario.setIdCiudad(new Integer(strIdCiudad));
                      usuario.setIdPais(new Integer(strIdPais));                      
                      // adding object to ArrayList
                      listaUsuario.add(usuario);
                    }
					
				}else{
					// Error when trying to getAllUsuarios
//					resul=-1;
//					loginErrorMsg.setText("No hay Usuarios");
				}
			}
			else{
//				resul=-1;
			}
		} catch (JSONException e) {
			e.printStackTrace();			
		}		
		return listaUsuario;
	}
}
