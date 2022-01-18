package Adogcatme.Proyecto.Repositorios;

import Adogcatme.Proyecto.entidades.Mascota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class FiltroRepositorio {

    private static final String RAZA = "raza";
    private static final String TIPO = "tipo";
    private static final String SEXO = "sexo";
    private static final String TAMANO = "tamano";

    @PersistenceContext
    private EntityManager em;

    public List<Mascota> filtro(String raza, String tipo, Integer edad, String sexo, String tamano, Boolean castrado) {
        javax.persistence.Query consulta = em.createQuery(consulta(raza, tipo, edad, sexo, tamano, castrado));

        String estado = "estado";
        if (filtrar(raza)) {
            raza = "%" + raza + "%";
            consulta.setParameter(RAZA, raza);
        }
        if (filtrar(tipo)) {
            consulta.setParameter(TIPO, tipo);
        }
        if (filtrar(sexo)) {
            consulta.setParameter(SEXO, sexo);
        }
        if (filtrar(tamano)) {
            consulta.setParameter(TAMANO, tamano);
        }
        if (filtrar(edad)) {
            consulta.setParameter("edad", edad);
        }
        if (filtrar(castrado)) {
            consulta.setParameter("castrado", castrado);
        } 
//        consulta.setParameter("estado", estado);
        return consulta.getResultList();
    }

    private boolean filtrar(String parametro) {
        return parametro != null && !parametro.trim().isEmpty();
    }

    private boolean filtrar(Integer parametro) {
        return parametro != null && parametro >= 0;
    }
    
    private boolean filtrar(Boolean parametro) {
        return parametro != null;
    }

    private String consulta(String raza, String tipo, Integer edad, String sexo, String tamano, Boolean castrado) {
        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT m FROM Mascota m WHERE estado is 1");

        if (filtrar(raza)) {
            consulta.append(" AND raza LIKE :raza");
        }
        if (filtrar(tipo)) {
            consulta.append(" AND tipo = :tipo");
        }
        if (filtrar(sexo)) {
            consulta.append(" AND sexo = :sexo");
        }
        if (filtrar(tamano)) {
            consulta.append(" AND tamano = :tamano");
        }
        if (filtrar(edad)) {
            consulta.append(" AND edad <= :edad");
        }
        if (filtrar(castrado)) {
            consulta.append(" AND castrado = :castrado");
        }
        return consulta.toString();
    }
}
