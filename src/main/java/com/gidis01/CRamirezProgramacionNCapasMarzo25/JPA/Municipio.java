package com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.*;

public class Municipio {
    private int IdMunicipio;
    private String Nombre;
    public Estado Estado;

    public int getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(int IdMunicipio) {
        this.IdMunicipio = IdMunicipio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Estado getEstado() {
        return Estado;
    }

    public void setEstado(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Estado Estado) {
        this.Estado = Estado;
    }
    
    
}
