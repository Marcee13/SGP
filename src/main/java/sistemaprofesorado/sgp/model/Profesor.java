package sistemaprofesorado.sgp.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import sistemaprofesorado.sgp.enums.EstadoCivil;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Generos;
import sistemaprofesorado.sgp.enums.Rol;
import sistemaprofesorado.sgp.enums.Sexos;
import sistemaprofesorado.sgp.enums.TipoDocumento;

@Data
@Entity
@Table(name="profesores")
public class Profesor implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesor;
    private String nombres;
    private String apellidos;
    @Column(nullable = false, unique = true)
    private String email;
    private String numeroTelefonico;
    private LocalDate fechaNacimiento;
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;
    @Enumerated(EnumType.STRING)
    private TipoDocumento documento;
    private String numeroDocumento;
    @Column(unique = true)
    private String nup;
    @Column(unique = true)
    private String seguroSocial;
    @Enumerated(EnumType.STRING)
    private Generos genero;
    @Enumerated(EnumType.STRING)
    private EstadoProfesor estado;
    @Enumerated(EnumType.STRING)
    private Sexos sexo;
    @Column(nullable = false)
    private String paisResidencia;
    private String direccionCompleta;
    private String municipio;
    private String departamento;
    private String especialidad;
    @Column(columnDefinition = "TEXT")
    private String resumenProfesional;
    private Boolean activo=true;
    @Column(name = "codigo_empleado", nullable = true, unique = true)
    private String codigoEmpleado;
    @Column(name = "foto_perfil")
    private String fotoPerfil;
    @Column(name = "documento_DUI_Pasaporte")
    private String documentoDUIPasaporte;
    @Column(name = "documento_NIT")
    private String documentoNIT;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public boolean estaActivo(){
        return this.estado!=null&&this.estado.esActivo();
    }

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private transient List<TituloAcademico> titulos;

    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private transient List<Aplicacion> aplicaciones;

    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private transient List<Contrato> contratos;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
    private Usuario usuario;
}
