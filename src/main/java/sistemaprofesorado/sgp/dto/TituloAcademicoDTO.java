package sistemaprofesorado.sgp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import sistemaprofesorado.sgp.enums.NivelAcademico;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TituloAcademicoDTO {
    private Long idTitulo;
    private String tituloObtenido;
    private String nombreInstitucion;
    private Integer anioGraduacion;
    private NivelAcademico nivelAcademico;
    private String documentoTitulo;
}
