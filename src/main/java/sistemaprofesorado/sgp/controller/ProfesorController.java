package sistemaprofesorado.sgp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ProfesorDTO;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.ProfesorService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProfesorController {
    private final ProfesorService profesorService;

    @GetMapping("/profesores/mi-perfil")
    public ResponseEntity<ApiResponse<ProfesorDTO>> obtenerMiPerfil(@RequestParam String credencial) {
        ProfesorDTO perfilProfesor = profesorService.buscarPorCarnetOrEmail(credencial);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Perfil del profesor obtenido exitosamente.",
            perfilProfesor,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/profesores/crear-perfil")
    public ResponseEntity<ApiResponse<ProfesorDTO>> crearPerfil(@Valid @RequestBody ProfesorDTO profesorDTO) {
        ProfesorDTO nuevoProfesor = profesorService.crearProfesor(profesorDTO);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Perfil del profesor creado exitosamente.",
            nuevoProfesor,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
    
    @PutMapping("/profesores/actualizar/{id}")
    public ResponseEntity<ApiResponse<ProfesorDTO>> actualizarPerfil(@PathVariable Long id, @Valid @RequestBody ProfesorDTO profesorDTO) {
        ProfesorDTO profesorActualizado = profesorService.actualizarProfesor(id, profesorDTO);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Perfil del profesor actualizado exitosamente.",
            profesorActualizado,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/profesores/obtener-por-id/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<ProfesorDTO>> obtenerProfesorPorId(@PathVariable Long id) {
        ProfesorDTO profesorDTO = profesorService.obtenerProfesorPorId(id);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Profesor obtenido exitosamente.",
            profesorDTO,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping("/profesores/listar-todos")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<List<ProfesorDTO>>> obtenerTodosLosProfesores() {
        List<ProfesorDTO> profesores = profesorService.obtenerTodosProfesores();
        ApiResponse<List<ProfesorDTO>> respuesta = new ApiResponse<>(
            "Lista de todos los profesores obtenida exitosamente.",
            profesores,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/profesores/activos")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<List<ProfesorDTO>>> obtenerProfesoresActivos() {
        List<ProfesorDTO> profesoresActivos = profesorService.obtenerProfesoresActivos();
        ApiResponse<List<ProfesorDTO>> respuesta = new ApiResponse<>(
            "Lista de profesores activos obtenida exitosamente.",
            profesoresActivos,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @GetMapping("/profesores/buscar")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<ProfesorDTO>> buscarPorCredencial(@RequestParam String credencial) {
        ProfesorDTO profesoresEncontrados = profesorService.buscarPorCarnetOrEmail(credencial);
        ApiResponse<ProfesorDTO> respuesta = new ApiResponse<>(
            "Profesor encontrado exitosamente.",
            profesoresEncontrados,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    
    @PatchMapping("/profesores/dar-baja/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<Void>> darBajaProfesor(@PathVariable Long id) {
        profesorService.darDeBajaProfesor(id);
        ApiResponse<Void> respuesta = new ApiResponse<>(
            "Profesor dado de baja exitosamente.",
            null,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}