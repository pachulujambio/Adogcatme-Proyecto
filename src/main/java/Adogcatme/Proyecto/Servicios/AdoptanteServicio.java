/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.AdoptanteRepositorio;
import Adogcatme.Proyecto.Repositorios.SolicitudRepositorio;
import Adogcatme.Proyecto.entidades.Adoptante;
import Adogcatme.Proyecto.entidades.Solicitud;
import exepciones.WebExeption;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptanteServicio {

    @Autowired
    private AdoptanteRepositorio adoptanteRepositorio;

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;
    
    @Transactional
    public void registrar(String nombre, String email, String contrasena, String telefono) throws WebExeption {

        validar(nombre, email, contrasena, telefono);
        Adoptante adoptante = new Adoptante();
        adoptante.setNombre(nombre);
        adoptante.setEmail(email);
        adoptante.setContrasena(contrasena);
        adoptante.setTelefono(telefono);

        adoptanteRepositorio.save(adoptante);

    }
    @Transactional
    public void modificar(String id, String nombre, String email, String contrasena, String telefono) throws WebExeption {

        validar(nombre, email, contrasena, telefono);

        Optional<Adoptante> respuesta = adoptanteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Adoptante adoptante = respuesta.get();
            adoptante.setNombre(nombre);
            adoptante.setEmail(email);
            adoptante.setTelefono(telefono);
            adoptante.setContrasena(contrasena);

            adoptanteRepositorio.save(adoptante);
        } else {
            throw new WebExeption("no se encontro el usuario");
        }

    }
    
    @Transactional
    public Adoptante save(Adoptante adoptante, String id_solicitud){
        Solicitud solicitud = solicitudRepositorio.getById(id_solicitud);
        System.out.println("ID de la solicitud que llega" + solicitud.getId());
        adoptante.setSolicitud(solicitud);   
    return adoptanteRepositorio.save(adoptante);
    }
     
    public Adoptante findByEmail(String email) {
        return adoptanteRepositorio.findByEmail(email);
    }

    public Adoptante findByUsuario(String usuario) {
        return adoptanteRepositorio.findByUsuario(usuario);
    }
    
    public Adoptante findByIde(String id) {
        return adoptanteRepositorio.findByIde(id);
    }

    @Transactional
    public void registrarAdoptante(Adoptante a) throws WebExeption {
        verificarRegistro(a);
        adoptanteRepositorio.save(a);
    }

    @Transactional
    public void editarAdoptante(Adoptante a) throws WebExeption {
        if (adoptanteRepositorio.existsById(a.getId())) {
            adoptanteRepositorio.save(a);
        }
    }

    public void verificarRegistro(Adoptante a) throws WebExeption {
        if (a.getNombre().isEmpty() || a.getNombre() == null) {
            throw new WebExeption("El nombre no puede estar vacio.");
        }
        if (a.getEmail().isEmpty() || a.getEmail() == null) {
            throw new WebExeption("El Email no puede estar vacio.");
        }
        if (a.getContrasena().isEmpty() || a.getContrasena() == null) {
            throw new WebExeption("Debe ingresar contrasena.");
        }
        if (a.getTelefono().isEmpty() || a.getTelefono() == null) {
            throw new WebExeption("El telefono no puede estar vacio.");
        }

    }

    public void validar(String nombre, String email, String contrasena, String telefono) throws WebExeption {
        if (nombre == null || nombre.isEmpty()) {
            throw new WebExeption("Nombre no puede ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new WebExeption("Email no puede ser nulo");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new WebExeption("contrasena no puede ser nulo");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new WebExeption("telefono no puede ser nulo");
        }
    }

}
