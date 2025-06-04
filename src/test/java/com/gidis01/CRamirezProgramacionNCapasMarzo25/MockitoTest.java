package com.gidis01.CRamirezProgramacionNCapasMarzo25;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.UsuarioDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Colonia;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Direccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Rol;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Usuario;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.apache.commons.math3.stat.inference.TestUtils.t;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MockitoTest {

    @Mock
    EntityManager entityManager;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    private TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario> queryUsuarios;

    @Mock
    private TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion> queryDirecciones;

    @InjectMocks
    UsuarioDAOImplementation usuarioDAOImplementation;

    @Test
    public void UsuarioMockito() {

        UsuarioDireccion ususDireccion = new UsuarioDireccion();
        ususDireccion.usuario = new Usuario();
        ususDireccion.usuario.setNombre("Petter");
        ususDireccion.usuario.setApellidoPaterno("Parker");
        ususDireccion.usuario.setPassword("12345");
        ususDireccion.usuario.Rol = new Rol();
        ususDireccion.direccion = new Direccion();
        ususDireccion.direccion.setCalle("New York");
        ususDireccion.direccion.Colonia = new Colonia();

        Result result = usuarioDAOImplementation.AddJPA(ususDireccion);

        Assertions.assertTrue(result.correct, "Mi result.correct viene en false");

        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class));
        Mockito.verify(entityManager, Mockito.times(1)).persist(Mockito.any(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion.class));
    }

    @Test
    public void UpdateUsuarioMockito() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(192);
        usuario.setNombre("JesustestEdit");
        usuario.setApellidoPaterno("Domingo");
        usuario.setApellidoMaterno("Peralta");
        usuario.setTelefono("5525896401");
        usuario.setEmail("jdomingo121@outlook.com");
        usuario.setFechaNacimiento(new Date(20122002));
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(2);
        usuario.setUserName("jdomingotest");
        usuario.setPassword("123");
        usuario.setSexo("M");
        usuario.setCelular("5569874201");

        com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario usuarioJPA = new com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario();
        Mockito.when(entityManager.find(
                Mockito.eq(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class),
                Mockito.eq(usuario.getIdUsuario())
        )).thenReturn(usuarioJPA);

        Result result = usuarioDAOImplementation.Update(usuario);
        Mockito.verify(entityManager, Mockito.times(1)).merge(Mockito.any(com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class));

        Assertions.assertNotNull(result, "El objeto del result viene null");
        Assertions.assertNull(result.ex, "Viene una excepcion");
        Assertions.assertNull(result.errorMessage, "Hay un mensaje de error");
        Assertions.assertNull(result.object, "Viene algo en object");
        Assertions.assertNull(result.objects, "Viene algo en objects");
        Assertions.assertTrue(result.correct, "El result.correct es false");
    }

    @Test
    public void GetAll() {

        TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario> queryUsuario = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(
                "FROM Usuario", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class))
                .thenReturn(queryUsuario);
        TypedQuery<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion> queryDirecciones = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(
        "FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion.class))
                .thenReturn(queryDirecciones);
        
        List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario> usuarios = new ArrayList<>();
        Mockito.when(queryUsuarios.getResultList()).thenReturn(usuarios);
        
        List<com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Direccion> direcciones = new ArrayList<>();
        Mockito.when(queryDirecciones.getResultList()).thenReturn(direcciones);
        
                
        Result result = usuarioDAOImplementation.GetAllJPA();
        
        Assertions.assertTrue(result.correct);
        
        Mockito.verify(entityManager, Mockito.times(1)).createQuery("FROM Usuario", com.gidis01.CRamirezProgramacionNCapasMarzo25.JPA.Usuario.class);
    }
}
