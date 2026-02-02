package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.TituloAcademicoDTO;
import sistemaprofesorado.sgp.model.TituloAcademico;

@Component
public class TituloAcademicoMapper {
    public TituloAcademicoDTO toDTO(TituloAcademico tituloAcademico) {
        TituloAcademicoDTO dto = new TituloAcademicoDTO();
        dto.setIdTitulo(tituloAcademico.getIdTitulo());
        dto.setNombreInstitucion(tituloAcademico.getNombreInstitucion());
        dto.setTituloObtenido(tituloAcademico.getTituloObtenido());
        dto.setAnioGraduacion(tituloAcademico.getAnioGraduacion());
        dto.setDocumentoTitulo(tituloAcademico.getDocumentoTitulo());
        dto.setNivelAcademico(tituloAcademico.getNivelAcademico());
        return dto;
    }

    public TituloAcademico toEntity(TituloAcademicoDTO dto) {
        TituloAcademico tituloAcademico = new TituloAcademico();
        tituloAcademico.setIdTitulo(dto.getIdTitulo());
        tituloAcademico.setNombreInstitucion(dto.getNombreInstitucion());
        tituloAcademico.setTituloObtenido(dto.getTituloObtenido());
        tituloAcademico.setAnioGraduacion(dto.getAnioGraduacion());
        tituloAcademico.setDocumentoTitulo(dto.getDocumentoTitulo());
        tituloAcademico.setNivelAcademico(dto.getNivelAcademico());
        return tituloAcademico;
    }
}
