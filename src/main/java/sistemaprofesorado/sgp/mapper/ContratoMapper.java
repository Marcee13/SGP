package sistemaprofesorado.sgp.mapper;

import org.springframework.stereotype.Component;

import sistemaprofesorado.sgp.dto.ContratoDTO;
import sistemaprofesorado.sgp.model.Contrato;

@Component
public class ContratoMapper {
    public ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setIdContrato(contrato.getIdContrato());
        dto.setFechaInicio(contrato.getFechaInicio());
        dto.setFechaFin(contrato.getFechaFin());
        dto.setContratoFirmado(contrato.getContratoFirmado());
        dto.setTipoContratacion(contrato.getTipoContratacion());
        dto.setSalario(contrato.getSalario());
        dto.setFirmadoDigitalmente(contrato.getFirmadoDigitalmente());
        return dto;
    }

    public Contrato toEntity(ContratoDTO dto) {
        Contrato contrato = new Contrato();
        contrato.setIdContrato(dto.getIdContrato());
        contrato.setFechaInicio(dto.getFechaInicio());
        contrato.setFechaFin(dto.getFechaFin());
        contrato.setContratoFirmado(dto.getContratoFirmado());
        contrato.setTipoContratacion(dto.getTipoContratacion());
        contrato.setSalario(dto.getSalario());
        contrato.setFirmadoDigitalmente(dto.getFirmadoDigitalmente());
        return contrato;
    }
}
