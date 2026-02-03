package sistemaprofesorado.sgp.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ContratoDTO;
import sistemaprofesorado.sgp.dto.CrearContratoDTO;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.mapper.ContratoMapper;
import sistemaprofesorado.sgp.model.Aplicacion;
import sistemaprofesorado.sgp.model.Contrato;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.repository.AplicacionRepository;
import sistemaprofesorado.sgp.repository.ContratoRepository;
import sistemaprofesorado.sgp.repository.ProfesorRepository;

@Service
@AllArgsConstructor
public class ContratacionService {
    private final AplicacionRepository aplicacionRepository;
    private final ProfesorRepository profesorRepository;
    private final ContratoRepository contratoRepository;
    private final ContratoMapper contratoMapper;

    @Transactional
    public ContratoDTO contratarProfesor(CrearContratoDTO datos) {

        Aplicacion aplicacion = aplicacionRepository.findById(datos.getIdAplicacion()).orElseThrow(() -> new RecursoNoEncontradoException("Aplicación no encontrada"));

        if (aplicacion.getEstadoAplicacion() != EstadoAplicacion.ACEPTADA) {
            throw new IllegalStateException("No se puede contratar. La aplicación debe estar ACEPTADA primero. Estado actual: " + aplicacion.getEstadoAplicacion());
        }

        Profesor profesor = aplicacion.getProfesor();

        profesor.setEstado(EstadoProfesor.NUMERARIO);

        if (profesor.getCodigoEmpleado() == null) {
            profesor.setCodigoEmpleado(generarCodigoEmpleado(profesor));
        }
        
        profesorRepository.save(profesor);

        Contrato nuevoContrato = new Contrato();
        nuevoContrato.setProfesor(profesor);
        nuevoContrato.setAplicacion(aplicacion);
        nuevoContrato.setFechaInicio(datos.getFechaInicio());
        nuevoContrato.setFechaFin(datos.getFechaFin());
        nuevoContrato.setTipoContratacion(datos.getTipoContratacion());
        nuevoContrato.setSalario(datos.getSalarioMensual());
        nuevoContrato.setContratoFirmado(datos.getUrlContratoFirmado());
        nuevoContrato.setFirmadoDigitalmente(false);

        Contrato contratoGuardado = contratoRepository.save(nuevoContrato);

        aplicacion.setEstadoAplicacion(EstadoAplicacion.CONTRATADO);
        aplicacionRepository.save(aplicacion);

        return contratoMapper.toDTO(contratoGuardado);
    }

    private String generarCodigoEmpleado(Profesor profesor) {
        int anio = LocalDate.now().getYear();
        char inicialNombre=profesor.getNombres().charAt(0);
        char inicialApellido=profesor.getApellidos().charAt(0);
        return String.format("EMP-%c%c-%d-%04d", inicialNombre, inicialApellido, anio, profesor.getIdProfesor());
    }
}
