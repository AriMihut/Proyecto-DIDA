package com.amm.dida.proyectoempresas;

import javafx.util.StringConverter;

public class ConvertidorComboboxDepartamento extends StringConverter<Departamento>{

    @Override
    public String toString(Departamento departamento) {
        if(departamento.getNombreDpto() == null){
            return null;
        }
        return departamento.getNombreDpto();
    }

    @Override
    public Departamento fromString(String string) {
        System.out.println("Na...");
        return null;
    }
    
}
