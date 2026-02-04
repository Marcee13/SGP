package sistemaprofesorado.sgp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.LoginDTO;
import sistemaprofesorado.sgp.model.Usuario;
import sistemaprofesorado.sgp.repository.UsuarioRepository;
import sistemaprofesorado.sgp.response.AuthResponse;
import sistemaprofesorado.sgp.security.JwtService;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail()).orElseThrow();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", usuario.getRol().name()); 
        extraClaims.put("nombre", usuario.getProfesor() != null ? usuario.getProfesor().getNombres() : "Admin");
        extraClaims.put("idUsuario", usuario.getIdUsuario());

        String token = jwtService.generateToken(extraClaims,usuario);

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}
