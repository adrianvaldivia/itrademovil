package com.itrade.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CATEGORIA.
 */
public class Categoria {

    private Long id;
    private Integer IdCategoria;
    /** Not-null value. */
    private String Descripcion;

    public Categoria() {
    }

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria(Long id, Integer IdCategoria, String Descripcion) {
        this.id = id;
        this.IdCategoria = IdCategoria;
        this.Descripcion = Descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCategoria() {
        return IdCategoria;
    }

    public void setIdCategoria(Integer IdCategoria) {
        this.IdCategoria = IdCategoria;
    }

    /** Not-null value. */
    public String getDescripcion() {
        return Descripcion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

}