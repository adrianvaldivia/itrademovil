package com.metrosoft.model;

public class Usuario {
	private int idusuario;
    private int idEmpleado;
    private int idPerfil;
    private String nombre;
    private String password;
    
    public Usuario(){
    }
    
    public Usuario(int idUsuario, int idEmpleado, int idPerfil, String nombre, String password){
    	this.idusuario=idUsuario;
    	this.idEmpleado=idEmpleado;
    	this.idPerfil=idPerfil;
    	this.nombre=nombre;
    	this.password=password;
    }
    
    public int getIdusuario() {
		return idusuario;
	}
    
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	public int getIdEmpleado() {
		return idEmpleado;
	}
	
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	
	public int getIdPerfil() {
		return idPerfil;
	}
	
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}