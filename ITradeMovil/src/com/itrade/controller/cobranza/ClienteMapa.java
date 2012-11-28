package com.itrade.controller.cobranza;

public class ClienteMapa {

	/**
	 * @param args
	 */
	private Integer IdCliente;
	private String Nombre;
	private String ApePaterno;
	private String ApeMaterno;
	private Double Latitud;
	private Double Longitud;
	private Integer CheckIn;
	private String Direccion;
	public ClienteMapa(Integer idCliente, String nombre, String apePaterno,
			String apeMaterno, Double latitud, Double longitud,
			Integer checkIn, String direccion) {
		super();
		IdCliente = idCliente;
		Nombre = nombre;
		ApePaterno = apePaterno;
		ApeMaterno = apeMaterno;
		Latitud = latitud;
		Longitud = longitud;
		CheckIn = checkIn;
		Direccion = direccion;
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
	public Double getLatitud() {
		return Latitud;
	}
	public void setLatitud(Double latitud) {
		Latitud = latitud;
	}
	public Double getLongitud() {
		return Longitud;
	}
	public void setLongitud(Double longitud) {
		Longitud = longitud;
	}
	public Integer getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(Integer checkIn) {
		CheckIn = checkIn;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}	
}
