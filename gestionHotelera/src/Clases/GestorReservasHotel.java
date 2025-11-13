package Clases;
import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class GestorReservasHotel<T> {
    private List<T> listaElementos;

    public GestorReservasHotel(List<T> listaElementos) {
        this.listaElementos = new ArrayList<>();
    }

    public void agregarElemento(T elemento) throws datoInvalidoException {
        if (elemento == null) {
            throw new datoInvalidoException("El elemento no puede ser nulo.");
        }
        this.listaElementos.add(elemento);
        System.out.println("Elemento agregado al gestor.");
    }

    public List<T> getListaElementos() {
        return listaElementos;
    }


}
