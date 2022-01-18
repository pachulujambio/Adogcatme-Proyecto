package Adogcatme.Proyecto.Servicios;

import Adogcatme.Proyecto.Repositorios.UsuarioRepositorio;
import Adogcatme.Proyecto.entidades.Usuario;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Adogcatme.Proyecto.entidades.Adoptante;
import Adogcatme.Proyecto.entidades.Dueno;
import Adogcatme.Proyecto.enums.Rol;
import exepciones.WebExeption;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private AdoptanteServicio adoptanteServicio;
    @Autowired
    private DuenoServicio duenoServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario saveAdotante(String usuario, String contrasena1, String contrasena2, String nombre, String telefono, String email, String barrio, String direccion) throws WebExeption {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        validar(usuario, contrasena1, contrasena2, nombre, telefono, email, barrio, direccion);
        Adoptante adoptante = adoptanteServicio.findByEmail(email);
        Adoptante adoptanteUsuario = adoptanteServicio.findByUsuario(usuario);

        if (adoptante == null && adoptanteUsuario == null) {
            adoptante = new Adoptante();
            adoptante.setUsuario(usuario);
            adoptante.setContrasena(encoder.encode(contrasena1));
            adoptante.setEmail(email);
            adoptante.setNombre(nombre);
            adoptante.setTelefono(telefono);
            adoptante.setBarrio(barrio);
            adoptante.setDireccion(direccion);
            adoptante.setRol(Rol.ADOPTANTE);
        }
        return usuarioRepositorio.save(adoptante);
    }

    @Transactional
    public Usuario saveDueno(String usuario, String contrasena1, String contrasena2, String nombre, String telefono, String email, String barrio, String direccion) throws WebExeption {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        validar(usuario, contrasena1, contrasena2, nombre, telefono, email, barrio, direccion);
        Dueno dueno = duenoServicio.findByEmail(email);
        Dueno duenoUsuario = duenoServicio.findByUsuario(usuario);

        if (dueno == null && duenoUsuario == null) {
            dueno = new Dueno();
            dueno.setUsuario(usuario);
            dueno.setContrasena(encoder.encode(contrasena2));
            dueno.setEmail(email);
            dueno.setNombre(nombre);
            dueno.setTelefono(telefono);
            dueno.setBarrio(barrio);
            dueno.setDireccion(direccion);
            dueno.setRol(Rol.DUENO);
        }
        return usuarioRepositorio.save(dueno);
    }

    public void validar(String usuario, String contrasena1, String contrasena2, String nombre, String telefono, String email, String barrio, String direccion) throws WebExeption {
        if (usuario == null || usuario.isEmpty()) {
            throw new WebExeption("El usuario no puede ser nulo");
        }
        if (contrasena1.isEmpty() || contrasena1 == null || contrasena2.isEmpty() || contrasena2 == null) {
            throw new WebExeption("La contraseña no puede estar vacio");
        }
        if (!contrasena1.equals(contrasena2)) {
            throw new WebExeption("Las contraseña no coinciden");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new WebExeption("El nombre no puede estar vacio");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new WebExeption("El telefono no puede estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new WebExeption("El email no puede estar vacio");
        }
        if (barrio.isEmpty() || barrio == null) {
            throw new WebExeption("El barrio no puede estar vacio");
        }
        if (direccion.isEmpty() || direccion == null) {
            throw new WebExeption("El direccion no puede estar vacio");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioRepositorio.findByUsername(username);
            User user;

            List<GrantedAuthority> authorities = new ArrayList<>();

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuario", usuario);

            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
            if (usuario.getRol().equals(Rol.ADOPTANTE) || usuario.getRol().equals(Rol.DUENO)) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(username, usuario.getContrasena(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario solicitado no existe");
        }
    }
}
