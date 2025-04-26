
package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;

public interface IEstadoDAO {
    Result GetAll(int IdPais);
    Result GetAllJPA(int IdPais);
    
}
