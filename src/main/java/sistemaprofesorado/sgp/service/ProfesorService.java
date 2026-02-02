package sistemaprofesorado.sgp.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ProfesorDTO;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.exceptions.DuplicadoException;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.mapper.ProfesorMapper;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.repository.ProfesorRepository;

@Service
@AllArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final ProfesorMapper profesorMapper;

    public ProfesorDTO crearProfesor(ProfesorDTO dto) {
        validarUnico(dto);

        String numeroLimpio = dto.getNumeroDocumento() != null ? dto.getNumeroDocumento().trim() : null;
        if(dto.getDocumento()!=null&&numeroLimpio!=null){
            dto.getDocumento().validar(numeroLimpio);
        }

        Profesor entidad =profesorMapper.toEntity(dto);

        entidad.setEstado(EstadoProfesor.ASPIRANTE);
        entidad.setNumeroDocumento(numeroLimpio);
        entidad.setCodigoEmpleado(null);

        if (dto.getContrasenia() != null && !dto.getContrasenia().isEmpty()) {
            //entidad.setPassword(passwordEncoder.encode(dto.getContrasenia()));
        } else {
            throw new IllegalArgumentException("La contraseña es obligatoria para crear un profesor.");
        }
        Profesor guardado=profesorRepository.save(entidad);
        return profesorMapper.toDTO(guardado);
    }

    public ProfesorDTO buscarPorCarnetOrEmail(String credencial) {
        Profesor profesor = profesorRepository.findByCodigoEmpleadoOrEmail(credencial, credencial).orElseThrow(() -> new RecursoNoEncontradoException("Profesor no encontrado con la credencial proporcionada."));
        return profesorMapper.toDTO(profesor);
    }

    public void validarUnico(ProfesorDTO dto){
        if(profesorRepository.existsByEmail(dto.getEmail())){
            throw new DuplicadoException("El email " + dto.getEmail() + " ya está registrado. Por favor, revise.");
        }
        if(profesorRepository.existsByNumeroDocumento(dto.getNumeroDocumento())){
            throw new DuplicadoException("El numero de documento " +dto.getNumeroDocumento()+" ya está registrado. Por favor revise.");
        }
    }
}
