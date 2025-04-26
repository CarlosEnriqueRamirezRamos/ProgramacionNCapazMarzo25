package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.*;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Estado;
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
public class EstadoDAOImplemetation implements IEstadoDAO {

    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
public Result GetAll(int IdPais) {
    Result result = new Result();

    try {
        jdbcTemplate.execute("CALL EstadoGetAll(?, ?)", (CallableStatementCallback<Integer>) callableStatement -> {
            
            // Registra el parámetro de entrada (IdPais)
            callableStatement.setInt(1, IdPais);  // El primer parámetro es el IdPais

            // Registra el parámetro de salida (REF_CURSOR)
            callableStatement.registerOutParameter(2, Types.REF_CURSOR);  // El segundo parámetro es el cursor de salida

            // Ejecuta el procedimiento
            callableStatement.execute();

            // Obtén el ResultSet del parámetro de salida (REF_CURSOR)
            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

            result.objects = new ArrayList<>();
            while (resultSet.next()) {
                Estado estado = new Estado();

                // Mapea los datos del ResultSet a la entidad Estado
                estado.setIdEstado(resultSet.getInt("IdEstado"));
                estado.setNombre(resultSet.getString("Nombre"));

                // Añade el Estado a la lista de resultados
                result.objects.add(estado);
            }

            return 1;
        });

        result.correct = true;
    } catch (Exception ex) {
        result.correct = false;
        result.errorMessage = ex.getLocalizedMessage();
        result.ex = ex;
    }

    return result;
}


@Override
    public Result GetAllJPA(int IdPais){
        com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais paisJPA = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais();
        TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais> queryPais = entityManager.createQuery("FROM Estado WHERE Pais.Idpais",
                com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais.class);
        List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais> paisesJPA  = queryPais.getResultList();
        paisesJPA =  (List<Pais>) entityManager.find(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Pais.class, IdPais);
        return null;
    }
}
