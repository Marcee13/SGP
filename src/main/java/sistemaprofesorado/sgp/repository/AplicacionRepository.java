package sistemaprofesorado.sgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.model.Aplicacion;

@Repository
public interface AplicacionRepository extends JpaRepository<Aplicacion, Long>{
    
}
