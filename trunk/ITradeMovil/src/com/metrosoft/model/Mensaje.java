package com.metrosoft.model;

import java.sql.Date;

public class Mensaje {

	private int idMensaje;
    private int idTipoIncidencia;
    private int idUnidad;
    private String descripcion;
    private Date fecha;
    
    public Mensaje() {
    }

 	public Mensaje(int idMensaje, int idTipoIncidencia, int idUnidad, String descripcion, Date fecha) {
       this.idMensaje = idMensaje;
       this.idTipoIncidencia = idTipoIncidencia;
       this.idUnidad = idUnidad;
       this.descripcion = descripcion;
       this.fecha = fecha;
    }
 	
    public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public int getIdTipoIncidencia() {
		return idTipoIncidencia;
	}

	public void setIdTipoIncidencia(int idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}

	public int getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
}