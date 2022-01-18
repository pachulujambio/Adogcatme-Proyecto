package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.DuenoRepositorio;
import Adogcatme.Proyecto.Repositorios.SolicitudRepositorio;
import Adogcatme.Proyecto.entidades.Dueno;
import Adogcatme.Proyecto.entidades.Solicitud;
import exepciones.WebExeption;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DuenoServicio {

    @Autowired
    private DuenoRepositorio duenoRepositorio;

    @Autowired
    private SolicitudRepositorio solicitudRepositorio;

    //Listar dueños
    public List<Dueno> listAll() {
        return duenoRepositorio.findAll();
    }

    //busqueda por ID
    public Dueno findByIde(String id) {
        return duenoRepositorio.findByIde(id);
    }

    public Dueno findByEmail(String email) {
        return duenoRepositorio.findByEmail(email);
    }

    public Dueno findByUsuario(String usuario) {
        return duenoRepositorio.findByUsuario(usuario);
    }

    //Modificar dueño
    @Transactional
    public Dueno modificar(Dueno dueno) throws WebExeption {
        if (duenoRepositorio.existsById(dueno.getId())) {
            return duenoRepositorio.save(dueno);
        }
        return null;
    }

    //Crear dueño 
    @Transactional
    public Dueno save(Dueno dueno) throws Exception {
        if (dueno.getNombre().isEmpty() || dueno.getNombre() == null) {
            throw new Exception(" La persona debe tener un nombre");
        }
        if (dueno.getEmail().isEmpty() || dueno.getEmail() == null) {
            throw new Exception(" La persona debe tener un mail de contacto");
        }
        if (dueno.getContrasena().isEmpty() || dueno.getContrasena() == null) {
            throw new Exception(" La contraseña es obligatoria");
        }
        if (dueno.getTelefono().isEmpty() || dueno.getTelefono() == null) {
            throw new Exception(" La persona debe tener un teléfono de contacto");
        }
        return duenoRepositorio.save(dueno);
    }

    @Transactional
    public Dueno saveEdicion(Dueno dueno) throws Exception {
        if (dueno.getNombre().isEmpty() || dueno.getNombre() == null) {
            throw new Exception(" La persona debe tener un nombre");
        }
        if (dueno.getEmail().isEmpty() || dueno.getEmail() == null) {
            throw new Exception(" La persona debe tener un mail de contacto");
        }
        if (dueno.getContrasena().isEmpty() || dueno.getContrasena() == null) {
            throw new Exception(" La contraseña es obligatoria");
        }
        if (dueno.getTelefono().isEmpty() || dueno.getTelefono() == null) {
            throw new Exception(" La persona debe tener un teléfono de contacto");
        }
        return duenoRepositorio.save(dueno);
    }

    //Eliminar dueño (Creada en el caso de que haya un usuario de administrador)
    @Transactional
    public void delete(Dueno dueno) {
        duenoRepositorio.delete(dueno);
    }

    @Transactional
    public Dueno saveSolicitud(Dueno dueno, String id_solicitud) {
        Solicitud solicitud = solicitudRepositorio.getById(id_solicitud);
        System.out.println("ID de la solicitud que llega" + solicitud.getId());
        dueno.setSolicitud(solicitud);
        return duenoRepositorio.save(dueno);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Dueno> optional = duenoRepositorio.findById(id);
        if (optional.isPresent()) {
            duenoRepositorio.delete(optional.get());
        }
    }

}
