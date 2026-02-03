package sistemaprofesorado.sgp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.model.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long>{

    boolean existsByEmail(String email);
    boolean existsByNumeroDocumento(String numeroDocumento);
    Optional<Profesor> findByCodigoEmpleadoOrEmail(String email, String codigoEmpleado);
    List<Profesor> findByEstado(EstadoProfesor numerario);
    List<Profesor> findByEstadoIn(List<EstadoProfesor> estados);
}
