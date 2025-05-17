package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Colonia;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
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
public class ColoniaDAOImplementation implements IColoniaDAO {

    @Autowired //Inyección dependencias (field, contructor, setter)
    public JdbcTemplate jdbcTemplate; // conexión directa

    @Autowired //Conexion de JPA
    private EntityManager EntityManager;

    @Override
    public Result ColoniaGetAll(int IdMunicipio) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL ColoniaGetAll(?,?)",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.setInt(1, IdMunicipio);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        result.objects = new ArrayList<>();
                        while (resultSet.next()) {
                            Colonia colonia = new Colonia();
                            colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            colonia.setNombre(resultSet.getString("Nombre"));
                            colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            result.objects.add(colonia);
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
    public Result ColoniaGetAllJPA(int IdMunicipio) {
        Result result = new Result();
        try {
            // Consulta JPA para obtener colonias por municipio
            TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia> query
                    = EntityManager.createQuery(
                            "FROM Colonia WHERE Municipio.IdMunicipio = :idMunicipio",
                            com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia.class);
            query.setParameter("idMunicipio", IdMunicipio);

            List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia> coloniasJPA = query.getResultList();

            result.objects = new ArrayList<>();
            for (com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia coloniaJPA : coloniasJPA) {
                Colonia colonia = new Colonia();
                colonia.setIdColonia(coloniaJPA.getIdColonia());
                colonia.setNombre(coloniaJPA.getNombre());
                colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                result.objects.add(colonia);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    public Result ColoniaByIdMunicipioJPA(int IdMunicipio) {
        com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia coloniaJPA = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia();
        TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia> queryColonia = 
                EntityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio",
                com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia.class);
        
        List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia> coloniasJPA = queryColonia.getResultList();
        coloniaJPA = EntityManager.find(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia.class, IdMunicipio);
        return null;
    }
}
