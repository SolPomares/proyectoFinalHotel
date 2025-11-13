package Clases;
import Enums.TipoDisponibilidad;
import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorReservasHotel {
    private List<Habitacion> listaHabitaciones;

    public GestorReservasHotel(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    public void darDeAltaHabitacion(Habitacion habitacion) throws datoInvalidoException {
        if (habitacion == null) {
            throw new datoInvalidoException("La habitación no puede ser nula.");
        }
        this.listaHabitaciones.add(habitacion);
        System.out.println("✅ Habitación Nro. " + habitacion.getNumeroHabitacion() + " dada de alta.");
    }

    public List<Habitacion> buscarHabitacionesDisponibles(int capacidadRequerida, Date fechaIn, Date fechaOut) {
        List<Habitacion> disponibles = new ArrayList<>();
        System.out.println("Buscando habitaciones disponibles para " + capacidadRequerida + " persona(s)");

        for (Habitacion h : listaHabitaciones) {
            if (h.getCapacidad() >= capacidadRequerida &&
                    h.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE)
            {
                disponibles.add(h);
            }
        }
        return disponibles;
    }

    public boolean crearReserva(Habitacion habitacion, Date fechaIn, Date fechaOut) {
        if (habitacion.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE) {
            habitacion.setDisponibilidad(TipoDisponibilidad.RESERVADO);
            habitacion.setDateIn(fechaIn);
            habitacion.setDateOut(fechaOut);
            System.out.println("Reserva creada para Hab. " + habitacion.getNumeroHabitacion());
            return true;
        }
        System.out.println(" Error: Habitación " + habitacion.getNumeroHabitacion() + " no está libre.");
        return false;
    }

    public boolean realizarCheckIn(Habitacion habitacion) {
        if (habitacion.getDisponibilidad() == TipoDisponibilidad.RESERVADO ||
                habitacion.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE) {

            habitacion.setDisponibilidad(TipoDisponibilidad.OCUPADO);
            System.out.println("Check-In realizado para Habitación " + habitacion.getNumeroHabitacion() + ". Estado: OCUPADO.");
            return true;
        }
        System.out.println(" Error: No se puede hacer Check-In. Estado: " + habitacion.getDisponibilidad().name());
        return false;
    }

    @Override
    public String toString() {
        return "GestorReservasHotel{" +
                "listaHabitaciones=" + listaHabitaciones +
                '}';
    }
}
