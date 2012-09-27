package com.metrosoft.model;

public class Cliente {


    private int idcliente;
    private String nombre;
    private String apellidos;
    private Double latitude;
    private Double longitude;
    private Integer estado;


   public Cliente() {
   }

	
   public Cliente(int idcliente) {
       this.idcliente = idcliente;
   }
   public Cliente(int idparadero, String nombre, Double posx, Double posy, Integer estado) {
      this.idcliente = idparadero;
      this.nombre = nombre;
      this.setLatitude(posx);
      this.setLongitude(posy);
      this.estado = estado;
   }
  
   public int getIdcliente() {
       return this.idcliente;
   }
   
   public void setIdcliente(int idparadero) {
       this.idcliente = idparadero;
   }
   public String getNombre() {
       return this.nombre;
   }
   
   public void setNombre(String nombre) {
       this.nombre = nombre;
   }

   public Integer getEstado() {
       return this.estado;
   }
   
   public void setEstado(Integer estado) {
       this.estado = estado;
   }


   public String getApellidos() {
	   return apellidos;
   }


   public void setApellidos(String apellidos) {
	   this.apellidos = apellidos;
   }


public Double getLatitude() {
	return latitude;
}


public void setLatitude(Double latitude) {
	this.latitude = latitude;
}


public Double getLongitude() {
	return longitude;
}


public void setLongitude(Double longitude) {
	this.longitude = longitude;
}

}

