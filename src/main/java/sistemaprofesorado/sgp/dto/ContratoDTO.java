package sistemaprofesorado.sgp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import sistemaprofesorado.sgp.enums.TipoContratacion;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContratoDTO {
    private Long idContrato;
    private String nombreProfesor;
    private String tituloPuesto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private TipoContratacion tipoContratacion;
    private BigDecimal salario;
    private String contratoFirmado;
    private Boolean firmadoDigitalmente;
}
