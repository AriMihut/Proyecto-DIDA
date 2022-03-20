package com.amm.dida.proyectoempresas;

public class Departamento { 
    
    private int id; 
    public enum AmbitoDpto {Finanzas, Informática, Logística, Formación};
    public AmbitoDpto ambitoDpto;
    private String nombreDpto;
    private double presupuesto;

    public Departamento() {}

    public Departamento(int id, AmbitoDpto ambitoDpto, String nombreDpto, double presupuesto) {
        this.id = id;
        this.ambitoDpto = ambitoDpto;
        this.nombreDpto = nombreDpto;
        this.presupuesto = presupuesto;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public AmbitoDpto getAmbitoDpto() {
        return ambitoDpto;
    }

    public void setAmbitoDpto(AmbitoDpto ambitoDpto) {
        this.ambitoDpto = ambitoDpto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreDpto() {
        return nombreDpto;
    }

    public void setNombreDpto(String nombreDpto) {
        this.nombreDpto = nombreDpto;
    }

    @Override
    public String toString() {
        return nombreDpto;
    }

}