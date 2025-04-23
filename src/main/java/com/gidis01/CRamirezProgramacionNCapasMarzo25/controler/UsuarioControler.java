package com.gidis01.CRamirezProgramacionNCapasMarzo25.controler;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.ColoniaDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.DireccionDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.EstadoDAOImplemetation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.MunicipioDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.PaisDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.UsuarioDireccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.UsuarioDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Colonia;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Direccion;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Result;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Usuario;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Rol;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.DAO.RolDAOImplementation;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Estado;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Municipio;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Pais;
import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.ResultFile;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Usuario")
public class UsuarioControler {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    // Model y ModelAttribute

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @Autowired
    private EstadoDAOImplemetation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("listaUsuarios", result.objects);
        System.out.println("Usuarios encontrados: " + result.objects);
        System.out.println("¿Correcto?: " + result.correct);

        return "UsuarioIndex";
    }

    @GetMapping("/Form/{IdUsuario}")
    public String Form(@PathVariable int IdUsuario, Model model) {
        Result resultPais = paisDAOImplementation.PaisGetAll();
        model.addAttribute("listaPais", resultPais.objects); // ✅ aquí agregamos los países

        Result result = new Result();
        if (IdUsuario == 0) {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.Rol = new Rol();
            usuarioDireccion.direccion = new Direccion();

            usuarioDireccion.direccion.Colonia = new Colonia();
            usuarioDireccion.direccion.Colonia.Municipio = new Municipio();
            usuarioDireccion.direccion.Colonia.Municipio.Estado = new Estado();
            usuarioDireccion.direccion.Colonia.Municipio.Estado.Pais = new Pais();
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "FormularioIndex";
        } else {
            System.out.println("Voy a editar");
            result = usuarioDAOImplementation.direccionesByIdUsuario(IdUsuario);
            model.addAttribute("usuarioDirecciones", result.object);
            return "UsuarioDetail";
        }
    }

    @GetMapping("/formEditable")
    public String FormEditable(Model model, @RequestParam int IdUsuario, @RequestParam(required = false) Integer IdDireccion) {
        if (IdDireccion == null) {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementation.GetById(IdUsuario).object;
            usuarioDireccion.direccion = new Direccion();
            usuarioDireccion.direccion.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
        } else if (IdDireccion == 0) {
            //Agregar Direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.direccion = new Direccion();
            usuarioDireccion.direccion.setIdDireccion(0);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("usuarioDireccion", direccionDAOImplementation.GetByIdDireccion(0).correct ? direccionDAOImplementation.GetByIdDireccion(0).object : null);
            model.addAttribute("paises", paisDAOImplementation.PaisGetAll().correct ? paisDAOImplementation.PaisGetAll().object : null);
        } else { //Editar dirección
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = new Usuario();
            usuarioDireccion.usuario.setIdUsuario(IdUsuario);
            usuarioDireccion.direccion = new Direccion();
            usuarioDireccion.direccion.setIdDireccion(IdDireccion);
            usuarioDireccion.direccion = (Direccion) direccionDAOImplementation.GetByIdDireccion(IdDireccion).object;
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("estados", estadoDAOImplementation.GetAll(0).correct ? estadoDAOImplementation.GetAll(0).objects : null);
        }
        return "FormularioIndex";
    }

    @PostMapping("/Form")
    public String Form(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult BindingResult, @RequestParam MultipartFile imagenFile, Model model) {
        try {
            if (!imagenFile.isEmpty()) {
                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                usuarioDireccion.usuario.setImagen(imgBase64);
            }
        } catch (Exception ex) {

        }

        if (usuarioDireccion.usuario.getIdUsuario() == 0) { //Agregar
            //Logica para consumir DAO para agregar un nuevo usuario
            System.out.println("Estoy agregando un nuevo usuario y direccion");
            usuarioDireccion.usuario.setFechaNacimiento(new Date());
            usuarioDAOImplementation.Add(usuarioDireccion);
        } else {
            if (usuarioDireccion.direccion.getIdDireccion() == -1) { //Editar usuario
                usuarioDAOImplementation.Update(usuarioDireccion.usuario);
                System.out.println("Estoy actualizando un usuario");
            } else if (usuarioDireccion.direccion.getIdDireccion() == 0) { //Agregar direccion
                System.out.println("Estoy agregando direccion");
            } else { //Editar direccion
                System.out.println("Estoy actualizando direccion");
            }
        }

        return "redirect:/Usuario";
    }

    @GetMapping("/EstadoGetAll/{IdPais}")
    @ResponseBody
    public Result EstadoGetAll(@PathVariable int IdPais) {
        Result result = estadoDAOImplementation.GetAll(IdPais);
        return result;
    }

    @GetMapping("/MunicipioGetAll/{IdEstado}")
    @ResponseBody
    public Result MunicipioGetAll(@PathVariable int IdEstado) {
        Result result = municipioDAOImplementation.MunicipioGetAll(IdEstado);
        return result;
    }

    @GetMapping("/ColoniaGetAll/{IdMunicipio}")
    @ResponseBody
    public Result ColoniaGetAll(@PathVariable int IdMunicipio) {
        Result result = coloniaDAOImplementation.ColoniaGetAll(IdMunicipio);
        return result;
    }

    @GetMapping("/CargaMasiva")
    public String CargaMasiva(Model model) {
        model.addAttribute("archivoCorrecto", null);
        model.addAttribute("listaErrores", new ArrayList<ResultFile>());
        return "CargaMasiva";
    }

    @PostMapping("/CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model, HttpSession session) {
        try {
            if (archivo == null || archivo.isEmpty()) {
                model.addAttribute("archivoCorrecto", false);
                model.addAttribute("listaErrores", 
                    Collections.singletonList(new ResultFile(0, "", "No se seleccionó archivo")));
                return "CargaMasiva";
            }

            String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];
            String root = System.getProperty("user.dir");
            String path = "src/main/resources/static/archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
            
            new File(root + "/" + path).mkdirs();
            archivo.transferTo(new File(absolutePath));

            List<UsuarioDireccion> listaUsuarios = tipoArchivo.equals("txt") 
                ? LecturaArchivoTXT(new File(absolutePath))
                : LecturaArchivoExcel(new File(absolutePath));

            List<ResultFile> listaErrores = ValidarArchivo(listaUsuarios);

            model.addAttribute("archivoCorrecto", true);
            model.addAttribute("listaErrores", listaErrores);
            
            if (listaErrores.isEmpty()) {
                session.setAttribute("urlFile", absolutePath);
            }

        } catch (Exception ex) {
            model.addAttribute("archivoCorrecto", false);
            model.addAttribute("listaErrores", 
                Collections.singletonList(new ResultFile(0, "", "Error al procesar archivo")));
        }
        return "CargaMasiva";
    }

    @GetMapping("/CargaMasiva/procesar")
    public String procesarArchivo(HttpSession session) {
        String filePath = (String) session.getAttribute("urlFile");
        if (filePath != null) {
            try {
                List<UsuarioDireccion> usuarios = filePath.endsWith(".txt")
                    ? LecturaArchivoTXT(new File(filePath))
                    : LecturaArchivoExcel(new File(filePath));
                
                for (UsuarioDireccion usuario : usuarios) {
                    usuarioDAOImplementation.Add(usuario);
                }
                
                new File(filePath).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.removeAttribute("urlFile");
        }
        return "redirect:/Usuario/CargaMasiva";
    }

    private List<UsuarioDireccion> leerArchivo(String filePath) throws Exception {
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        return extension.equals("txt")
                ? LecturaArchivoTXT(new File(filePath))
                : LecturaArchivoExcel(new File(filePath));
    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {

        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {

            for (Sheet sheet : workbook) {

                for (Row row : sheet) {

                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                    usuarioDireccion.usuario = new Usuario();

                    usuarioDireccion.usuario.setNombre(row.getCell(0).toString());

                    usuarioDireccion.usuario.setApellidoPaterno(row.getCell(1).toString());

                    usuarioDireccion.usuario.setApellidoMaterno(row.getCell(2).toString());

                    usuarioDireccion.usuario.setEmail(row.getCell(3).toString());

                    usuarioDireccion.usuario.Rol = new Rol();

                    usuarioDireccion.usuario.Rol.setIdRol(Integer.parseInt(row.getCell(4).toString()));

                    usuarioDireccion.usuario.Rol.equals(row.getCell(3) != null ? (int) row.getCell(3).getNumericCellValue() : 0);

                }

            }

        } catch (Exception ex) {

            System.out.println("Error al abrir el archivo");

        }

        return listaUsuarios;

    }

    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();
                usuarioDireccion.usuario.setUserName(campos[0]);
                usuarioDireccion.usuario.setNombre(campos[1]);
                usuarioDireccion.usuario.setApellidoPaterno(campos[2]);
                usuarioDireccion.usuario.setApellidoMaterno(campos[3]);
                usuarioDireccion.usuario.setTelefono(campos[4]);

                usuarioDireccion.usuario.Rol = new Rol();
                usuarioDireccion.usuario.Rol.setIdRol(Integer.parseInt(campos[5]));

                usuarioDireccion.usuario.setEmail(campos[6]);
                usuarioDireccion.usuario.setPassword(campos[7]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Dar formato a la fecha
                usuarioDireccion.usuario.setFechaNacimiento(formatter.parse(campos[8]));
                usuarioDireccion.usuario.setCurp(campos[9]);
                usuarioDireccion.usuario.setCelular(campos[10]);
                usuarioDireccion.usuario.setSexo(campos[11]);
                usuarioDireccion.usuario.setImagen(campos[12]);

                usuarioDireccion.direccion = new Direccion();
                usuarioDireccion.direccion.setCalle(campos[13]);
                usuarioDireccion.direccion.setNumeroInterior(campos[14]);
                usuarioDireccion.direccion.setNumeroExterior(campos[15]);

                usuarioDireccion.direccion.Colonia = new Colonia();
                usuarioDireccion.direccion.Colonia.setIdColonia(Integer.parseInt(campos[16]));
                usuarioDireccion.direccion.Colonia.setCodigoPostal(campos[17]);
                listaUsuarios.add(usuarioDireccion);
            }
        } catch (Exception ex) {
            listaUsuarios = null;
        }
        return listaUsuarios;
    }

    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();

        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "la lista es nula", "la lista es nula"));
        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "la lista es vacia", "la lista es vacia"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
                if (usuarioDireccion.usuario.getNombre() == null || usuarioDireccion.usuario.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.usuario.getNombre(), "El nombre es un campo oligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoPaterno() == null || usuarioDireccion.usuario.getApellidoPaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.usuario.getApellidoPaterno(), "El Apellido Paterno es un campo oligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoMaterno() == null || usuarioDireccion.usuario.getApellidoMaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.usuario.getApellidoMaterno(), "El Apellido Meterno es un campo obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }
}
