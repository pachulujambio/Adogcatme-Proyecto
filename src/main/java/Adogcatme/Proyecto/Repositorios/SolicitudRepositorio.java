/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Repositorios;

import Adogcatme.Proyecto.entidades.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepositorio extends JpaRepository<Solicitud, String> {

    @Query("SELECT s FROM Solicitud s WHERE adoptante_id = :adoptante_id AND mascota_id = :mascota_id")
    public Solicitud findByAyM(@Param("adoptante_id") String adoptante_id, @Param("mascota_id") String mascota_id);

    @Query("SELECT s FROM Solicitud s WHERE dueno_id = :dueno_id AND estado is NULL OR estado is 1")
    public List<Solicitud> findSolicitudesDisp(@Param("dueno_id") String dueno_id);

    @Query("SELECT s FROM Solicitud s WHERE dueno_id = :dueno_id AND estado is NULL")
    public List<Solicitud> findSolicitudesDispDueno(@Param("dueno_id") String dueno_id);
    
    @Query("SELECT s FROM Solicitud s WHERE adoptante_id = :adoptante_id AND estado is NULL OR estado is 1")
    public List<Solicitud> findSolicitudesDispAdop(@Param("adoptante_id") String adoptante_id);

}
