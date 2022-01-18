/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Controladores;

import Adogcatme.Proyecto.entidades.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("")
    public String login(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String usuario, @RequestParam(required = false) String logout) {
        try {
            if (error != null) {
                model.addAttribute("error", "El usuario o la contraseña son incorrectos");
            }
            if (usuario != null) {
                model.addAttribute("usuario", usuario);
            }
            model.addAttribute("usuario", new Usuario());
            return "home";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }
}
