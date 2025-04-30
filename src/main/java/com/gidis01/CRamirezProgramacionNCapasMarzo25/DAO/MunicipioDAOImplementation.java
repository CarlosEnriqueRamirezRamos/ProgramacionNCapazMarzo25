package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Municipio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired //Conexion de JPA
    private EntityManager EntityManager;

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
                        while (resulSet.next()) {
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

    @Override
    public Result MunicipioGetAllJPA(int IdEstado) {
        Result result = new Result();
        try {
            TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Municipio> queryMunicipios = EntityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idEstado", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Municipio.class);
            List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Municipio> municipios = queryMunicipios.getResultList();
            result.objects = new ArrayList<>();

            for (com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Municipio municipioJPA : municipios) {
                Municipio municipio = new Municipio();
                municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                municipio.setNombre(municipioJPA.getNombre());

                result.objects.add(municipio);
            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
