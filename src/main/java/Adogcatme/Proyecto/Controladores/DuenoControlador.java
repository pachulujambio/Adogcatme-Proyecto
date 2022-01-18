package Adogcatme.Proyecto.Controladores;

import Adogcatme.Proyecto.Servicios.DuenoServicio;
import Adogcatme.Proyecto.Servicios.MascotaServicio;
import Adogcatme.Proyecto.Servicios.SolicitudServicio;
import Adogcatme.Proyecto.entidades.Dueno;
import Adogcatme.Proyecto.entidades.Mascota;
import exepciones.WebExeption;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequestMapping("/dueno")
@Controller
public class DuenoControlador {

    @Autowired
    private DuenoServicio duenoServicio;

    @Autowired
    private SolicitudServicio ss;

    @Autowired
    private MascotaServicio ms;

    //Modificar un dueño
    @GetMapping("/editar")
    public String editarPerfilDueno(Model model, HttpSession session) {
        Dueno usuario = (Dueno) session.getAttribute("usuario");
        Dueno dueno = duenoServicio.findByIde(usuario.getId());
        model.addAttribute("usuario", dueno);
        return "perfil-dueno-edicion";
    }

    @PostMapping("/save")
    public String guardarDueno(@ModelAttribute Dueno usuario) throws Exception {
        try {
            Dueno dueno = duenoServicio.findByIde(usuario.getId());
            List<Mascota> listaMascotas = dueno.getMascotas();
            usuario.setMascotas2(listaMascotas);
            duenoServicio.save(usuario);
            return "redirect:/dueno/home";
        } catch (WebExeption ex) {
        }

        return "redirect:/dueno/editar";
    }

    @GetMapping("/home")
    public String homeDueno(Model model, HttpSession session) {
        Dueno dueno = (Dueno) session.getAttribute("usuario");
        Dueno usuario = duenoServicio.findByIde(dueno.getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("mascotas", ms.mascotasDisponibles());
        return "perfil-dueno";
    }

    @GetMapping("/solicitudes")
    public String solicitudesDueno(Model model, HttpSession session) {
        Dueno dueno = (Dueno) session.getAttribute("usuario");
        Dueno usuario = duenoServicio.findByIde(dueno.getId());
        model.addAttribute("solicitudes", ss.solicitudesDisp(usuario.getId()));
        return "solicitudes-dueno";
    }

    @PostMapping("/accion/{id}")
    public String accionSolicitud(@PathVariable(name = "id") String id_solicitud, @RequestParam(required = false) Boolean accion, HttpSession session) {
        try {
            Dueno dueno = (Dueno) session.getAttribute("usuario");
            Dueno usuario = duenoServicio.findByIde(dueno.getId());
            ss.solicitudAccion(id_solicitud, usuario, accion);
            return "redirect:/dueno/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
