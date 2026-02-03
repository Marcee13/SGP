package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.AplicacionDTO;
import sistemaprofesorado.sgp.model.Aplicacion;

@Component
public class AplicacionMapper {
    public AplicacionDTO toDTO(Aplicacion aplicacion) {
        AplicacionDTO dto = new AplicacionDTO();
        dto.setIdAplicacion(aplicacion.getIdAplicacion());
        dto.setComentariosEvaluador(aplicacion.getComentariosEvaluador());
        dto.setEstadoAplicacion(aplicacion.getEstadoAplicacion());
        dto.setFechaAplicacion(aplicacion.getFechaAplicacion());
        dto.setPuntajeEvaluacion(aplicacion.getPuntajeEvaluacion());
        if (aplicacion.getProfesor() != null) {
            dto.setIdProfesor(aplicacion.getProfesor().getIdProfesor());
            dto.setNombreProfesor(aplicacion.getProfesor().getNombres() + " " + aplicacion.getProfesor().getApellidos());
        }
        
        if (aplicacion.getOfertaLaboral() != null) {
            dto.setIdOferta(aplicacion.getOfertaLaboral().getIdOferta());
            dto.setTituloOferta(aplicacion.getOfertaLaboral().getTituloPuesto());
        }
        return dto;
    }

    public Aplicacion toEntity(AplicacionDTO dto) {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.setIdAplicacion(dto.getIdAplicacion());
        aplicacion.setComentariosEvaluador(dto.getComentariosEvaluador());
        aplicacion.setEstadoAplicacion(dto.getEstadoAplicacion());
        aplicacion.setFechaAplicacion(dto.getFechaAplicacion());
        aplicacion.setPuntajeEvaluacion(dto.getPuntajeEvaluacion());
        return aplicacion;
    }
}
