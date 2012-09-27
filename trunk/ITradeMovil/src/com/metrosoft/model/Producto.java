package com.metrosoft.model;


//import java.sql.Time;


//@DatabaseTable (tableName = "pedido")
public class Producto {
	//@DatabaseField(generatedId=true)
	private int idproducto;
	
	//@DatabaseField
	private String nombre;

		
	//@DatabaseField
	

	public Producto (){
	//ORM necesita esto	
	}


	public int getIdproducto() {
		return idproducto;
	}


	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}
