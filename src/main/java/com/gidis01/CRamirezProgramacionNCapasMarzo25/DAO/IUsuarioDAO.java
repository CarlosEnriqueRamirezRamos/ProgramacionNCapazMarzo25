package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Usuario;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;

public interface IUsuarioDAO {
    Result GetAll(); //método abstracto 
    Result Add(UsuarioDireccion usuarioDireccion);
    Result direccionesByIdUsuario(int IdUsuario);
    Result GetById(int IdUsuario);
    Result Update(Usuario usuario);
    Result UpdateStatus(Usuario usuario);
    Result GetAllJPA();
    Result AddJPA();
//    Result Update(int IdUsuario);
}
