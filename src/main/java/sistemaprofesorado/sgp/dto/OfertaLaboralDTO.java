package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfertaLaboralDTO {
    private Long idOferta;
    private String tituloPuesto;
    private String descripcionPuesto;
    private String requisitos;
    private String beneficios;
    private Boolean abierta;
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;
}
