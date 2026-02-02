package sistemaprofesorado.sgp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.model.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long>{

    boolean existsByEmail(String email);
    boolean existsByNumeroDocumento(String numeroDocumento);
    Optional<Profesor> findByCodigoEmpleadoOrEmail(String email, String codigoEmpleado);
}
