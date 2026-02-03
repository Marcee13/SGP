package sistemaprofesorado.sgp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.LoginDTO;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.response.AuthResponse;
import sistemaprofesorado.sgp.service.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponse respuesta = authService.login(loginDTO);
        
        return ResponseEntity.ok(
            new ApiResponse<>("Login exitoso", respuesta, true)
        );
    }
}
