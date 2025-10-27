package Excepciones;

public class datoInvalidoException extends RuntimeException {
    public datoInvalidoException(String message) {
        super(message);
    }
}
