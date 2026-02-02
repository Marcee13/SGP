package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoCivil;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Generos;
import sistemaprofesorado.sgp.enums.Sexos;
import sistemaprofesorado.sgp.enums.TipoDocumento;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfesorDTO {
    private Long idProfesor;
    private String nombres;
    private String apellidos;
    private String email;
    private String contrasenia;
    private String numeroTelefonico;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;
    private EstadoCivil estadoCivil;
    private TipoDocumento documento;
    private String numeroDocumento;
    private String nup;
    private String seguroSocial;
    private Generos genero;
    private EstadoProfesor estado;
    private Sexos sexo;
    private String paisResidencia;
    private String direccionCompleta;
    private String municipio;
    private String departamento;
    private String especialidad;
    private String resumenProfesional;
    private Boolean activo=true;
    private String codigoEmpleado;
    private String fotoPerfil;
    private String documentoDUIPasaporte;
    private String documentoNIT;
}
