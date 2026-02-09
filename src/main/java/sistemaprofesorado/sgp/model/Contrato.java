package sistemaprofesorado.sgp.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import sistemaprofesorado.sgp.enums.TipoContratacion;

@Data
@Entity
@Table(name = "contrato")
public class Contrato{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;
    @ManyToOne
    private Profesor profesor;
    @ManyToOne
    private Aplicacion aplicacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @Enumerated(EnumType.STRING)
    private TipoContratacion tipoContratacion;
    private BigDecimal salario;
    private String contratoFirmado;
    private Boolean firmadoDigitalmente;
}
