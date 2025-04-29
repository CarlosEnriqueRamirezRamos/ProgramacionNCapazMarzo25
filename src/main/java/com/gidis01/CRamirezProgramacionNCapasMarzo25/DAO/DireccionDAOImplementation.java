package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Colonia;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Direccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Estado;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Municipio;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Pais;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired //Conexion de JPA
    private EntityManager entityManager;

    @Override
public Result GetByIdDireccion(int IdDireccion) {
    Result result = new Result();

    try {
        jdbcTemplate.execute("{CALL DireccionById(?,?)}",  // Cambiado a DireccionById
                (CallableStatementCallback<Integer>) callableStatement -> {
                    callableStatement.setInt(1, IdDireccion);
                    callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                    callableStatement.execute();

                    ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                    if (resultSet.next()) {
                        Direccion direccion = new Direccion();
                        // Mapeo de campos básicos
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        
                        // Mapeo de Colonia
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        
                        // Mapeo de Municipio
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        
                        // Mapeo de Estado
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        
                        // Mapeo de País
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));

                        result.object = direccion;
                    } else {
                        result.correct = false;
                        result.errorMessage = "No se encontró la dirección con ID: " + IdDireccion;
                    }

                    return 1;
                });
        
        if(result.object != null) {
            result.correct = true;
        }
    } catch (Exception ex) {
        result.correct = false;
        result.errorMessage = ex.getLocalizedMessage();
        result.ex = ex;
    }

    return result;
}

    @Override
    public Result GetById(int IdDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL DireccionById(?,?)",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.setInt(1, IdDireccion);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        if (resultSet.next()) {
                            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                            usuarioDireccion.direccion = new Direccion();
                            usuarioDireccion.direccion.setIdDireccion(IdDireccion);
                            usuarioDireccion.direccion.setCalle(resultSet.getString("Calle"));
                            usuarioDireccion.direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            usuarioDireccion.direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            usuarioDireccion.direccion.Colonia = new Colonia();
                            usuarioDireccion.direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            usuarioDireccion.direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            usuarioDireccion.direccion.Colonia.setNombre(resultSet.getString("CodigoPostal"));
                            usuarioDireccion.direccion.Colonia.Municipio = new Municipio();
                            usuarioDireccion.direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            usuarioDireccion.direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            usuarioDireccion.direccion.Colonia.Municipio.Estado = new Estado();
                            usuarioDireccion.direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            usuarioDireccion.direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            usuarioDireccion.direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            usuarioDireccion.direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            usuarioDireccion.direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            result.object = usuarioDireccion;
                        }
                        return 1;
                    });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();

        try {
            com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion direccion
                    = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion();
            direccion = entityManager.find(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion.class, IdDireccion);

            entityManager.remove(direccion);
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
