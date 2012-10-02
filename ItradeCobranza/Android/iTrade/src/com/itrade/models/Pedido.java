package com.itrade.models;

import java.io.Serializable;

public class Pedido implements Serializable {
	
	private Integer IdPedido;
	private Integer IdCliente;
	private String Nombre;
	private String ApePaterno;
	private String ApeMaterno;
	private String FechaPedido;//Falta convertir a Date!!!
	private Double MontoTotal;
		
	@Override
	public String toString() {
		return "Pedido [IdPedido=" + IdPedido + ", IdCliente=" + IdCliente
				+ ", Nombre=" + Nombre + ", ApePaterno=" + ApePaterno
				+ ", ApeMaterno=" + ApeMaterno + ", FechaPedido=" + FechaPedido
				+ ", MontoTotal=" + MontoTotal + "]";
	}
	public Pedido(Integer idPedido, Integer idCliente, String nombre,
			String apePaterno, String apeMaterno, String fechaPedido,
			Double montoTotal) {
		super();
		IdPedido = idPedido;
		IdCliente = idCliente;
		Nombre = nombre;
		ApePaterno = apePaterno;
		ApeMaterno = apeMaterno;
		FechaPedido = fechaPedido;
		MontoTotal = montoTotal;
	}
	public Integer getIdPedido() {
		return IdPedido;
	}
	public void setIdPedido(Integer idPedido) {
		IdPedido = idPedido;
	}
	public Integer getIdCliente() {
		return IdCliente;
	}
	public void setIdCliente(Integer idCliente) {
		IdCliente = idCliente;
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
	public String getFechaPedido() {
		return FechaPedido;
	}
	public void setFechaPedido(String fechaPedido) {
		FechaPedido = fechaPedido;
	}
	public Double getMontoTotal() {
		return MontoTotal;
	}
	public void setMontoTotal(Double montoTotal) {
		MontoTotal = montoTotal;
	}
	
	
}
