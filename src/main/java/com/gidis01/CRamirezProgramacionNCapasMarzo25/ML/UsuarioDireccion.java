package com.gidis01.CRamirezProgramacionNCapasMarzo25.ML;

import java.util.List;
import jakarta.validation.Valid;
        
public class UsuarioDireccion {
    @Valid
    public Usuario usuario;
    public List<Direccion> direcciones;
    public Direccion direccion;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    
    
}
