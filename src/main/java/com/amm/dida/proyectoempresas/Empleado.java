package com.amm.dida.proyectoempresas;

import java.util.Date;

public class Empleado {
    
    private int id;
    private String nombreEmpleado;
    private Date fechaAlta;
    private String sexo;
    private int idDepartamento;//clave foranea
    private int idEmpresa;//clave foranea
    private boolean esResponsable;

    public Empleado() {
    }

    public Empleado(int id, String nombreEmpleado, Date fechaAlta, String sexo, int idDepartamento, int idEmpresa, boolean esResponsable) {
        this.id = id;
        this.nombreEmpleado = nombreEmpleado;
        this.fechaAlta = fechaAlta;
        this.sexo = sexo;
        this.idDepartamento = idDepartamento;
        this.idEmpresa = idEmpresa;
        this.esResponsable = esResponsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isEsResponsable() {
        return esResponsable;
    }

    public void setEsResponsable(boolean esResponsable) {
        this.esResponsable = esResponsable;
    }

    @Override
    public String toString() {
        return "Empleado{" + ", nombreEmpleado=" + nombreEmpleado + '}';
    }
    
}
