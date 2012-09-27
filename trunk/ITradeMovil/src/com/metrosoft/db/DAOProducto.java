package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.metrosoft.model.Producto;


public class DAOProducto extends DAOConexion{

	public DAOProducto(){
		super();
	}
	
	public List<Producto> getAllProductos(int idempleado) {
		List<Producto> listaIncidencia = new ArrayList<Producto>();

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            
            sql = "select * from producto order by idproducto";
            
            rs = st.executeQuery(sql);
            
            int cont=0;
            while (rs.next()) {
                if (cont<5){
	            	Producto producto = new Producto();
	                
	                producto.setIdproducto((rs.getInt("idproducto")));
	                producto.setNombre(rs.getString("nombre"));
//	                pedido.setIdempleado((rs.getInt("idempleado")));
//	                pedido.setIdcliente(rs.getInt("idcliente"));
//	                pedido.setHora((rs.getTime("hora")));
//	                pedido.setIdruta((rs.getInt("idunidad")));
//	                pedido.setIdtipoincidencia(rs.getInt("idtipoincidencia"));
//	                pedido.setIdruta((rs.getInt("idruta")));
	                listaIncidencia.add(producto);
                }
                else break;
                cont++;
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}

		}
		return listaIncidencia;
	}

	
	public List<Producto> consultaIncidencia(int idunidad) {
		List<Producto> listaIncidencia = new ArrayList<Producto>();

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            
            sql = "select * from \"public\".incidencia where estado = '1' and idunidad ="+ idunidad +" order by fecha desc";
                        
            rs = st.executeQuery(sql);
            
            
            if (rs.next()) {
                Producto producto = new Producto();
                
                producto.setIdproducto((rs.getInt("idproducto")));
                producto.setNombre(rs.getString("nombre"));
//                pedido.setIdempleado((rs.getInt("idempleado")));
//                pedido.setIdcliente(rs.getInt("idcliente"));
//                pedido.setHora((rs.getTime("hora")));
//                pedido.setIdruta((rs.getInt("idunidad")));
//                pedido.setIdtipoincidencia(rs.getInt("idtipoincidencia"));
//                pedido.setIdruta((rs.getInt("idruta")));
                listaIncidencia.add(producto);
                
                getConn().createStatement().executeUpdate("update \"public\".incidencia set estado = 0 where estado = 1 and idincidencia="+listaIncidencia.get(listaIncidencia.size()-1).getIdproducto());
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}

		}
		return listaIncidencia;
	}
	
}
