package sistemaprofesorado.sgp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.OfertaLaboralDTO;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.mapper.OfertaLaboralMapper;
import sistemaprofesorado.sgp.model.OfertaLaboral;
import sistemaprofesorado.sgp.repository.OfertaLaboralRepository;

@Service
@AllArgsConstructor
public class OfertaLaboralService {
    private final OfertaLaboralRepository ofertaRepository;
    private final OfertaLaboralMapper ofertaLaboralMapper;

    @Transactional
    public OfertaLaboralDTO crearOfertaLaboral(OfertaLaboralDTO ofertaLaboralDTO) {

        if (ofertaLaboralDTO.getFechaCierre()!=null && ofertaLaboralDTO.getFechaCierre().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de cierre no puede ser una fecha anterior a la fecha actual.");
        }

        OfertaLaboral oferta = ofertaLaboralMapper.toEntity(ofertaLaboralDTO);

        oferta.setAbierta(true);
        oferta.setFechaPublicacion(LocalDate.now());

        OfertaLaboral ofertaGuardada = ofertaRepository.save(oferta);
        return ofertaLaboralMapper.toDTO(ofertaGuardada);
    }

    @Transactional
    public OfertaLaboralDTO cerrarOfertaLaboral(Long idOferta) {
        OfertaLaboral oferta =ofertaRepository.findById(idOferta).orElseThrow(() -> new RecursoNoEncontradoException("Oferta laboral con id " + idOferta + " no encontrada."));

        oferta.setAbierta(false);
        oferta.setFechaCierre(LocalDate.now());

        ofertaRepository.save(oferta);
        return ofertaLaboralMapper.toDTO(oferta);
    }

    @Transactional
    public OfertaLaboralDTO modificarOfertaLaboralDTO(Long idOferta, OfertaLaboralDTO ofertaLaboralDTO) {
        OfertaLaboral oferta =ofertaRepository.findById(idOferta).orElseThrow(() -> new RecursoNoEncontradoException("La oferta laboral con id " + idOferta + " no fue encontrada."));
        oferta.setTituloPuesto(ofertaLaboralDTO.getTituloPuesto());
        oferta.setDescripcionPuesto(ofertaLaboralDTO.getDescripcionPuesto());
        oferta.setFechaCierre(ofertaLaboralDTO.getFechaCierre());
        oferta.setRequisitos(ofertaLaboralDTO.getRequisitos());
        oferta.setBeneficios(ofertaLaboralDTO.getBeneficios());

        OfertaLaboral ofertaModificada = ofertaRepository.save(oferta);
        return ofertaLaboralMapper.toDTO(ofertaModificada);
    }

    @Transactional
    public List<OfertaLaboralDTO> obtenerOfertasLaboralesAbiertas() {
        List<OfertaLaboral> ofertasAbiertas = ofertaRepository.findByAbiertaTrueOrderByFechaCierreAsc();
        return ofertasAbiertas.stream()
                .map(ofertaLaboralMapper::toDTO)
                .toList();
    }

    @Transactional
    public OfertaLaboralDTO obtenerOfertaLaboralPorId(Long idOferta) {
        OfertaLaboral oferta = ofertaRepository.findById(idOferta).orElseThrow(() -> new RecursoNoEncontradoException("Oferta laboral con id " + idOferta + " no encontrada."));
        return ofertaLaboralMapper.toDTO(oferta);
    }
}