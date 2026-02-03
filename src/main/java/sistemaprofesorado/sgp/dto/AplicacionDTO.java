package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AplicacionDTO {
    private Long idAplicacion;
    private Long idProfesor;
    private String nombreProfesor;
    private Long idOferta;
    private String tituloOferta;
    @NotBlank
    private LocalDate fechaAplicacion;
    private EstadoAplicacion estadoAplicacion;
    private Double puntajeEvaluacion;
    private String comentariosEvaluador;
}
