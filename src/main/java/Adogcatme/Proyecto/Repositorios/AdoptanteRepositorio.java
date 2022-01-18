package Adogcatme.Proyecto.Repositorios;

import Adogcatme.Proyecto.entidades.Adoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptanteRepositorio extends JpaRepository<Adoptante, String> {

    @Query("select a from Adoptante a where nombre = :nombre")
    public Adoptante findByNombre(@Param("nombre") String nombre);

//    @Query("select a from Adoptante a whrere adoptante_id = :usuario_id")
//    public Adoptante findByUsuarioId(@Param("usuario_id") String usuario_id);

    @Query("select a from Adoptante a where email = :email")
    Adoptante findByEmail(@Param("email") String email);

    @Query("select a from Adoptante a where usuario = :usuario")
    public Adoptante findByUsuario(@Param("usuario") String usuario);

    @Query("select a from Adoptante a where id = :id")
    public Adoptante findByIde(@Param("id") String id);

}
