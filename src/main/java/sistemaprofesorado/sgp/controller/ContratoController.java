package sistemaprofesorado.sgp.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<ContratoDTO>> generarContrato(@Valid @RequestBody CrearContratoDTO datos) {
        ContratoDTO nuevoContrato = contratacionService.contratarProfesor(datos);
        ApiResponse<ContratoDTO> respuesta = new ApiResponse<>(
            "Contrato generado exitosamente.",
            nuevoContrato,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping(value="/contratos/descargar-pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> descargarContrato(@PathVariable Long id)throws DocumentException {
        
        byte[] pdfBytes = contratacionService.generarReportePdf(id);

        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "Contrato.pdf"); 
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
