package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.ImagenRepositorio;
import Adogcatme.Proyecto.entidades.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {

    @Autowired
    ImagenRepositorio ir;

    public Imagen findByIde(String id) {
        return ir.findByIde(id);
    }

    @Transactional
    public Imagen guardarImagen(MultipartFile archivo) {
        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return ir.save(imagen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
