package sistemaprofesorado.sgp.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import sistemaprofesorado.sgp.dto.ProfesorDTO;
import sistemaprofesorado.sgp.enums.EstadoProfesor;
import sistemaprofesorado.sgp.enums.Rol;
import sistemaprofesorado.sgp.exceptions.DuplicadoException;
import sistemaprofesorado.sgp.exceptions.RecursoNoEncontradoException;
import sistemaprofesorado.sgp.mapper.ProfesorMapper;
import sistemaprofesorado.sgp.model.Profesor;
import sistemaprofesorado.sgp.model.Usuario;
import sistemaprofesorado.sgp.repository.ProfesorRepository;
import sistemaprofesorado.sgp.repository.UsuarioRepository;

@Service
@AllArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final ProfesorMapper profesorMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ProfesorDTO crearProfesor(ProfesorDTO dto) {
        validarUnico(dto);

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setRol(Rol.PROFESOR);
        usuario.setPassword(passwordEncoder.encode(dto.getContrasenia()));

        String numeroLimpio = dto.getNumeroDocumento() != null ? dto.getNumeroDocumento().trim() : null;
        if(dto.getDocumento()!=null&&numeroLimpio!=null){
            dto.getDocumento().validar(numeroLimpio);
        }

        Profesor profesorEntity = profesorMapper.toEntity(dto);
        profesorEntity.setEstado(EstadoProfesor.ASPIRANTE);
        profesorEntity.setRol(Rol.PROFESOR);
        profesorEntity.setNumeroDocumento(numeroLimpio);
        profesorEntity.setCodigoEmpleado(null);
        
        profesorEntity.setUsuario(usuario);
        usuario.setProfesor(profesorEntity);

        Profesor guardado = profesorRepository.save(profesorEntity);
        
        return profesorMapper.toDTO(guardado);
    }

    @Transactional
    public ProfesorDTO buscarPorCarnetOrEmail(String credencial) {
        Profesor profesor = profesorRepository.findByCodigoEmpleadoOrEmail(credencial, credencial).orElseThrow(() -> new RecursoNoEncontradoException("Profesor no encontrado con la credencial proporcionada."));
        return profesorMapper.toDTO(profesor);
    }

    @Transactional
    public ProfesorDTO actualizarProfesor(Long idProfesor, ProfesorDTO dto) {
        Profesor profesorExistente = profesorRepository.findById(idProfesor).orElseThrow(() -> new RecursoNoEncontradoException("Profesor con id " + idProfesor + " no encontrado."));

        if (!profesorExistente.getEmail().equals(dto.getEmail()) && profesorRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicadoException("El email " + dto.getEmail() + " ya está registrado. Por favor, revise.");
        }
        profesorExistente.setEmail(dto.getEmail());

        profesorExistente.setNombres(dto.getNombres());
        profesorExistente.setApellidos(dto.getApellidos());
        profesorExistente.setNumeroTelefonico(dto.getNumeroTelefonico());
        profesorExistente.setPaisResidencia(dto.getPaisResidencia());
        profesorExistente.setDireccionCompleta(dto.getDireccionCompleta());
        profesorExistente.setMunicipio(dto.getMunicipio());
        profesorExistente.setDepartamento(dto.getDepartamento());
        profesorExistente.setEstadoCivil(dto.getEstadoCivil());
        profesorExistente.setResumenProfesional(dto.getResumenProfesional());
        profesorExistente.setEspecialidad(dto.getEspecialidad());
        profesorExistente.setFotoPerfil(dto.getFotoPerfil());

        if (dto.getNumeroDocumento() != null && !dto.getNumeroDocumento().equals(profesorExistente.getNumeroDocumento())) {
             if (profesorRepository.existsByNumeroDocumento(dto.getNumeroDocumento())) {
                 throw new DuplicadoException("El nuevo número de documento ya está en uso.");
             }
             profesorExistente.setNumeroDocumento(dto.getNumeroDocumento());
        }

        Profesor actualizado = profesorRepository.save(profesorExistente);
        return profesorMapper.toDTO(actualizado);
    }

    @Transactional
    public ProfesorDTO obtenerProfesorPorId(Long idProfesor) {
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(() -> new RecursoNoEncontradoException("El profesor con id " + idProfesor + " no se encontró."));
        return profesorMapper.toDTO(profesor);
    }

    @Transactional
    public List<ProfesorDTO> obtenerTodosProfesores() {
        List<Profesor> profesores = profesorRepository.findAll();
        return profesores.stream()
                .map(profesorMapper::toDTO)
                .toList();
    }

    @Transactional
    public void darDeBajaProfesor(Long idProfesor) {
        Profesor profesor = profesorRepository.findById(idProfesor).orElseThrow(() -> new RecursoNoEncontradoException("No se ha encontrado al profesor con id " + idProfesor));
        profesor.setEstado(EstadoProfesor.DESVINCULADO);
        profesorRepository.save(profesor);
    }

    @Transactional
    public List<ProfesorDTO> obtenerProfesoresActivos() {
        List<EstadoProfesor> estadosActivos = List.of(EstadoProfesor.NUMERARIO, EstadoProfesor.INTERINO, EstadoProfesor.EN_BAJA, EstadoProfesor.EN_SUSPENSION);
        List<Profesor> profesoresActivos = profesorRepository.findByEstadoIn(estadosActivos);
        return profesoresActivos.stream()
                .map(profesorMapper::toDTO)
                .toList();
    }

    public void validarUnico(ProfesorDTO dto){
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new DuplicadoException("El email " + dto.getEmail() + " ya está registrado. Por favor, revise.");
        }
        if(profesorRepository.existsByNumeroDocumento(dto.getNumeroDocumento())){
            throw new DuplicadoException("El numero de documento " +dto.getNumeroDocumento()+" ya está registrado. Por favor revise.");
        }
    }
}
