package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AplicacionDTO {
    private Long idAplicacion;
    private LocalDate fechaAplicacion;
    private EstadoAplicacion estadoAplicacion;
    private Double puntajeEvaluacion;
    private String comentariosEvaluador;
}
