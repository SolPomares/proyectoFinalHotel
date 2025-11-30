package Excepciones;

public class ReservaInexistenteExeption extends RuntimeException {
    public ReservaInexistenteExeption(String message) {
        super(message);
    }
}
