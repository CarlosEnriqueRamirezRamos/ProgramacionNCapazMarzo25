
package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;

public interface IColoniaDAO {
    Result ColoniaGetAll(int IdMunicipio);
    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
}
