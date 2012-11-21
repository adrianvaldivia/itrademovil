package com.itrade.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CONTACTO.
 */
public class Contacto {

    private Long id;
    private Long IdPersona;
    private Long IdUsuario;
    private String Nombre;
    private String ApePaterno;
    private String ApeMaterno;
    private String Activo;
    private String Telefono;
    private String Email;

    public Contacto() {
    }

    public Contacto(Long id) {
        this.id = id;
    }

    public Contacto(Long id, Long IdPersona, Long IdUsuario, String Nombre, String ApePaterno, String ApeMaterno, String Activo, String Telefono, String Email) {
        this.id = id;
        this.IdPersona = IdPersona;
        this.IdUsuario = IdUsuario;
        this.Nombre = Nombre;
        this.ApePaterno = ApePaterno;
        this.ApeMaterno = ApeMaterno;
        this.Activo = Activo;
        this.Telefono = Telefono;
        this.Email = Email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(Long IdPersona) {
        this.IdPersona = IdPersona;
    }

    public Long getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Long IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApePaterno() {
        return ApePaterno;
    }

    public void setApePaterno(String ApePaterno) {
        this.ApePaterno = ApePaterno;
    }

    public String getApeMaterno() {
        return ApeMaterno;
    }

    public void setApeMaterno(String ApeMaterno) {
        this.ApeMaterno = ApeMaterno;
    }

    public String getActivo() {
        return Activo;
    }

    public void setActivo(String Activo) {
        this.Activo = Activo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}