/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gidis01.CRamirezProgramacionNCapasMarzo25.controler;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthController {

    @GetMapping("/login")
    public String login() {

        return "PersonalLogin"; 
    }

    @GetMapping("/login-error")
    public String loginError() {
        return "login-error"; 
    }

    @GetMapping("/login-success")
    public String loginSuccess(){
        return "login-Success"; 
    }
    
    @GetMapping("/logout-exitoso")
    public String logout(Model model) {
        model.addAttribute("usuario", "usuario");
        return "logout";
    }

}
