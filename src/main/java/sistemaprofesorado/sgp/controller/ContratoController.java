package sistemaprofesorado.sgp.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ContratoDTO;
import sistemaprofesorado.sgp.dto.CrearContratoDTO;
import sistemaprofesorado.sgp.model.Contrato;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.ContratacionService;
import sistemaprofesorado.sgp.service.PdfService;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ContratoController {
    private final ContratacionService contratacionService;

    @PostMapping("/contratos/generar")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<ContratoDTO>> generarContrato(@Valid @RequestBody CrearContratoDTO datos) {
        ContratoDTO nuevoContrato = contratacionService.contratarProfesor(datos);
        ApiResponse<ContratoDTO> respuesta = new ApiResponse<>(
            "Contrato generado exitosamente.",
            nuevoContrato,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping("/descargar-pdf/{id}")
    public ResponseEntity<byte[]> descargarContrato(@PathVariable Long id) throws IOException {
        
        byte[] pdfBytes = contratacionService.generarReportePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "Contrato_Generado.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
