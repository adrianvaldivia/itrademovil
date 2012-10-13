package com.itrade.modelo;
//clase encargada de los MenuItem
public class ItemMenu {
	protected long id;
	protected String rutaImagen;
	protected String nombre;
	protected String tipo;
	
	
	public ItemMenu() {
		this.nombre = "";
		this.tipo = "";
		this.rutaImagen = "";
	}
	
	public ItemMenu(long id, String nombre, String tipo) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.rutaImagen = "";
	}
	
	public ItemMenu(long id, String nombre, String tipo, String rutaImagen) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.rutaImagen = rutaImagen;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRutaImagen() {
		return rutaImagen;
	}
	
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override 
	public String toString() {

		    return this.getNombre();
	  }
}
