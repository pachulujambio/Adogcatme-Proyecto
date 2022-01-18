package Adogcatme.Proyecto.Repositorios;

import Adogcatme.Proyecto.entidades.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DuenoRepositorio extends JpaRepository<Dueno, String> {

    @Query("select d from Dueno d where email = :email")
    Dueno findByEmail(@Param("email") String email);
   
    @Query("select d from Dueno d where id = :id ")
    Dueno findByIde(@Param("id") String id);
    
    @Query("select d from Dueno d where usuario = :usuario")
    Dueno findByUsuario(@Param("usuario") String usuario);

}
