package com.itrade.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itrade.model.Persona;
import com.itrade.controller.pedidos.UsuarioFunctions;


public class DAOPersona {
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_IDUSUARIO = "IdUsuario";
	private static String KEY_PERSONA = "persona";
	private static String KEY_NOMBRE = "Nombre";
	private static String KEY_PASSWORD = "Password";
	private static String KEY_APEPATERNO = "ApePaterno";
	private static String KEY_APEMATERNO = "ApeMaterno";
	private static String KEY_IDPERFIL = "IdPerfil";
	private static String KEY_IDPERSONA = "IdPersona";
	private static String KEY_ACTIVO = "Activo";
	private static String KEY_DNI = "DNI";
	private static String KEY_FECHNAC = "FechNac";
	private static String KEY_IDDISTRITO = "IdDistrito";
	private static String KEY_EMAIL = "Email";
	private static String KEY_TELEFONO = "Telefono";
	private static String KEY_PERSONAS = "personas";
    JSONArray personas = null;
	
	public DAOPersona(){
		super();
	}
	
	public Persona buscarDatosPersona(int idusuario){
		Persona datEmpl = new Persona(); 
		UsuarioFunctions userFunction = new UsuarioFunctions();
//		Log.d("Button", "Login");
		JSONObject json = userFunction.obtenerDatosEmpleado(idusuario);
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
//				String eid = json.getString(KEY_EID);
				if(Integer.parseInt(res) == 1){					
                    JSONArray empleadoObj = json.getJSONArray(KEY_PERSONA); // JSON Array

                    // get first product object from JSON Array
                    JSONObject json_empleado = empleadoObj.getJSONObject(0);
                    datEmpl.setNombre(json_empleado.getString(KEY_NOMBRE));
                    datEmpl.setApePaterno(json_empleado.getString(KEY_APEPATERNO));

					// datos de empleados obtenidos satisfactoriamente											
					
				}else{
					// Error al traer el empleado
					datEmpl=null;
//					loginErrorMsg.setText("No se encontro el empleado");
				}
			}
			else{
				datEmpl=null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			datEmpl=null;
		}

		return datEmpl;
	}
	public List<Persona> getAllPersonas() {
		List<Persona> listaPersona = new ArrayList<Persona>();
		UsuarioFunctions userFunction = new UsuarioFunctions();
		JSONObject json = userFunction.getAllPersonas();
		
		try {
			if (json.getString(KEY_SUCCESS) != null) {				
				String res = json.getString(KEY_SUCCESS);
				
				if(Integer.parseInt(res) == 1){					
					personas = json.getJSONArray(KEY_PERSONAS);						
//					JSONObject json_user = json.getJSONObject("user");
                    for (int i = 0; i < personas.length(); i++) {
                        JSONObject json_persona = personas.getJSONObject(i);
                        
                        
                      Persona persona = new Persona();
                      // Storing each json item in variable 
                      String strIdPersona=json_persona.getString(KEY_IDPERSONA);
                      String strNombre=json_persona.getString(KEY_NOMBRE);
                      String strApePaterno=json_persona.getString(KEY_APEPATERNO);
                      String strApeMaterno=json_persona.getString(KEY_APEMATERNO);
                      String strTelefono=json_persona.getString(KEY_TELEFONO);
                      String strEmail=json_persona.getString(KEY_EMAIL);
                      String strActivo=json_persona.getString(KEY_ACTIVO);
                      String strFechNac=json_persona.getString(KEY_FECHNAC);
                      String strDNI=json_persona.getString(KEY_DNI);
                      
	                  Date fechaNacimiento=null;
	                  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                  try {	                	  
	                	  fechaNacimiento = dateFormat.parse(strFechNac);
	                  } catch (ParseException e) {						
						e.printStackTrace();
	                  }

                      
                      persona.setId(new Long(strIdPersona));
                      persona.setNombre(strNombre);
                      persona.setApePaterno(strApePaterno);                   

                      persona.setApeMaterno(strApeMaterno);
                      persona.setTelefono(strTelefono);
                      persona.setEmail(strEmail);
                      persona.setActivo(strActivo);
                      persona.setFechNac(fechaNacimiento);
                      persona.setDNI(strDNI);                   
                      // adding object to ArrayList
                      listaPersona.add(persona);
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
		return listaPersona;
	}
}
