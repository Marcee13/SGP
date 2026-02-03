package sistemaprofesorado.sgp.dto;

import lombok.Data;
import sistemaprofesorado.sgp.enums.Rol;

@Data
public class UsuarioDTO {
    private Rol rol;
    private Long idUsuario;
    private String email;
    private String password;
}
