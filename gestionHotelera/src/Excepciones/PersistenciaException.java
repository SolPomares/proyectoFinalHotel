package Excepciones;

public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String message) {
        super(message);
    }
}
