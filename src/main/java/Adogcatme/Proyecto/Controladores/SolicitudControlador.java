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
import exepciones.WebExeption;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/solicitud")
public class SolicitudControlador {

    @Autowired
    private MascotaServicio mascotaServicio;
    @Autowired
    private AdoptanteServicio adoptanteServicio;
    @Autowired
    private SolicitudServicio solicitudServicio;

    @GetMapping("/guardar/{id}")
    public String guardarSolicitud(@PathVariable(name = "id") String id, HttpSession session) {
        try {
            Adoptante adoptanteSession = (Adoptante) session.getAttribute("usuario");
            Adoptante adoptante = adoptanteServicio.findByIde(adoptanteSession.getId());
            Mascota mascota = mascotaServicio.findById(id);
            solicitudServicio.save(adoptante,mascota);
            return "congrats";
        } catch (WebExeption e) {
        }
        return "redirect:/adoptante/home";
    }
    
    
}
