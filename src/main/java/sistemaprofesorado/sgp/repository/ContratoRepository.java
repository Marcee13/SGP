package sistemaprofesorado.sgp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>{
    
}
