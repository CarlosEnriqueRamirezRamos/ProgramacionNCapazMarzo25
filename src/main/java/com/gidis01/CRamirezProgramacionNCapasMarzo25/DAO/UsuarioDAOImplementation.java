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
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // logica de base de datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired //Inyección dependencias (field, contructor, setter)
    public JdbcTemplate jdbcTemplate; // conexión directa

    @Autowired //Conexion de JPA
    private EntityManager EntityManager;

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
    public Result GetAllJPA() {
        //  Esto es lenguaje JPQL
        Result result = new Result();
        try {
            TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario> queryUsuarios = EntityManager.createQuery("FROM Usuario", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class);
            List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario> usuarios = queryUsuarios.getResultList();
            result.objects = new ArrayList<>();
            for (com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario usuario : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();
                usuarioDireccion.usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.usuario.setUserName(usuario.getUsername());
                usuarioDireccion.usuario.setNombre(usuario.getNombre());
                usuarioDireccion.usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.usuario.setTelefono(usuario.getTelfono());
                usuarioDireccion.usuario.setEmail(usuario.getEmail());
                usuarioDireccion.usuario.setPassword(usuario.getPassword());
                usuarioDireccion.usuario.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioDireccion.usuario.setCurp(usuario.getCurp());
                usuarioDireccion.usuario.setCelular(usuario.getCurp());
                usuarioDireccion.usuario.setSexo(usuario.getSexo());
                usuarioDireccion.usuario.setStatus(usuario.getStatus());
                usuarioDireccion.usuario.setImagen(usuario.getImagen());

                TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion> queryDireccion = EntityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.direcciones = new ArrayList<>();
                for (com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());

                    usuarioDireccion.direcciones.add(direccion);
                }
                result.objects.add(usuarioDireccion);

            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    
    @Transactional //La transaccion no estaba activa porque faltaba eso!!
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario usuarioJPA =
                    new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario();
            //Insercion de Usuario
            usuarioJPA.setUsername(usuarioDireccion.usuario.getUserName());
            usuarioJPA.setNombre(usuarioDireccion.usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.usuario.getApellidoMaterno());
            usuarioJPA.setTelfono(usuarioDireccion.usuario.getTelefono());
            usuarioJPA.Rol = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Rol();
            usuarioJPA.Rol.setIdRol(usuarioDireccion.usuario.Rol.getIdRol());
            usuarioJPA.setEmail(usuarioDireccion.usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.usuario.getPassword());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.usuario.getFechaNacimiento());
            usuarioJPA.setCurp(usuarioDireccion.usuario.getCurp());
            usuarioJPA.setCelular(usuarioDireccion.usuario.getCelular());
            usuarioJPA.setSexo(usuarioDireccion.usuario.getSexo());
            usuarioJPA.setImagen(usuarioDireccion.usuario.getImagen());
            usuarioJPA.setStatus(usuarioDireccion.usuario.getStatus());
            EntityManager.persist(usuarioJPA);
            
            //insercion de Direccion
            com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion direccionJPA
                    = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.direccion.getNumeroExterior());
            direccionJPA.Colonia = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.direccion.Colonia.getIdColonia());
            direccionJPA.Usuario = usuarioJPA;
            EntityManager.persist(direccionJPA);
            
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result DeleteJPA(){
        Result result = new Result();
        
        return result;
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
                            usuarioDireccion.usuario.setStatus(resultSet.getInt("Status"));
                            usuarioDireccion.usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                            usuarioDireccion.usuario.setEmail(resultSet.getString("Email"));
                            usuarioDireccion.usuario.setPassword(resultSet.getString("Password"));
                            usuarioDireccion.usuario.setCurp(resultSet.getString("Curp"));
                            usuarioDireccion.usuario.setCelular(resultSet.getString("Celular"));
                            usuarioDireccion.usuario.setSexo(resultSet.getString("Sexo"));
                            usuarioDireccion.usuario.setPassword(resultSet.getString("Password"));
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

    @Override
    public Result UpdateStatus(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{call UpdateStatusById(?,?)}",
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setInt(1, usuario.getIdUsuario());
                        callableStatement.setInt(2, usuario.getStatus());

                        boolean REsults = callableStatement.execute();
                        result.correct = true;
                        return true;
                    }
            );
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error al actualizar usuario: " + ex.getMessage();
            result.ex = ex;
            // Log detallado del error
            ex.printStackTrace();
        }
        return result;
    }
}
