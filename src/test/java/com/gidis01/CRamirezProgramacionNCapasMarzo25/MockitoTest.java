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
import java.util.Date;
import java.util.List;
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
        Result result = usuarioDAOImplementation.GetAll();
        if (result.correct) {
            System.out.println("Usuarios encontrados: " + result.objects.size());
            for (Object obj : result.objects) {
                UsuarioDireccion ud = (UsuarioDireccion) obj;
                System.out.println("Usuario: " + ud.usuario.getNombre() + " " + ud.usuario.getApellidoPaterno());
                for (Direccion dir : ud.direcciones) {
                    System.out.println("  Dirección: " + dir.getCalle() + " #" + dir.getNumeroExterior());
                }
            }
        } else {
            System.out.println("Error al obtener usuarios:");
            System.out.println(result.errorMessage);
        }
    }
}
