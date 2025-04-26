package com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    private int IdRol;
    
    @Column(name = "nombre")
     private String Nombre;

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    
    
}
