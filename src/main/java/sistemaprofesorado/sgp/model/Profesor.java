package sistemaprofesorado.sgp.model;

import lombok.Data;

import java.time.LocalDate;

import jakarta.persistence.*;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Generos;
import sistemaprofesorado.sgp.enums.Sexos;
import sistemaprofesorado.sgp.enums.TipoContratacion;
import sistemaprofesorado.sgp.enums.TipoDocumento;

@Data
@Entity
@Table(name="profesores")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesor;
    private String nombres;
    private String apellidos;
    private String email;
    private String contrasenia;
    private String numeroTelefonico;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private TipoDocumento documento;
    private String numeroDocumento;
    @Enumerated(EnumType.STRING)
    private TipoContratacion tipoContratacion;
    @Enumerated(EnumType.STRING)
    private Generos genero;
    @Enumerated(EnumType.STRING)
    private EstadoProfesor estado;
    @Enumerated(EnumType.STRING)
    private Sexos sexo;
    @Column(nullable = false)
    private String paisResidencia;
    private String especialidad;
    private Boolean activo=true;
    @Column(name = "codigo_empleado", nullable = true, unique = true)
    private String codigoEmpleado;
    @Column(name = "foto_perfil")
    private String fotoPerfil;
    @Column(name = "documento_titulo")
    private String documentoTitulo;
    @Column(name = "documento_atestados")
    private String documentoAtestados;
    @Column(name = "documento_DUI_Pasaporte")
    private String documentoDUIPasaporte;
    @Column(name = "documento_NIT")
    private String documentoNIT;
    @Column(name = "esta_activo")
    private Boolean estaActivo = true;

    public boolean estaActivo(){
        return this.estado!=null&&this.estado.esActivo();
    }
}
