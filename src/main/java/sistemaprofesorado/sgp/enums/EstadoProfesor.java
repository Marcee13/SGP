package sistemaprofesorado.sgp.enums;

public enum EstadoProfesor {
    ASPIRANTE(true),
    SELECCIONADO(true),
    PENDIENTE_EVALUACION(true),
    EN_FORMACION(true),
    EN_SUSPENSION(false),
    EN_BAJA(false),
    EN_RETIRO(false),
    INTERINO(true),
    NUMERARIO(true),
    DESVINCULADO(false);

    private final boolean activo;

    EstadoProfesor(boolean activo){
        this.activo=activo;
    }

    public boolean esActivo(){
        return this.activo;
    }
}
