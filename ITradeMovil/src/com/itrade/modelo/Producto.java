package com.itrade.modelo;

import java.io.Serializable;

public class Producto implements Serializable {
	private Integer idProducto;
	private Integer montoLinea;
	private Integer cantidad;
	
	public Producto(Integer idProducto, Integer montoLinea, Integer cantidad)  {
		super();
		this.idProducto = idProducto;
		this.montoLinea = montoLinea;
		this.cantidad = cantidad;
	}
	
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getMontoLinea() {
		return montoLinea;
	}
	public void setMontoLinea(Integer montoLinea) {
		this.montoLinea = montoLinea;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}
