package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOFuncionesAuxiliares extends DAOConexion{

	public DAOFuncionesAuxiliares(){
		super();
	}
	
	public int generarIdConsecutivo(String tabla, String campo_Id) {
		int idConsecutivo=1;
		ResultSet rs=null;
		try {
			
			this.conectar();
			st = getConn().createStatement();
            String sql;
            sql = "select max("+campo_Id+") from "+tabla;
            
            rs = st.executeQuery(sql);
            
            if (rs.next()){ //existe ya un n�mero de Id
            	idConsecutivo = rs.getInt(1) + 1;
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}
		return idConsecutivo;
	}
	
}
