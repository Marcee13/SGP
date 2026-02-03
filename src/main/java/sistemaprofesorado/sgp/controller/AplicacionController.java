package sistemaprofesorado.sgp.controller;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.AplicacionDTO;
import sistemaprofesorado.sgp.enums.EstadoAplicacion;
import sistemaprofesorado.sgp.response.ApiResponse;
import sistemaprofesorado.sgp.service.AplicacionService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AplicacionController {
    private final AplicacionService aplicacionService;

    @PostMapping("/aplicaciones/aplicar")
    public ResponseEntity<ApiResponse<AplicacionDTO>> aplicar(@RequestParam Long idProfesor, @RequestParam Long idOferta){
        AplicacionDTO aplicacion =aplicacionService.aplicarOferta(idProfesor, idOferta);

        ApiResponse<AplicacionDTO> respuesta =new ApiResponse<>("Aplicacion enviada exitosamente.", aplicacion, true);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PatchMapping("/aplicaciones/evaluar/{id}")
    public ResponseEntity<ApiResponse<AplicacionDTO>> evaluar(@PathVariable Long id, @RequestParam EstadoAplicacion estado, @RequestParam String comentarios){
        AplicacionDTO dto = aplicacionService.evaluarAplicacion(id, estado, comentarios);

        ApiResponse<AplicacionDTO> respuesta=new ApiResponse<>("Evaluacion registrada exitosamente",dto,true);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }

    @GetMapping("/aplicaciones/por-oferta/{idOferta}")
    public ResponseEntity<ApiResponse<List<AplicacionDTO>>> listarPorOferta(@PathVariable Long idOferta) {
        List<AplicacionDTO> lista=aplicacionService.listarPorOfertaLaboral(idOferta);
        ApiResponse<List<AplicacionDTO>> respuesta=new ApiResponse<>("Candidatos obtenidos exitosamente.", lista, true);
        return new ResponseEntity<>(respuesta,HttpStatus.OK);
    }
}
