package sistemaprofesorado.sgp.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;

@Data
@Entity
@Table(name = "aplicacion")
public class Aplicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAplicacion;
    private LocalDate fechaAplicacion;
    @Enumerated(EnumType.STRING)
    private EstadoAplicacion estadoAplicacion;
    private Double puntajeEvaluacion;
    private String comentariosEvaluador;

    @ManyToOne
    private Profesor profesor;
    @ManyToOne
    private OfertaLaboral ofertaLaboral;
}
