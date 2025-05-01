package com.gidis01.CRamirezProgramacionNCapasMarzo25.RestController;

import com.gidis01.CRamirezProgramacionNCapasMarzo25.ML.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {
    
    @GetMapping("saludo")
    public String Saludo(@RequestParam String nombre){
        return "Hola mundo"+ nombre;
    }
    
    @GetMapping("saludo2")
    public ResponseEntity Saludo2(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos Enrique");
        usuario.setApellidoPaterno("Ramirez");
        usuario.setApellidoMaterno("Ramos");
        return ResponseEntity.accepted().body(usuario);
    }
    
    @GetMapping("/Suma/{uno}/{dos}")
    public ResponseEntity Suma(@PathVariable int uno, @PathVariable int dos){
        int result = uno+dos;
        return  ResponseEntity.ok().body("La suma de los numeros es: "+ result);
    }
    
    @GetMapping("/Resta/{uno}/{dos}")
    public ResponseEntity Resta (@PathVariable int uno, @PathVariable int dos){
        int result = uno-dos;
        return ResponseEntity.ok().body("La resta de los numero es: "+ result);
    }
    
    @GetMapping("/Multiplicacion/{uno}/{dos}")
    public ResponseEntity Multiplicacion (@PathVariable int uno, @PathVariable int dos){
        int result = uno*dos;
        return ResponseEntity.ok().body("La multiplicacion de los numero es: "+ result);
    }
    
    @GetMapping("/Divicion/{uno}/{dos}")
    public ResponseEntity Divicion (@PathVariable int uno, @PathVariable int dos){
        int result = uno/dos;
        return ResponseEntity.ok().body("La divicion de los numero es: "+ result);
    }
}
