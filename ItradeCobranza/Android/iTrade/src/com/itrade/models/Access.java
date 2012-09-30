package com.itrade.models;

import java.io.Serializable;

public class Access implements Serializable{
	private String descripcion;	
	private String id_contact;	
	@Override
	public String toString() {
		return "Acceso->" + descripcion;
	}
		
	public Access(String descripcion, String id_contact) {
		super();
		this.descripcion = descripcion;
		this.id_contact = id_contact;
	}
		
	public String getId_contact() {
		return id_contact;
	}
	public void setId_contact(String id_contact) {
		this.id_contact = id_contact;
	}	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
