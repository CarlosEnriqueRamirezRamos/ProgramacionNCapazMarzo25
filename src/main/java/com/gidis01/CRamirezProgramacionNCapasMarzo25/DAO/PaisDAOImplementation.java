
package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Pais;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.*;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.*;
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
public class PaisDAOImplementation implements IPaisDAO{

    
    @Autowired // conexión de JPA
    private EntityManager entityManager;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;    
    
    @Override
    public Result PaisGetAll() {
        Result result = new Result();
        try{
            jdbcTemplate.execute("CALL PaisGetAll(?)",
                    (CallableStatementCallback<Integer>) callableStatement ->{
                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();
                        
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();
                        while (resultSet.next()) {
                            Pais pais = new Pais();
                            pais.setIdPais(resultSet.getInt("IdPais"));
                            pais.setNombre(resultSet.getString("Nombre"));
                            
                            result.objects.add(pais);
                        }
                    
                    return 1;
                        
                    });
            result.correct = true;
        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;
        }
        return result;
    }
    
    
    @Override
    public Result PaisGetAllJPA() {
        Result result = new Result();
        try {
            // Consulta JPA para obtener todos los países
            TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais> queryPaises
                    = entityManager.createQuery("FROM Pais", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais.class);

            List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais> paises = queryPaises.getResultList();

            result.objects = new ArrayList<>();// Crear lista de resultados

            for (com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais paisJPA : paises) {// Convertir cada entidad JPA a tu objeto de dominio Pais
//            Pais paisDomain = new Pais();
                com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais paisDomain = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais();
                paisDomain.setIdPais(paisJPA.getIdPais());
                paisDomain.setNombre(paisJPA.getNombre());

                result.objects.add(paisDomain);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result; // Asegúrate de retornar el result, no null
    }
}
