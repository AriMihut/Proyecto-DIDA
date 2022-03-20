
package com.amm.dida.proyectoempresas;

public class Empresa {
    
    private int id;
    private String nombre;
    private String email;
    private int telefono;
    private String ambito;
    private boolean internacional;
    private String mercanciaTransportada;

    public Empresa() {
    }

    public Empresa(int id, String nombre, String email, int telefono, String ambito, boolean internacional, String mercanciaTransportada) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ambito = ambito;
        this.internacional = internacional;
        this.mercanciaTransportada = mercanciaTransportada;
    }
    
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
    
     public int getTelefono() {
        return telefono;
    }
     
     public String getAmbito(){
         return ambito;
     }
     
     public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
     
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
      
    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public boolean getInternacional() {
        return internacional;
    }

    public void setInternacional(boolean internacional) {
        this.internacional = internacional;
    } 

    public String getMercanciaTransportada() {
        return mercanciaTransportada;
    }

    public void setMercanciaTransportada(String mercanciaTransportada) {
        this.mercanciaTransportada = mercanciaTransportada;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
