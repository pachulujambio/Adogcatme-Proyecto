package Adogcatme.Proyecto.Controladores;

import Adogcatme.Proyecto.Servicios.MascotaServicio;
import Adogcatme.Proyecto.entidades.Mascota;
import exepciones.WebExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/img")
public class ImagenControlador {
    
    @Autowired
    MascotaServicio ms;
    
    @GetMapping("/mascota")
    public ResponseEntity<byte[]> fotoMascota(@RequestParam String id){
        try {
            Mascota m = ms.findById(id);
            if(m.getImagen() == null){
                throw new WebExeption("La mascota no posee una foto");
            }
            byte[] imagen = m.getImagen().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

