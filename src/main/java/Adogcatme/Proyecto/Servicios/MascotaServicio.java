package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.FiltroRepositorio;
import Adogcatme.Proyecto.Repositorios.MascotaRepositorio;
import Adogcatme.Proyecto.entidades.Dueno;
import Adogcatme.Proyecto.entidades.Imagen;
import Adogcatme.Proyecto.entidades.Mascota;
import exepciones.WebExeption;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaServicio {

    @Autowired
    MascotaRepositorio mr;

    @Autowired
    FiltroRepositorio fr;

    @Autowired
    DuenoServicio ds;

    @Autowired
    ImagenServicio is;

    public List<Mascota> findByFiltro(String raza, String tipo, Integer edad, String sexo, String tamano, Boolean castrado) {
        return fr.filtro(raza, tipo, edad, sexo, tamano, castrado);
    }

    public Mascota findById(String id) {
        Optional<Mascota> mascota = mr.findById(id);
        if (mascota.isPresent()) {
            return mascota.get();
        }
        return null;
    }

    public List<Mascota> listAll() {
        return mr.findDisponibles();
    }

    public List<Mascota> mascotasDisponibles() {
        return mr.mascotasDisponibles(Boolean.TRUE);
    }

    @Transactional
    public void registrarMascota(Mascota m, Dueno d, MultipartFile archivo) throws WebExeption, Exception {
        Imagen imagen = is.guardarImagen(archivo);
        verificarRegistro(m, imagen);
        m.setImagen(imagen);
        m.setDueno(d);
        mr.save(m);
        d.getMascotas().add(m);
        ds.save(d);
    }

    @Transactional
    public void editarMascota(Mascota m) throws WebExeption {
        if (mr.existsById(m.getId())) {
            mr.save(m);
        }
    }

    @Transactional
    public void editarMascotaEnDueño(Mascota m, Dueno dueno, MultipartFile archivo) throws WebExeption, Exception {
        if (mr.existsById(m.getId())) {
            Imagen imagen = is.guardarImagen(archivo);
            verificarRegistro(m, imagen);
            m.setImagen(imagen);
            m.setDueno(dueno);
            mr.save(m);
            ds.save(dueno);
        }
    }

    public void eliminarMascota(String id_mascota) throws WebExeption {
        Mascota m = mr.findByIde(id_mascota);
        if (m != null) {
            m.setEstado(Boolean.FALSE);
            mr.save(m);
        }
    }

    public void verificarRegistro(Mascota m, Imagen i) throws WebExeption {
        if (m.getNombre().isEmpty() || m.getNombre() == null) {
            throw new WebExeption("El nombre de la mascota no puede estar vacio.");
        }
        if (m.getDescripcion().isEmpty() || m.getDescripcion() == null) {
            throw new WebExeption("La descripcion de la mascota no puede estar vacia.");
        }
        if (m.getRaza().isEmpty() || m.getRaza() == null) {
            throw new WebExeption("La raza de la mascota no puede estar vacia.");
        }
        if (m.getSexo().isEmpty() || m.getSexo() == null) {
            throw new WebExeption("Debes elegir el sexo de la mascota.");
        }
        if (m.getTamano().isEmpty() || m.getTamano() == null) {
            throw new WebExeption("Debes indicar el peso de la mascota.");
        }
        if (m.getTipo().isEmpty() || m.getTipo() == null) {
            throw new WebExeption("Debes indicar el tipo de mascota.");
        }
        if (m.getEdad() == 0 || m.getEdad() == null) {
            throw new WebExeption("La edad de la mascota no puede estar vacia.");
        }
        if (m.getPeso() == 0 || m.getPeso() == null) {
            throw new WebExeption("El peso de la mascota no puede estar vacio.");
        }
        if (i == null) {
            throw new WebExeption("Debe subir una imagen de la mascota.");
        }
        if (m.getCastrado() == null) {
            throw new WebExeption("Debe indicar si la mascota esta castrada o no.");
        }
    }
}
