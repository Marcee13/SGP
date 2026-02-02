package sistemaprofesorado.sgp.model;

import jakarta.persistence.*;
import lombok.Data;
import sistemaprofesorado.sgp.enums.NivelAcademico;

@Data
@Entity
@Table(name = "titulo_academico")
public class TituloAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTitulo;
    private String nombreInstitucion;
    private String tituloObtenido;
    private Integer anioGraduacion;
    @Enumerated(EnumType.STRING)
    private NivelAcademico nivelAcademico;
    private String documentoTitulo;

    @ManyToOne
    @JoinColumn(name = "id_profesor")
    private Profesor profesor;
}
