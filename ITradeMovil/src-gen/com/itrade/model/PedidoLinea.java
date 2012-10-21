package com.itrade.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PEDIDO_LINEA.
 */
public class PedidoLinea {

    private Long id;
    private Integer IdPedido;
    private Integer IdProducto;
    private Double MontoLinea;
    private Integer Cantidad;

    public PedidoLinea() {
    }

    public PedidoLinea(Long id) {
        this.id = id;
    }

    public PedidoLinea(Long id, Integer IdPedido, Integer IdProducto, Double MontoLinea, Integer Cantidad) {
        this.id = id;
        this.IdPedido = IdPedido;
        this.IdProducto = IdProducto;
        this.MontoLinea = MontoLinea;
        this.Cantidad = Cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPedido() {
        return IdPedido;
    }

    public void setIdPedido(Integer IdPedido) {
        this.IdPedido = IdPedido;
    }

    public Integer getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(Integer IdProducto) {
        this.IdProducto = IdProducto;
    }

    public Double getMontoLinea() {
        return MontoLinea;
    }

    public void setMontoLinea(Double MontoLinea) {
        this.MontoLinea = MontoLinea;
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer Cantidad) {
        this.Cantidad = Cantidad;
    }

}