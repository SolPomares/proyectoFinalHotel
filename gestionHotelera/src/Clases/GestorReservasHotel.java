package Clases;
import Enums.TipoDisponibilidad;
import Excepciones.NoDisponibleException;
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

    public List<Habitacion> buscarHabitacionesDisponibles(int capacidadRequerida, Date fechaIn, Date fechaOut) throws NoDisponibleException {
        List<Habitacion> disponibles = new ArrayList<>();
        System.out.println("Buscando habitaciones disponibles para " + capacidadRequerida + " persona(s)");

        for (Habitacion h : listaHabitaciones) {
            if (h.getCapacidad() >= capacidadRequerida &&
                    h.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE)
            {
                disponibles.add(h);
            }
        }

        if (disponibles.isEmpty()) {
            throw new NoDisponibleException("No se encontraron habitaciones disponibles para la capacidad y fechas especificadas.");
        }

        return disponibles;
    }

    public boolean crearReserva(Habitacion habitacion, Date fechaIn, Date fechaOut) {
        if (habitacion.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE) {
            habitacion.setDisponibilidad(TipoDisponibilidad.RESERVADO);
            habitacion.setDateIn(fechaIn);
            habitacion.setDateOut(fechaOut);
            System.out.println("Reserva creada para Habitacion: " + habitacion.getNumeroHabitacion());
            return true;
        } else {
            throw new NoDisponibleException("No sepudo reservar la habitacion: " + habitacion.getNumeroHabitacion() + "porque el estado actual de la habitacion es: " + habitacion.getDisponibilidad().name());
        }

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
