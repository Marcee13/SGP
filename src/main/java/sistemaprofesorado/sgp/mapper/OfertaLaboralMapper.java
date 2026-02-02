package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.OfertaLaboralDTO;
import sistemaprofesorado.sgp.model.OfertaLaboral;

@Component
public class OfertaLaboralMapper {
    public OfertaLaboralDTO toDTO(OfertaLaboral ofertaLaboral) {
        OfertaLaboralDTO dto = new OfertaLaboralDTO();
        dto.setIdOferta(ofertaLaboral.getIdOferta());
        dto.setTituloPuesto(ofertaLaboral.getTituloPuesto());
        dto.setDescripcionPuesto(ofertaLaboral.getDescripcionPuesto());
        dto.setRequisitos(ofertaLaboral.getRequisitos());
        dto.setFechaPublicacion(ofertaLaboral.getFechaPublicacion());
        dto.setFechaCierre(ofertaLaboral.getFechaCierre());
        dto.setBeneficios(ofertaLaboral.getBeneficios());
        dto.setAbierta(ofertaLaboral.getAbierta());
        return dto;
    }

    public OfertaLaboral toEntity(OfertaLaboralDTO dto) {
        OfertaLaboral ofertaLaboral = new OfertaLaboral();
        ofertaLaboral.setIdOferta(dto.getIdOferta());
        ofertaLaboral.setTituloPuesto(dto.getTituloPuesto());
        ofertaLaboral.setDescripcionPuesto(dto.getDescripcionPuesto());
        ofertaLaboral.setRequisitos(dto.getRequisitos());
        ofertaLaboral.setFechaPublicacion(dto.getFechaPublicacion());
        ofertaLaboral.setFechaCierre(dto.getFechaCierre());
        ofertaLaboral.setBeneficios(dto.getBeneficios());
        ofertaLaboral.setAbierta(dto.getAbierta());
        return ofertaLaboral;
    }
}
