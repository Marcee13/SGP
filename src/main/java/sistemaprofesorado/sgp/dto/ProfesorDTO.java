package sistemaprofesorado.sgp.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Generos;
import sistemaprofesorado.sgp.enums.Sexos;
import sistemaprofesorado.sgp.enums.TipoContratacion;
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
    @JsonFormat(pattern = "dd-MM-yyyy") //Setea el formato a la fecha sino por defecto es AAAA-MM-DD
    private LocalDate fechaNacimiento;
    private TipoDocumento documento;
    private String numeroDocumento;
    private TipoContratacion tipoContratacion;
    private Generos genero;
    private EstadoProfesor estado;
    private Sexos sexo;
    private String paisResidencia;
    private String especialidad;
    private Boolean activo=true;
    private String codigoEmpleado;
    private String fotoPerfil;
    private String documentoTitulo;
    private String documentoAtestados;
    private String documentoDUIPasaporte;
    private String documentoNIT;
}
