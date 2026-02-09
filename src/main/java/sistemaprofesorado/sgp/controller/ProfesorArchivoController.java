package sistemaprofesorado.sgp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaTypeFactory;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.core.io.Resource;
import sistemaprofesorado.sgp.service.AlmacenamientoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.repository.ProfesorRepository;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProfesorArchivoController {
    private final AlmacenamientoService almacenamientoService;
    private final ProfesorRepository profesorRepository;

    @PostMapping(value="/profesores/archivos/foto-perfil/{idProfesor}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATIVO', 'PROFESOR')")
    public ResponseEntity<String> subirFotoPerfil(@PathVariable Long idProfesor, @RequestParam("archivo") MultipartFile archivo) {
        
        Profesor profesor = profesorRepository.findById(idProfesor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Profesor indicado no encontrado"));

        String nombreArchivo = almacenamientoService.guardarArchivo(archivo);
        
        profesor.setFotoPerfil(nombreArchivo);
        profesorRepository.save(profesor);

        return ResponseEntity.ok("Foto subida exitosamente: " + nombreArchivo);
    }

    @PostMapping(value="/profesores/archivos/documento/{idProfesor}/{tipo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATIVO', 'PROFESOR')")
    public ResponseEntity<String> subirDocumento(@PathVariable Long idProfesor, @PathVariable String tipo, @RequestParam("archivo") MultipartFile archivo) {
        
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(() -> new RecursoNoEncontradoException("El profesor no se encontró"));

        String nombreArchivo = almacenamientoService.guardarArchivo(archivo);

        if ("DUI".equalsIgnoreCase(tipo)) {
            profesor.setDocumentoDUIPasaporte(nombreArchivo);
        } else if ("NIT".equalsIgnoreCase(tipo)) {
            profesor.setDocumentoNIT(nombreArchivo);
        } else {
            return ResponseEntity.badRequest().body("Tipo de documento no válido. Use DUI o NIT.");
        }
        
        profesorRepository.save(profesor);

        return ResponseEntity.ok("Documento " + tipo + " subido exitosamente.");
    }

    @GetMapping("/profesores/archivos/foto-perfil/{idProfesor}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", format = "binary")))
    public ResponseEntity<Resource> verFotoPerfil(@PathVariable Long idProfesor, HttpServletRequest request) {
        
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        String nombreArchivo = profesor.getFotoPerfil();
        
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            throw new RecursoNoEncontradoException("El profesor no tiene foto de perfil asignada.");
        }

        return prepararRespuestaArchivo(nombreArchivo, request);
    }

    @GetMapping("/profesores/{idProfesor}/documento/{tipo}")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", format = "binary")))
    public ResponseEntity<Resource> verDocumento(@PathVariable Long idProfesor, @PathVariable String tipo, HttpServletRequest request) {
        
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(() -> new RecursoNoEncontradoException("Profesor no encontrado"));

        String nombreArchivo = null;

        if ("DUI".equalsIgnoreCase(tipo)||"PASAPORTE".equalsIgnoreCase(tipo)) {
            nombreArchivo = profesor.getDocumentoDUIPasaporte();
        } else if ("NIT".equalsIgnoreCase(tipo)) {
            nombreArchivo = profesor.getDocumentoNIT();
        } else {
            throw new IllegalArgumentException("Tipo de documento inválido, los documentos permitidos son DUI y NIT");
        }

        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            throw new RecursoNoEncontradoException("El documento " + tipo + " no ha sido subido aún.");
        }

        return prepararRespuestaArchivo(nombreArchivo, request);
    }

    private ResponseEntity<Resource> prepararRespuestaArchivo(String nombreArchivo, HttpServletRequest request) {
        Resource resource = almacenamientoService.cargarArchivo(nombreArchivo);

        MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}