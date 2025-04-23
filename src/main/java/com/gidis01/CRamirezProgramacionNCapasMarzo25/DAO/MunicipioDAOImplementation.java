package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Municipio;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result MunicipioGetAll(int IdEstado) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL MunicipioGetAll(?,?)",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.setInt(1, IdEstado);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        callableStatement.execute();
                        
                        ResultSet resulSet = (ResultSet) callableStatement.getObject(2);
                        result.objects = new ArrayList<>();
                        while(resulSet.next()){
                            Municipio municipio = new Municipio();
                            municipio.setIdMunicipio(resulSet.getInt("IdMunicipio"));
                            municipio.setNombre(resulSet.getNString("Nombre"));
                            result.objects.add(municipio);
                        }
                        return 1;
                        
                    });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }
    
}
