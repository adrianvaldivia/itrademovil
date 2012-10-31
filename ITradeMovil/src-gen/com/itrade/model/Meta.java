package com.itrade.model;

public class Meta {
	private double suma;//avance

	private String fechini;//fecha inicio periodo
	private String fechafin;//fecha fin del periodo
	private double meta;//meta	
	private String nombre;//descripcion periodo
	
	public String getFechini() {
		return fechini;
	}

	public void setFechini(String fechini) {
		this.fechini = fechini;
	}

	public String getFechafin() {
		return fechafin;
	}

	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}

	public Meta() {
    }

	public double getSuma() {
		return suma;
	}

	public void setSuma(double suma) {
		this.suma = suma;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
