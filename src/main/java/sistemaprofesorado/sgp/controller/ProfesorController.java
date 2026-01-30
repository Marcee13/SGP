package sistemaprofesorado.sgp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ProfesorDTO;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.ProfesorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProfesorController {
    private final ProfesorService profesorService;

    @GetMapping("/profesor/mi-perfil")
    public ResponseEntity<ApiResponse<ProfesorDTO>> obtenerMiPerfil(@RequestParam String credencial) {
        ProfesorDTO perfilProfesor = profesorService.buscarPorCarnetOrEmail(credencial);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Perfil del profesor obtenido exitosamente.",
            perfilProfesor,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/profesor/crear-perfil")
    public ResponseEntity<ApiResponse<ProfesorDTO>> crearPerfil(@RequestBody ProfesorDTO profesorDTO) {
        ProfesorDTO nuevoProfesor = profesorService.crearProfesor(profesorDTO);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Perfil del profesor creado exitosamente.",
            nuevoProfesor,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    
}
