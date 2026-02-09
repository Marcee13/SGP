package sistemaprofesorado.sgp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.enums.NivelAcademico;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.model.TituloAcademico;
import sistemaprofesorado.sgp.repository.ProfesorRepository;
import sistemaprofesorado.sgp.repository.TitluloAcademicoRepository;
import sistemaprofesorado.sgp.service.AlmacenamientoService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TituloAcademicoController {
    private final TitluloAcademicoRepository tituloAcademicoRepository;
    private final ProfesorRepository profesorRepository;
    private final AlmacenamientoService almacenamientoService;

    @PostMapping(value = "/titulos/agregar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registrarTitulo(@RequestParam Long idProfesor, @RequestParam String institucion, @RequestParam String tituloObtenido, @RequestParam Integer anio, @RequestParam NivelAcademico nivelAcademico, @RequestParam MultipartFile archivo){
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(()->new RecursoNoEncontradoException("El profesor con ID "+idProfesor+" no se encontró."));
        String nombreArchivo= almacenamientoService.guardarArchivo(archivo);

        TituloAcademico nuevoTitulo = new TituloAcademico();
        nuevoTitulo.setNombreInstitucion(institucion);
        nuevoTitulo.setTituloObtenido(tituloObtenido);
        nuevoTitulo.setAnioGraduacion(anio);
        nuevoTitulo.setNivelAcademico(nivelAcademico);
        nuevoTitulo.setDocumentoTitulo(nombreArchivo);
        nuevoTitulo.setProfesor(profesor);

        tituloAcademicoRepository.save(nuevoTitulo);

        return ResponseEntity.ok("Titulo académico registrado exitosamente");
    }

    @GetMapping("/titulos/{idTitulo}/archivo")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", format = "binary")))
    public ResponseEntity<Resource> verArchivoTitulo(@PathVariable Long idTitulo, HttpServletRequest request) {

        TituloAcademico titulo = tituloAcademicoRepository.findById(idTitulo).orElseThrow(() -> new RecursoNoEncontradoException("Título académico no encontrado"));

        String nombreArchivo = titulo.getDocumentoTitulo();

        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este título no tiene un archivo adjunto.");
        }

        Resource resource = almacenamientoService.cargarArchivo(nombreArchivo);
        
        MediaType mediaType = MediaTypeFactory.getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
