package com.itrade.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CLIENTE.
 */
public class Cliente {

    private Long id;
    private Integer IdPersona;
    private Integer IdCliente;
    private String Nombre;
    private String ApePaterno;
    private String Telefono;
    private String ApeMaterno;
    /** Not-null value. */
    private String Razon_Social;
    private String RUC;
    private Double Latitud;
    private Double Longitud;
    private String Direccion;
    private Integer IdCobrador;
    private Integer IdUsuario;
    private String Activo;
    private Double MontoActual;

    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, Integer IdPersona, Integer IdCliente, String Nombre, String ApePaterno, String Telefono, String ApeMaterno, String Razon_Social, String RUC, Double Latitud, Double Longitud, String Direccion, Integer IdCobrador, Integer IdUsuario, String Activo, Double MontoActual) {
        this.id = id;
        this.IdPersona = IdPersona;
        this.IdCliente = IdCliente;
        this.Nombre = Nombre;
        this.ApePaterno = ApePaterno;
        this.Telefono = Telefono;
        this.ApeMaterno = ApeMaterno;
        this.Razon_Social = Razon_Social;
        this.RUC = RUC;
        this.Latitud = Latitud;
        this.Longitud = Longitud;
        this.Direccion = Direccion;
        this.IdCobrador = IdCobrador;
        this.IdUsuario = IdUsuario;
        this.Activo = Activo;
        this.MontoActual = MontoActual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPersona() {
        return IdPersona;
    }

    public void setIdPersona(Integer IdPersona) {
        this.IdPersona = IdPersona;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer IdCliente) {
        this.IdCliente = IdCliente;
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

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getApeMaterno() {
        return ApeMaterno;
    }

    public void setApeMaterno(String ApeMaterno) {
        this.ApeMaterno = ApeMaterno;
    }

    /** Not-null value. */
    public String getRazon_Social() {
        return Razon_Social;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setRazon_Social(String Razon_Social) {
        this.Razon_Social = Razon_Social;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double Latitud) {
        this.Latitud = Latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double Longitud) {
        this.Longitud = Longitud;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public Integer getIdCobrador() {
        return IdCobrador;
    }

    public void setIdCobrador(Integer IdCobrador) {
        this.IdCobrador = IdCobrador;
    }

    public Integer getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(Integer IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getActivo() {
        return Activo;
    }

    public void setActivo(String Activo) {
        this.Activo = Activo;
    }

    public Double getMontoActual() {
        return MontoActual;
    }

    public void setMontoActual(Double MontoActual) {
        this.MontoActual = MontoActual;
    }

}
