package sistemaprofesorado.sgp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ContratoDTO;
import sistemaprofesorado.sgp.dto.CrearContratoDTO;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.ContratacionService;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ContratoController {
    private final ContratacionService contratacionService;

    @PostMapping("/contratos/generar")
    public ResponseEntity<ApiResponse<ContratoDTO>> generarContrato(@RequestBody CrearContratoDTO datos) {
        ContratoDTO nuevoContrato = contratacionService.contratarProfesor(datos);
        ApiResponse<ContratoDTO> respuesta = new ApiResponse<>(
            "Contrato generado exitosamente.",
            nuevoContrato,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}
