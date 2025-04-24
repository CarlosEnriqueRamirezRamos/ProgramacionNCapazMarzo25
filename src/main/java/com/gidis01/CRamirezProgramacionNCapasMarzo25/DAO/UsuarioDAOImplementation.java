package com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Usuario;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Colonia;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Direccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Estado;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Municipio;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Pais;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Rol;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // logica de base de datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired //Inyección dependencias (field, contructor, setter)
    public JdbcTemplate jdbcTemplate; // conexión directa

    @Autowired

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioGetAll(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {

                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {

                            int IdUsuario = resultSet.getInt("IdUsuario");
                            if (!result.objects.isEmpty() && IdUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).usuario.getIdUsuario()) {
                                // Aquí deberías agregar la dirección a alguna lista o procesarla.
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("IdPais"));
                            } else {
                                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                                usuarioDireccion.usuario = new Usuario();
                                usuarioDireccion.usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuarioDireccion.usuario.setUserName(resultSet.getString("UserName"));
                                usuarioDireccion.usuario.setNombre(resultSet.getString("Nombre"));
                                usuarioDireccion.usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuarioDireccion.usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuarioDireccion.usuario.setImagen(resultSet.getString("Imagen"));
                                usuarioDireccion.usuario.setEmail(resultSet.getString("Email"));
                                usuarioDireccion.usuario.setPassword(resultSet.getString("Password"));
                                usuarioDireccion.usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuarioDireccion.usuario.setSexo(resultSet.getString("Sexo"));
                                usuarioDireccion.usuario.setTelefono(resultSet.getString("Telefono"));
                                usuarioDireccion.usuario.setCelular(resultSet.getString("Celular"));
                                usuarioDireccion.usuario.setCurp(resultSet.getString("Curp"));
                                usuarioDireccion.usuario.setStatus(resultSet.getInt("Status"));
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                usuarioDireccion.direcciones = new ArrayList<>();
                                usuarioDireccion.direcciones.add(direccion);
                                result.objects.add(usuarioDireccion);
                            }
                        }
                        result.correct = true;
                        return 1; // Asegúrate de retornar algo si el callback lo exige.
                    });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }
        return result; // Devuelve el resultado al final del método.
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{Call UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.usuario.getUserName());
                callableStatement.setString(2, usuarioDireccion.usuario.getNombre());
                callableStatement.setString(3, usuarioDireccion.usuario.getApellidoPaterno());
                callableStatement.setString(4, usuarioDireccion.usuario.getApellidoMaterno());
                callableStatement.setString(5, usuarioDireccion.usuario.getTelefono());
                callableStatement.setInt(6, usuarioDireccion.usuario.Rol.getIdRol());
                callableStatement.setString(7, usuarioDireccion.usuario.getEmail());
                callableStatement.setString(8, usuarioDireccion.usuario.getPassword());
                callableStatement.setDate(9, new java.sql.Date(usuarioDireccion.usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(10, usuarioDireccion.usuario.getCurp());
                callableStatement.setString(11, usuarioDireccion.usuario.getCelular());
                callableStatement.setString(12, usuarioDireccion.usuario.getSexo());
                callableStatement.setString(13, usuarioDireccion.direccion.getCalle());
                callableStatement.setString(14, usuarioDireccion.direccion.getNumeroInterior());
                callableStatement.setString(15, usuarioDireccion.direccion.getNumeroExterior());
                callableStatement.setInt(16, usuarioDireccion.direccion.Colonia.getIdColonia());
                callableStatement.setString(17, usuarioDireccion.usuario.getImagen());

                int rowAffected = callableStatement.executeUpdate();
                result.correct = rowAffected > 0 ? true : false;

                return 1;

            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result direccionesByIdUsuario(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{Call UsuarioDireccionById(?, ?, ?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);

                callableStatement.execute();

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);

                if (resultSetUsuario.next()) {
                    usuarioDireccion.usuario = new Usuario();
                    usuarioDireccion.usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.usuario.setImagen(resultSetUsuario.getString("Imagen"));
                    usuarioDireccion.usuario.setUserName(resultSetUsuario.getString("UserName"));
                    usuarioDireccion.usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuarioDireccion.usuario.Rol = new Rol();
                    usuarioDireccion.usuario.Rol.setIdRol(resultSetUsuario.getInt("IdRoll"));
                    usuarioDireccion.usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuarioDireccion.usuario.setCurp(resultSetUsuario.getString("Curp"));
                    usuarioDireccion.usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuarioDireccion.usuario.setSexo(resultSetUsuario.getString("Sexo"));

                }
                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);

                usuarioDireccion.direcciones = new ArrayList();

                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSetDireccion.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultSetDireccion.getString("NombreMunicipio"));
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSetDireccion.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSetDireccion.getString("NombreEstado"));
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSetDireccion.getInt("IdPais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSetDireccion.getString("NombrePais"));

                    usuarioDireccion.direcciones.add(direccion);
                }
                result.object = usuarioDireccion;
                result.correct = true;
                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL UsuarioById(?,?)",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.setInt(1, IdUsuario);
                        callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                        callableStatement.execute();
                        ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                        if (resultSet.next()) {
                            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                            usuarioDireccion.usuario = new Usuario();
                            usuarioDireccion.usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                            usuarioDireccion.usuario.setImagen(resultSet.getString("Imagen"));
                            usuarioDireccion.usuario.setUserName(resultSet.getString("UserName"));
                            usuarioDireccion.usuario.setNombre(resultSet.getString("NombreUsuario"));
                            usuarioDireccion.usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                            usuarioDireccion.usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                            usuarioDireccion.usuario.setTelefono(resultSet.getString("Telefono"));
                            usuarioDireccion.usuario.setTelefono(resultSet.getString("Telefono"));
                            usuarioDireccion.usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                            usuarioDireccion.usuario.setEmail(resultSet.getString("Email"));
                            usuarioDireccion.usuario.setPassword(resultSet.getString("Password"));
                            usuarioDireccion.usuario.setCurp(resultSet.getString("Curp"));
                            usuarioDireccion.usuario.setCelular(resultSet.getString("Celular"));
                            usuarioDireccion.usuario.setSexo(resultSet.getString("Sexo"));
                            usuarioDireccion.usuario.Rol = new Rol();
                            usuarioDireccion.usuario.Rol.setIdRol(resultSet.getInt("IdRoll"));
                            usuarioDireccion.usuario.Rol.setNombre("NombreRol");
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

    @Override
public Result Update(Usuario usuario) {
    Result result = new Result();

    try {
        jdbcTemplate.execute(
            "{call UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", 
            (CallableStatementCallback<Boolean>) callableStatement -> {
                // Configurar parámetros según el procedimiento almacenado
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getUserName());
                callableStatement.setString(3, usuario.getNombre());
                callableStatement.setString(4, usuario.getApellidoPaterno());
                callableStatement.setString(5, usuario.getApellidoMaterno());
                callableStatement.setInt(6, usuario.getRol().getIdRol());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setDate(9, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(10, usuario.getCurp());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.getSexo());
                callableStatement.setString(13, usuario.getTelefono());
                callableStatement.setString(14, usuario.getImagen());

                // Ejecutar el procedimiento
                boolean hasResults = callableStatement.execute();
                
                // Verificar si se actualizó correctamente
                result.correct = true;
                return true;
            }
        );
    } catch (Exception ex) {
        result.correct = false;
        result.errorMessage = "Error al actualizar usuario: " + ex.getMessage();
        result.ex = ex;
        // Log detallado del error
        System.err.println("Error en UsuarioDAO.Update:");
        ex.printStackTrace();
    }

    return result;
}
}
