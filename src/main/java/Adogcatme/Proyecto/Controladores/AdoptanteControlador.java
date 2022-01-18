/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Controladores;

import Adogcatme.Proyecto.Servicios.AdoptanteServicio;
import Adogcatme.Proyecto.Servicios.MascotaServicio;
import Adogcatme.Proyecto.Servicios.SolicitudServicio;
import Adogcatme.Proyecto.entidades.Adoptante;
import Adogcatme.Proyecto.entidades.Mascota;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adoptante")
public class AdoptanteControlador {

    @Autowired
    AdoptanteServicio as;

    @Autowired
    SolicitudServicio ss;

    @Autowired
    MascotaServicio ms;

    @GetMapping("/perfil")
    public String homeDueno(Model model, HttpSession session) {
        Adoptante adop = (Adoptante) session.getAttribute("usuario");
        Adoptante usuario = as.findByIde(adop.getId());
        model.addAttribute("usuario", usuario);
        return "perfil-adop";
    }

    @GetMapping("/home")
    public String homeAdoptante(Model model, @RequestParam(required = false) String raza, @RequestParam(required = false) String tipo, @RequestParam(required = false) Integer edad, @RequestParam(required = false) String sexo, @RequestParam(required = false) String tamano, @RequestParam(required = false) Boolean castrado) {
        try {
            if ((raza != null) || (tipo != null) || (edad != null) || (sexo != null) || (tamano != null) || (castrado != null)) {
                model.addAttribute("mascotas", ms.findByFiltro(raza, tipo, edad, sexo, tamano, castrado));
            } else {
                //aca falta poner que traiga solo las que estan disponibles
                model.addAttribute("mascotas", ms.listAll());
            }
            return "home-adop";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "home-adop";
    }

    @GetMapping("/editarAdopt")
    public String editarAdoptante(Model model, HttpSession session) {
        Adoptante adoptante = (Adoptante) session.getAttribute("usuario");

        Adoptante usuario = as.findByIde(adoptante.getId());

        model.addAttribute("adoptante", usuario);
        return "perfil-adoptante-edicion";
    }

    @PostMapping("/editarAdoptante")
    public String modificar(@ModelAttribute Adoptante adoptante) {
        try {
            as.editarAdoptante(adoptante);
            return "redirect:/adoptante/perfil";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/perfilmascota/{id}")
    public ModelAndView verMascota(@PathVariable(name = "id") String id) {
        ModelAndView editarVista = new ModelAndView("perfil-mascot");
        Mascota mascota = ms.findById(id);
        editarVista.addObject("mascota", mascota);
        return editarVista;
    }

    @GetMapping("/solicitudes")
    public String solicitudesAdop(Model model, HttpSession session) {
        try {
            Adoptante adoptante = (Adoptante) session.getAttribute("usuario");
            Adoptante usuario = as.findByIde(adoptante.getId());
            model.addAttribute("solicitudes", ss.solicitudesDispAdop(usuario.getId()));
            return "solicitudes-adop";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/adoptante/home";
    }

    @GetMapping("/perfildueno/{id}")
    public String ingresaralperfildeldueno(@PathVariable(name = "id") String id, Model model) {
        Mascota mascota = ms.findById(id);
        model.addAttribute("dueno", mascota.getDueno());
        model.addAttribute("mascota" , mascota);
        return "perfil-dueno-adop";
    }
}
