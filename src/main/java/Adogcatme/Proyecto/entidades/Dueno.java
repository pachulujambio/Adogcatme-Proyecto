package Adogcatme.Proyecto.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Dueno extends Usuario implements Serializable {

    @OneToMany (fetch = FetchType.EAGER)
    private List<Mascota> mascotas;

    public Dueno() {
        this.mascotas = new ArrayList<Mascota>();
    }
    
    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(Mascota mascota) {
        mascotas.add(mascota);
    }
    public void setMascotas2(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }


}
