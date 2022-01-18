/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Adogcatme.Proyecto.Repositorios;

import Adogcatme.Proyecto.entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{

    @Query("SELECT m FROM Mascota m WHERE estado is 1")
    public List<Mascota> findDisponibles(); 

    @Query("SELECT m FROM Mascota m WHERE sexo = :sexo")
    public List<Mascota> findBySexo(@Param("sexo") String sexo); 

    @Query("SELECT m FROM Mascota m WHERE castrado = :castrado")
    public List<Mascota> findByCastrado(@Param("castrado") Integer castrado); 

    @Query("SELECT m FROM Mascota m WHERE dueno_id = :dueno_id")
    public List<Mascota> findByDuenoId(@Param("dueno_id") String dueno_id); 
 
    @Query("SELECT m FROM Mascota m WHERE estado = :estado")
    public List<Mascota> mascotasDisponibles(@Param("estado") Boolean estado); 

    @Query("SELECT m FROM Mascota m WHERE id = :id")
    public Mascota findByIde(@Param("id") String id); 
    
}