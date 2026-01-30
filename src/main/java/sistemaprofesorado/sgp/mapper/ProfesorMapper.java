package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.ProfesorDTO;
import sistemaprofesorado.sgp.model.Profesor;

@Component
public class ProfesorMapper {
    public ProfesorDTO toDTO(Profesor profesor) {
        ProfesorDTO dto = new ProfesorDTO();
        dto.setIdProfesor(profesor.getIdProfesor());
        dto.setNombres(profesor.getNombres());
        dto.setApellidos(profesor.getApellidos());
        dto.setEmail(profesor.getEmail());
        dto.setContrasenia(profesor.getContrasenia());
        dto.setNumeroTelefonico(profesor.getNumeroTelefonico());
        dto.setDocumento(profesor.getDocumento());
        dto.setNumeroDocumento(profesor.getNumeroDocumento());
        dto.setTipoContratacion(profesor.getTipoContratacion());
        dto.setGenero(profesor.getGenero());
        dto.setEstado(profesor.getEstado());
        dto.setSexo(profesor.getSexo());
        dto.setPaisResidencia(profesor.getPaisResidencia());
        dto.setEspecialidad(profesor.getEspecialidad());
        dto.setActivo(profesor.getActivo());
        dto.setCodigoEmpleado(profesor.getCodigoEmpleado());
        dto.setFotoPerfil(profesor.getFotoPerfil());
        dto.setDocumentoTitulo(profesor.getDocumentoTitulo());
        dto.setDocumentoAtestados(profesor.getDocumentoAtestados());
        dto.setDocumentoDUIPasaporte(profesor.getDocumentoDUIPasaporte());
        dto.setDocumentoNIT(profesor.getDocumentoNIT());
        return dto;
    }

    public Profesor toEntity(ProfesorDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(dto.getIdProfesor());
        profesor.setNombres(dto.getNombres());
        profesor.setApellidos(dto.getApellidos());
        profesor.setNumeroTelefonico(dto.getNumeroTelefonico());
        profesor.setEmail(dto.getEmail());
        profesor.setContrasenia(dto.getContrasenia());
        profesor.setDocumento(dto.getDocumento());
        profesor.setNumeroDocumento(dto.getNumeroDocumento());
        profesor.setTipoContratacion(dto.getTipoContratacion());
        profesor.setGenero(dto.getGenero());
        profesor.setEstado(dto.getEstado());
        profesor.setSexo(dto.getSexo());
        profesor.setPaisResidencia(dto.getPaisResidencia());
        profesor.setEspecialidad(dto.getEspecialidad());
        profesor.setActivo(dto.getActivo());
        profesor.setCodigoEmpleado(dto.getCodigoEmpleado());
        profesor.setFotoPerfil(dto.getFotoPerfil());
        profesor.setDocumentoTitulo(dto.getDocumentoTitulo());
        profesor.setDocumentoAtestados(dto.getDocumentoAtestados());
        profesor.setDocumentoDUIPasaporte(dto.getDocumentoDUIPasaporte());
        profesor.setDocumentoNIT(dto.getDocumentoNIT());
        return profesor;
    }
}
