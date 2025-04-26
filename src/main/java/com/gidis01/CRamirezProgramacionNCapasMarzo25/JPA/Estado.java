package com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.*;

public class Estado {
    private int idEstado;
    private int Nombre;
    public Pais pais;

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getNombre() {
        return Nombre;
    }

    public void setNombre(int Nombre) {
        this.Nombre = Nombre;
    }

    public com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais getPais() {
        return pais;
    }

    public void setPais(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais pais) {
        this.pais = pais;
    }
    
    
}
