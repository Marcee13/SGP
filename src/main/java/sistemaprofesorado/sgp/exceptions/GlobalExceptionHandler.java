package sistemaprofesorado.sgp.exceptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import tools.jackson.databind.exc.InvalidFormatException;

import sistemaprofesorado.sgp.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(nombreCampo, mensaje);
        });

        ApiResponse<Map<String, String>> respuesta = new ApiResponse<>(
            "Error de validación en los datos enviados",
            errores,
            false
        );
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
    
   @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> manejarJsonMalFormado(HttpMessageNotReadableException ex) {
        
        String mensajeUsuario = "Error en el formato del JSON enviado. Verifique fechas (dd-MM-YYYY) y tipos de datos.";

        if (ex.getCause() instanceof InvalidFormatException ifx 
                && ifx.getTargetType() != null 
                && ifx.getTargetType().isEnum()) {
                
            String valorInvalido = ifx.getValue().toString();
            String valoresPermitidos = Arrays.toString(ifx.getTargetType().getEnumConstants());
            
            mensajeUsuario = String.format("El valor '%s' no es válido. Valores permitidos: %s", valorInvalido, valoresPermitidos);
        }

        ApiResponse<Object> respuesta = new ApiResponse<>(
            mensajeUsuario,
            null,
            false
        );

        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> manejarErrorBaseDatos(DataIntegrityViolationException ex) {
        String mensaje = "Error de integridad en la base de datos.";

        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("duplicate key")) {
                mensaje = "Uno de los datos enviados ya existe en el sistema (ej: Código de empleado o Email).";
            } else if (ex.getMessage().contains("not-null")) {
                mensaje = "Faltan datos obligatorios que la base de datos requiere.";
            }
        }

        ApiResponse<Object> respuesta = new ApiResponse<>(mensaje, null, false);
        return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> manejarErrorDeParametros(MethodArgumentTypeMismatchException ex) {
        String mensajeUsuario = "Parámetro inválido en la URL.";
        Class<?> requiredType = ex.getRequiredType();
        if (requiredType != null && requiredType.isEnum()) {
            String valoresPermitidos = Arrays.toString(requiredType.getEnumConstants());
            mensajeUsuario = String.format("El valor '%s' no es válido. Valores permitidos: %s", ex.getValue(), valoresPermitidos);
        }
        return new ResponseEntity<>(new ApiResponse<>(mensajeUsuario, null, false), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> manejarAccesoDenegado(AccessDeniedException ex) {
        return new ResponseEntity<>(new ApiResponse<>("No tiene permisos para acceder a este recurso.", null, false), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> manejarErrorAutenticacion(AuthenticationException ex) {
        return new ResponseEntity<>(new ApiResponse<>("No autorizado. Inicie sesión nuevamente.", null, false), HttpStatus.UNAUTHORIZED);
    }*/

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(NoResourceFoundException ex) {
        return new ResponseEntity<>(new ApiResponse<>("El endpoint no existe: /" + ex.getResourcePath(), null, false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> manejarErrorGeneral(Exception ex) {
        // En desarrollo es útil ver ex.getMessage(), en producción mejor ocultarlo
        return new ResponseEntity<>(new ApiResponse<>("Error interno del servidor: " + ex.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicadoException.class)
    public ResponseEntity<ApiResponse<Object>> manejarDuplicado(DuplicadoException ex) {
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null, false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Object>> manejarNoEncontrado(RecursoNoEncontradoException ex){
        return new ResponseEntity<>(new ApiResponse<>(ex.getMessage(), null, false), HttpStatus.NOT_FOUND);
    }
}