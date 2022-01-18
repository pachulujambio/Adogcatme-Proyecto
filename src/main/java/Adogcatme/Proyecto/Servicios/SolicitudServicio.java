/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.MascotaRepositorio;
import Adogcatme.Proyecto.Repositorios.SolicitudRepositorio;
import Adogcatme.Proyecto.entidades.Adoptante;
import Adogcatme.Proyecto.entidades.Dueno;
import Adogcatme.Proyecto.entidades.Mascota;
import Adogcatme.Proyecto.entidades.Solicitud;
import exepciones.WebExeption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicio {

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    @Autowired
    private DuenoServicio duenoServicio;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    public List<Solicitud> listAll() {

        return solicitudRepositorio.findAll();
    }

    @Transactional
    public Solicitud save(Adoptante adoptante, Mascota mascota) throws WebExeption {
        try {
            Solicitud solicitud = solicitudRepositorio.findByAyM(adoptante.getId(), mascota.getId());
            if (solicitud == null) {
                solicitud = new Solicitud();
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate horaActual = LocalDate.now();
                Date fechaActual = Date.from(horaActual.atStartOfDay(defaultZoneId).toInstant());
                solicitud.setFecha(fechaActual);
                Dueno dueno = duenoServicio.findByIde(mascota.getDueno().getId());
                solicitud.setAdoptante(adoptante);
                solicitud.setMascota(mascota);
                solicitud.setDueno(dueno);
                adoptante.setSolicitud(solicitud);
                return solicitudRepositorio.save(solicitud);
            }
        } catch (Exception e) {
            new WebExeption("Ya has enviado una solitud por esta macota, espere la respuesta.");
        }
        return null;
    }

    @Transactional
    public void solicitudAccion(String id_solicitud, Dueno dueno, Boolean accion) {
        Solicitud solicitud = solicitudRepositorio.getById(id_solicitud);
        if (accion) {
            if (solicitud.getDueno().getId() == dueno.getId()) {
                solicitud.getMascota().setEstado(false);
                solicitud.setEstado(true);
            }
        } else if (accion == false) {
            solicitud.setEstado(false);
        } else {
            solicitud.setEstado(null);
        }
    }
    
    
    

    @Transactional
    public void delete(Solicitud solicitud) {
        solicitudRepositorio.delete(solicitud);
    }

    public List<Solicitud> solicitudesDisp(String id) {
        return solicitudRepositorio.findSolicitudesDispDueno(id);
    }
    
    public List<Solicitud> solicitudesDispAdop(String id) {
        return solicitudRepositorio.findSolicitudesDispAdop(id);
    }
}
