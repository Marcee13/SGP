package sistemaprofesorado.sgp.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "oferta_laboral")
public class OfertaLaboral implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOferta;
    private String tituloPuesto;
    private String descripcionPuesto;
    private String requisitos;
    private String beneficios;
    private Boolean abierta;
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;
}
