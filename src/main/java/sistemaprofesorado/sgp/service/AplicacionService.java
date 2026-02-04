package sistemaprofesorado.sgp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.AplicacionDTO;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;
import sistemaprofesorado.sgp.exceptions.DuplicadoException;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.mapper.AplicacionMapper;
import sistemaprofesorado.sgp.model.Aplicacion;
import sistemaprofesorado.sgp.model.OfertaLaboral;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.repository.AplicacionRepository;
import sistemaprofesorado.sgp.repository.OfertaLaboralRepository;
import sistemaprofesorado.sgp.repository.ProfesorRepository;

@Service
@AllArgsConstructor
public class AplicacionService {
    private final AplicacionRepository aplicacionRepository;
    private final ProfesorRepository profesorRepository;
    private final OfertaLaboralRepository ofertaRepository;
    private final AplicacionMapper aplicacionMapper;

    @Transactional
    public AplicacionDTO aplicarOferta(Long idProfesor, Long idOferta) {
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(()->new RecursoNoEncontradoException("El profesor indicado no ha sido encontrado"));
        OfertaLaboral oferta = ofertaRepository.findById(idOferta).orElseThrow(()->new RecursoNoEncontradoException("No se encuentra la oferta laboral indicada"));

        if(!Boolean.TRUE.equals(oferta.getAbierta())){
            throw new IllegalStateException("No se puede aplicar. Esta oferta laboral está cerrada.");
        }

        boolean yaAplico = aplicacionRepository.existsByProfesorAndOfertaLaboral(profesor, oferta);
        if(yaAplico){
            throw new DuplicadoException("Ya se ha aplicado a esta oferta laboral previamente.");
        }

        Aplicacion aplicacion = new Aplicacion();
        aplicacion.setProfesor(profesor);
        aplicacion.setOfertaLaboral(oferta);
        aplicacion.setFechaAplicacion(LocalDate.now());
        aplicacion.setEstadoAplicacion(EstadoAplicacion.RECIBIDA);

        Aplicacion aplicacionGuardada = aplicacionRepository.save(aplicacion);
        return aplicacionMapper.toDTO(aplicacionGuardada);
    }

    @Transactional
    public AplicacionDTO evaluarAplicacion(Long idAplicacion, EstadoAplicacion nuevoEstado, String comentarios) {
        Aplicacion aplicacion = aplicacionRepository.findById(idAplicacion).orElseThrow(()->new RecursoNoEncontradoException("No se encuentra la aplicación indicada"));

        aplicacion.setEstadoAplicacion(nuevoEstado);
        aplicacion.setComentariosEvaluador(comentarios);

        Aplicacion aplicacionActualizada = aplicacionRepository.save(aplicacion);
        return aplicacionMapper.toDTO(aplicacionActualizada);
    }

    public List<AplicacionDTO> listarPorOfertaLaboral(Long idOferta) {
        if (!ofertaRepository.existsById(idOferta)) {
            throw new RecursoNoEncontradoException("La oferta con ID " + idOferta + " no existe.");
        }

        List<Aplicacion> aplicaciones=aplicacionRepository.findByOfertaLaboral_IdOferta(idOferta);

        return aplicaciones.stream().map(aplicacionMapper::toDTO).toList();
    }

    @Transactional
    public List<AplicacionDTO> listarTodas(){
        List<Aplicacion> aplicaciones=aplicacionRepository.findAll();
        return aplicaciones.stream().map(aplicacionMapper::toDTO).toList();
    }
}
