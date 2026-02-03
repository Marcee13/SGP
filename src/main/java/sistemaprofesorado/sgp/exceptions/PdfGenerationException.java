package sistemaprofesorado.sgp.exceptions;

public class PdfGenerationException extends RuntimeException{
    public PdfGenerationException(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
