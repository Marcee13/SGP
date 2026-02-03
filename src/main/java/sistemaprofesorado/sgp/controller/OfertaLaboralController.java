package sistemaprofesorado.sgp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.OfertaLaboralDTO;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.OfertaLaboralService;



@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class OfertaLaboralController {
    private final OfertaLaboralService ofertaLaboralService;

    @GetMapping("/ofertas/disponibles")
    public ResponseEntity<ApiResponse<List<OfertaLaboralDTO>>> obtenerOfertasDisponibles() {
        List<OfertaLaboralDTO> ofertas = ofertaLaboralService.obtenerOfertasLaboralesAbiertas();
        ApiResponse<List<OfertaLaboralDTO>> respuesta = new ApiResponse<>(
            "Ofertas laborales disponibles obtenidas exitosamente.",
            ofertas,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/ofertas/crear")
    public ResponseEntity<ApiResponse<OfertaLaboralDTO>> crearOferta(@Valid @RequestBody OfertaLaboralDTO ofertaLaboralDTO) {
        OfertaLaboralDTO nuevaOferta=ofertaLaboralService.crearOfertaLaboral(ofertaLaboralDTO);
        ApiResponse<OfertaLaboralDTO> respuesta = new ApiResponse<>(
            "Oferta laboral creada exitosamente.",
            nuevaOferta,
            true
        );  
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/ofertas/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<OfertaLaboralDTO>> modificarOfertaLaboral(@PathVariable Long id, @Valid @RequestBody OfertaLaboralDTO ofertaLaboralDTO) {
        OfertaLaboralDTO ofertaModificada = ofertaLaboralService.modificarOfertaLaboralDTO(id, ofertaLaboralDTO);
        ApiResponse<OfertaLaboralDTO> respuesta = new ApiResponse<>(
            "Oferta laboral modificada exitosamente.",
            ofertaModificada,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PatchMapping("/ofertas/cerrar/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<OfertaLaboralDTO>> cerrarOfertaLaboral(@PathVariable Long id) {
        OfertaLaboralDTO ofertaCerrada = ofertaLaboralService.cerrarOfertaLaboral(id);
        ApiResponse<OfertaLaboralDTO> respuesta = new ApiResponse<>(
            "Oferta laboral cerrada exitosamente.",
            ofertaCerrada,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/ofertas/obtener/{id}")
    @PreAuthorize("hasRole('ADMINISTRATIVO')")
    public ResponseEntity<ApiResponse<OfertaLaboralDTO>> obtenerOfertaLaboral(@PathVariable Long id) {
        OfertaLaboralDTO oferta = ofertaLaboralService.obtenerOfertaLaboralPorId(id);
        ApiResponse<OfertaLaboralDTO> respuesta = new ApiResponse<>(
            "Oferta laboral obtenida exitosamente.",
            oferta,
            true
        );
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    } 
}