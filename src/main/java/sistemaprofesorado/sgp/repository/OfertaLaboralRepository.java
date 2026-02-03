package sistemaprofesorado.sgp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.model.OfertaLaboral;

@Repository
public interface OfertaLaboralRepository extends JpaRepository<OfertaLaboral, Long>{
    List<OfertaLaboral> findByAbiertaTrueOrderByFechaCierreAsc();
}
