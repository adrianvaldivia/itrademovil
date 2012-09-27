package com.metrosoft.model;

public class Empleado  {


    private int idempleado;
    private String nombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private String dni;

   public Empleado() {
   }

	
   public Empleado(int idempleado) {
       this.idempleado = idempleado;
   }
   public Empleado(int idempleado, String nombre, String apellidopaterno, String apellidomaterno, String dni) {
      this.idempleado = idempleado;
      this.nombre = nombre;
      this.apellidopaterno = apellidopaterno;
      this.apellidomaterno = apellidomaterno;
      this.dni = dni;

   }
  
   public int getIdempleado() {
       return this.idempleado;
   }
   
   public void setIdempleado(int idempleado) {
       this.idempleado = idempleado;
   }
   public String getNombre() {
       return this.nombre;
   }
   
   public void setNombre(String nombre) {
       this.nombre = nombre;
   }
   public String getApellidopaterno() {
       return this.apellidopaterno;
   }
   
   public void setApellidopaterno(String apellidopaterno) {
       this.apellidopaterno = apellidopaterno;
   }
   public String getApellidomaterno() {
       return this.apellidomaterno;
   }
   
   public void setApellidomaterno(String apellidomaterno) {
       this.apellidomaterno = apellidomaterno;
   }
   public String getDni() {
       return this.dni;
   }
   
   public void setDni(String dni) {
       this.dni = dni;
   }

}


