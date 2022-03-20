package com.amm.dida.proyectoempresas;

import javafx.util.StringConverter;

public class ConvertidorComboboxEmpresa extends StringConverter<Empresa>{

    @Override
    public String toString(Empresa empresa) {
       
        if(empresa.getNombre() == null){
            return null;
        }
        return empresa.getNombre();
    }

    @Override
    public Empresa fromString(String string) {
        
         return null;
    }
    
}
