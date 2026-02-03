package sistemaprofesorado.sgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.model.Aplicacion;
import sistemaprofesorado.sgp.model.OfertaLaboral;
import sistemaprofesorado.sgp.model.Profesor;

@Repository
public interface AplicacionRepository extends JpaRepository<Aplicacion, Long>{

    boolean existsByProfesorAndOfertaLaboral(Profesor profesor, OfertaLaboral oferta);
    List<Aplicacion> findByOfertaLaboral_IdOferta(Long idOferta);
}
