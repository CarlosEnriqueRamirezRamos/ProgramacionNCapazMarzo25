package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;

public interface IDireccionDAO {

    Result GetByIdDireccion(int IdDireccion);
    Result GetById(int IdDireccion);
    Result DireccionDeleteJPA(int IdDireccion);
}
