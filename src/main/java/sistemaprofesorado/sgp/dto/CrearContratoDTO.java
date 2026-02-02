package sistemaprofesorado.sgp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import sistemaprofesorado.sgp.enums.TipoContratacion;

@Data
public class CrearContratoDTO {
    @NotNull(message = "El ID de la aplicación es obligatorio")
    private Long idAplicacion;
    @NotNull
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @NotNull
    private TipoContratacion tipoContratacion;
    @NotNull
    @Positive
    private BigDecimal salarioMensual;
    private String urlContratoFirmado;
}
