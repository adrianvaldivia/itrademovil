package com.itrade.models;

import java.io.Serializable;

public class Login implements Serializable { //IMPORTANTE QUE SEA SERIALIZABLE para poder pasarlo a traves del intent
	private String Username;
	private String Nombre;
	private String ApePaterno;
	private String ApeMaterno;
	
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getApePaterno() {
		return ApePaterno;
	}
	public void setApePaterno(String apePaterno) {
		ApePaterno = apePaterno;
	}
	public String getApeMaterno() {
		return ApeMaterno;
	}
	public void setApeMaterno(String apeMaterno) {
		ApeMaterno = apeMaterno;
	}
	public Login(String username, String nombre, String apePaterno,
			String apeMaterno) {
		super();
		Username = username;
		Nombre = nombre;
		ApePaterno = apePaterno;
		ApeMaterno = apeMaterno;
	}
	@Override
	public String toString() {
		return "Login [Username=" + Username + ", Nombre=" + Nombre
				+ ", ApePaterno=" + ApePaterno + ", ApeMaterno=" + ApeMaterno
				+ "]";
	}	
	
}
