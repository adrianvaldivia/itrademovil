package com.itrade.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table NOTIFICACION.
 */
public class Notificacion {

    private Long id;
    private Integer IdNotificacion;
    private Integer IdUsuario;
    private String Fecha;

    public Notificacion() {
    }

    public Notificacion(Long id) {
        this.id = id;
    }

    public Notificacion(Long id, Integer IdNotificacion, Integer IdUsuario, String Fecha) {
        this.id = id;
        this.IdNotificacion = IdNotificacion;
        this.IdUsuario = IdUsuario;
        this.Fecha = Fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdNotificacion() {
        return IdNotificacion;
    }

    public void setIdNotificacion(Integer IdNotificacion) {
        this.IdNotificacion = IdNotificacion;
    }

    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

}
