package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.metrosoft.model.Pedido;
import com.metrosoft.model.PedidoLinea;


public class DAOPedido extends DAOConexion{

	public DAOPedido(){
		super();
	}
	DAOFuncionesAuxiliares daoFunc = null;
	public List<Pedido> getAllPedidos(int idempleado) {
		List<Pedido> listaPedido = new ArrayList<Pedido>();

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            
//            sql = "select * from pedido where idempleado =" + idempleado+" order by idpedido";
            sql = "select * from pedido p, cliente c where p.idempleado =" + idempleado+" and p.idcliente=c.idcliente order by idpedido";
            
            rs = st.executeQuery(sql);
            
            int cont=0;
            while (rs.next()) {
                if (cont<5){
	            	Pedido pedido = new Pedido();
	                
	                pedido.setIdpedido((rs.getInt("idpedido")));
	                pedido.setFecha(rs.getDate("fecha"));
	                pedido.setIdempleado((rs.getInt("idempleado")));
	                pedido.setIdcliente(rs.getInt("idcliente"));
	                pedido.setNombrecliente(rs.getString("nombre"));
	                listaPedido.add(pedido);
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
		return listaPedido;
	}

	
	public List<Pedido> consultaIncidencia(int idunidad) {
		List<Pedido> listaIncidencia = new ArrayList<Pedido>();

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            
            sql = "select * from \"public\".incidencia where estado = '1' and idunidad ="+ idunidad +" order by fecha desc";
                        
            rs = st.executeQuery(sql);
            
            
            if (rs.next()) {
                Pedido pedido = new Pedido();
                
                pedido.setIdpedido((rs.getInt("idpedido")));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setIdempleado((rs.getInt("idempleado")));
                pedido.setIdcliente(rs.getInt("idcliente"));
//                pedido.setHora((rs.getTime("hora")));
//                pedido.setIdruta((rs.getInt("idunidad")));
//                pedido.setIdtipoincidencia(rs.getInt("idtipoincidencia"));
//                pedido.setIdruta((rs.getInt("idruta")));
                listaIncidencia.add(pedido);
                
                getConn().createStatement().executeUpdate("update \"public\".incidencia set estado = 0 where estado = 1 and idincidencia="+listaIncidencia.get(listaIncidencia.size()-1).getIdpedido());
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
	
	public int insertaPedido(Pedido pedido){
		int resul=-1;
		int idPedido;
		daoFunc = new DAOFuncionesAuxiliares();
//		ResultSet rs=null;
        try{
				this.conectar();
				
				idPedido =daoFunc.generarIdConsecutivo("pedido", "idpedido");
				
				pst = getConn().prepareStatement("insert into pedido values (?,?,?,?)");
				
				pst.setInt(1, idPedido);                               //idPedido
				java.util.Date fechaActual1 = new java.util.Date();
				java.sql.Date fechaActual = new java.sql.Date(fechaActual1.getTime());
				pst.setDate(2, fechaActual);                           //fecha
				pst.setInt(3, pedido.getIdempleado());  //idempleado
							
				pst.setInt(4, pedido.getIdcliente());          //idCliente
				
				pst.executeUpdate();											
				resul=idPedido;
        }catch(Exception e){               
        	System.out.println(e.getMessage());//Este c�digo se ejecuta cuando se produce una excepci�n 
            	resul =-1;
        }finally{
        	//if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
        	if (pst != null) try { st.close(); } catch (SQLException ignore) {}
        	if (conn != null) try { conn.close(); } catch (SQLException ignore) {}        
        }
        return resul;
    }


	public int insertaPedidoLinea(PedidoLinea pedidoLinea) {
		int resul=-1;
//		int idPedido;
		daoFunc = new DAOFuncionesAuxiliares();
//		ResultSet rs=null;
        try{
				this.conectar();
				
				//idPedido =daoFunc.generarIdConsecutivo("pedido", "idpedido");
				
				pst = getConn().prepareStatement("insert into pedidolinea values (?,?,?)");
				
				pst.setInt(1, pedidoLinea.getIdpedido());                    //idPedido
				pst.setInt(2, pedidoLinea.getIdproducto());                  //idProducto
				pst.setInt(3, pedidoLinea.getCantidad());                    //idCantidad				
				pst.executeUpdate();
				resul=1;
        }catch(Exception e){               
        	System.out.println(e.getMessage());//Este c�digo se ejecuta cuando se produce una excepci�n 
            	resul =-1;
        }finally{
        	//if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
        	if (pst != null) try { st.close(); } catch (SQLException ignore) {}
        	if (conn != null) try { conn.close(); } catch (SQLException ignore) {}        
        }
        return resul;
		
	}

}
