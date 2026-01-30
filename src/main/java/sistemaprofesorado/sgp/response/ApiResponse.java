package sistemaprofesorado.sgp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String mensaje;
    private T datos;
    private boolean exito;
}
