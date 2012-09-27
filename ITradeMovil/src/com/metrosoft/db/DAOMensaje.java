package com.metrosoft.db;

import java.sql.SQLException;

import com.metrosoft.model.Mensaje;

public class DAOMensaje extends DAOConexion{
	
	public DAOMensaje(){
		super();
	}
	
	DAOFuncionesAuxiliares daoFunc = null;
	
	public Boolean insertaMsj(Mensaje mensaje, int idunidad){
		Boolean resul=false;
		int idMensaje;
		daoFunc = new DAOFuncionesAuxiliares();
//		ResultSet rs=null;
        try{
				this.conectar();
				
				idMensaje =daoFunc.generarIdConsecutivo("mensaje", "idmensaje");
				
				pst = getConn().prepareStatement("insert into \"public\".mensaje values (?,?,?,?,?,?)");
				
				pst.setInt(1, idMensaje);                               //idMensaje
				pst.setString(2, mensaje.getDescripcion().toString());  //descripcion
				
				java.util.Date fechaActual1 = new java.util.Date();
				java.sql.Date fechaActual = new java.sql.Date(fechaActual1.getTime());
				pst.setDate(3, fechaActual);                           //fecha
				
				pst.setInt(4, mensaje.getIdTipoIncidencia());          //idtipoIncidencia
				pst.setInt(5, idunidad);                               //idUnidad
				pst.setInt(6, 1);                                      //estado
				
				pst.executeUpdate();											
				resul=true;
        }catch(Exception e){               
        	System.out.println(e.getMessage());//Este código se ejecuta cuando se produce una excepción 
            	resul =false;
        }finally{
        	//if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
        	if (pst != null) try { st.close(); } catch (SQLException ignore) {}
        	if (conn != null) try { conn.close(); } catch (SQLException ignore) {}        
        }
        return resul;
    }

}
