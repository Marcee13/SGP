package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoCivil;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Generos;
import sistemaprofesorado.sgp.enums.Rol;
import sistemaprofesorado.sgp.enums.Sexos;
import sistemaprofesorado.sgp.enums.TipoDocumento;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfesorDTO {
    private Long idProfesor;
    @NotBlank
    private String nombres;
    @NotBlank
    private String apellidos;
    @NotBlank
    private String email;
    @NotBlank
    private String contrasenia;
    private String numeroTelefonico;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate fechaNacimiento;
    @NotNull
    private EstadoCivil estadoCivil;
    @NotNull
    private TipoDocumento documento;
    @NotBlank
    private String numeroDocumento;
    private String nup;
    private String seguroSocial;
    @NotNull
    private Generos genero;
    private EstadoProfesor estado;
    @NotNull
    private Sexos sexo;
    @NotBlank
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
    private Rol rol;
}
