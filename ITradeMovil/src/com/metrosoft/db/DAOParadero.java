package com.metrosoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.osmdroid.util.GeoPoint;

import com.metrosoft.model.Cliente;

public class DAOParadero extends DAOConexion{
//	public List<Incidencia> getAllIncidencias() {
    private final float factor=1000000;
	public DAOParadero(){
		super();
	}
	public List<Cliente> getAllParaderos(int idRuta) {
		List<Cliente> listaParadero = new ArrayList<Cliente>();

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
                Cliente paradero = new Cliente();
                paradero.setIdcliente(rs.getInt("idcliente"));
                paradero.setNombre(rs.getString("nombre"));
                paradero.setLatitude(rs.getDouble("latitude"));
                paradero.setLongitude(rs.getDouble("longitude"));                
                paradero.setEstado(1);                
                listaParadero.add(paradero);
            }

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}

		}
		return listaParadero;
	}
	public GeoPoint getPosicionActual(int idUnidad) {
		GeoPoint gPt0 = new GeoPoint(-12071208,-77077569);//pucp
	 	int posxint,posyint;
	    float posxdouble,posydouble;

		ResultSet rs=null;
		try {
			this.conectar();
			st = getConn().createStatement();
            String sql;
            //select distinct p.idparadero, p.nombre, p.posx, p.posy, p.estado, pxr.orden from paradero as p , paraderoxruta as pxr where p.idparadero=pxr.idparadero and pxr.idruta=9 and p.estado=1 order by pxr.orden
//            sql = "select * from \"public\".paradero";
            sql = "select posx, posy from unidad where idunidad="+idUnidad;
            rs = st.executeQuery(sql);
            int rowCount = 0;
            while (rs.next()) {
            	posxdouble=rs.getFloat("posx")*factor;
            	posydouble=rs.getFloat("posy")*factor;
            	posxint=(int)posxdouble;
            	posyint=(int)posydouble;
            	gPt0.setLatitudeE6(posxint);
            	gPt0.setLongitudeE6(posyint);
            	rowCount++;
//                paradero.setIdparadero(rs.getInt("idparadero"));
//                paradero.setNombre(rs.getString("nombre"));            
            }
            ///////////////////////////////////////////////////////////////cambios
            if(rowCount==0){//Si hay un usuario
            	gPt0=null;
            }
			////////////////////////////////////////////////////////////////fin cambios
		} catch (SQLException e) {
			gPt0=null;
			e.printStackTrace();
		}
		finally{
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}		
			if (st != null) try { st.close(); } catch (SQLException ignore) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignore) {}

		}
		return gPt0;
	}
}

