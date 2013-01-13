package com.tekton.model;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PREGUNTAX_ALUMNO.
 */
public class PreguntaxAlumno {

    private Long id;
    private Long IdAlumno;
    private Long IdPregunta;
    private Integer Activo;

    public PreguntaxAlumno() {
    }

    public PreguntaxAlumno(Long id) {
        this.id = id;
    }

    public PreguntaxAlumno(Long id, Long IdAlumno, Long IdPregunta, Integer Activo) {
        this.id = id;
        this.IdAlumno = IdAlumno;
        this.IdPregunta = IdPregunta;
        this.Activo = Activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(Long IdAlumno) {
        this.IdAlumno = IdAlumno;
    }

    public Long getIdPregunta() {
        return IdPregunta;
    }

    public void setIdPregunta(Long IdPregunta) {
        this.IdPregunta = IdPregunta;
    }

    public Integer getActivo() {
        return Activo;
    }

    public void setActivo(Integer Activo) {
        this.Activo = Activo;
    }

}