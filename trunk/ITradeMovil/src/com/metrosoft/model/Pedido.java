package com.metrosoft.model;


//import java.sql.Time;
import java.util.Date;

//@DatabaseTable (tableName = "pedido")
public class Pedido {
	//@DatabaseField(generatedId=true)
	private int idpedido;
	
	//@DatabaseField
	private Date fecha;

	//@DatabaseField
	private int idempleado;
		
	//@DatabaseField
	private int idcliente;
	
	private String nombrecliente;
		
	//@DatabaseField
	

	public Pedido (){
	//ORM necesita esto	
	}

	public int getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getIdempleado() {
		return idempleado;
	}

	public void setIdempleado(int idempleado) {
		this.idempleado = idempleado;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombrecliente() {
		return nombrecliente;
	}

	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}
	
//	public Incidencia (String nombre, int idtipoincidencia){
//		this.nombre=nombre;
//		this.idtipoincidencia=idtipoincidencia;
//	}
	
//	public void setIdincidencia(int idincidencia) {
//		this.idincidencia = idincidencia;
//	}
//
//	public int getIdincidencia() {
//		return idincidencia;
//	}

	

}
