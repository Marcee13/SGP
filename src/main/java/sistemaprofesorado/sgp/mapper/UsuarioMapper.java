package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.UsuarioDTO;
import sistemaprofesorado.sgp.model.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario usuario){
        UsuarioDTO dto =new UsuarioDTO();
        dto.setEmail(usuario.getEmail());
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setPassword(usuario.getPassword());
        dto.setRol(usuario.getRol());
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto){
        Usuario usuario=new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        return usuario;
    }
}
