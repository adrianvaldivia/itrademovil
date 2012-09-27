package com.metrosoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOConexion {
    public  String driver;
    public  String name_db;
    public  String user_db;//url
    public  String pass_db;
    public   Connection conn;
    public  Statement st;
    public PreparedStatement pst;
	
    public DAOConexion(){
        //this.driver = "org.postgresql.Driver";
    	this.driver = "com.mysql.jdbc.Driver";
        //this.name_db = "jdbc:postgresql://quilla.lab.inf.pucp.edu.pe:1042/postgres";
        this.name_db = "jdbc:mysql://190.222.214.208:3306/test";//put here your local ip add
        this.user_db = "chichan";
        this.pass_db = "1234"; 		
	}
    public DAOConexion(String driver, String name_db, String user_db, String pass_db){
        this.driver = driver;
        this.name_db = name_db;
        this.user_db = user_db;
        this.pass_db = pass_db;                
    }
    
    public Boolean conectar(){
    	Boolean resul=false;
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resul=false;
        }
        //String url = "jdbc:mysql://192.168.0.4:3306/test?user=chichan&password=1234";
        //String url = "jdbc:mysql://192.168.0.4:3306/test";
        try {
            DriverManager.setLoginTimeout(5);
            conn=(DriverManager.getConnection(this.name_db,this.user_db,this.pass_db));//url
            st=conn.createStatement();
            resul=true;
            //conn = DriverManager.getConnection(name_db, user_db, pass_db);
        } catch (SQLException e) {
            e.printStackTrace();
            resul=false;
        }
        return resul;
    }
	
    public void desconectar() {
        try {
            //Cierro todas las conexiones con la base de datos
            //Automaticamente libero memoria y cierro las conexiones abiertas en el objeto Connection
        	st.close();
            conn.close();
        } catch (Exception e) {
                // Controlo cualquier excepcion generada durante el cierre de la conexion
        }
    }
	public  Connection getConn() {
		return conn;
	}

	public  Statement getSt() {
		return st;
	}
	public PreparedStatement getPst() {
		return pst;
	}
}
