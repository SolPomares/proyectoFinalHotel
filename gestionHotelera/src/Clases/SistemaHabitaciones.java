package Clases;

import Clases.Habitacion;
import Clases.Pasajero;
import Enums.TipoDisponibilidad;
import Excepciones.ReservaInexistenteExeption;
import Excepciones.datoInvalidoException;
import Excepciones.habitacionOcupadaException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Clases.Reserva;

public class SistemaHabitaciones {
    //Atributos
    private ArrayList<Habitacion> listaHabitaciones;
    private ArrayList <Reserva>  listaReservas;

    //Constructor
    public SistemaHabitaciones(ArrayList<Habitacion> listaHabitaciones, ArrayList<Reserva> listaReservas) {
        this.listaHabitaciones = listaHabitaciones;
        this.listaReservas = listaReservas;
    }

    //Metodo para busacr reserva por id
    public Reserva buscarReservaPorId (UUID id){
        for(Reserva r : listaReservas){
            if(r.getIdReserva().equals(id)){
                return r;
            }
        }
        return null;
    }

    //metodo para listar habitaciones disponibles
    public List<Habitacion> listarHabitacionesDisponibles() {// El stream aplica dos filtros:
            return listaHabitaciones.stream()
                    .filter(h -> h.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE)
                    .filter(h -> h.getTipoNODisponible() == null)
                    .collect(Collectors.toList());

    }

    //Metodo para crear una reserva
    public void crearReserva (Pasajero pasajero, Habitacion habitacion, Date inicio, Date fin) throws habitacionOcupadaException {
        if(habitacion.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE){
            Reserva r = new Reserva(pasajero, habitacion, inicio, fin);
            listaReservas.add(r);
            System.out.println("Reserva" + r + "realizada con exito");
            habitacion.setDisponibilidad(TipoDisponibilidad.OCUPADO);
        }
        else{
            throw new habitacionOcupadaException ("ERROR! La habitacion seleccionada no esta disponible");
        }
    }

    //METODOS PARA CHECK-IN Y CHECK-OUT

    public boolean checkin (UUID id) throws datoInvalidoException {
        if (id == null) {
            throw new datoInvalidoException("ERROR! El ID de reserva no puede ser nulo.");
        }

        Reserva r = buscarReservaPorId(id);

        // Si la reserva no existe, salimos
        if(r == null){
            throw new ReservaInexistenteExeption("ERROR! La reserva ingresada no existe.");
        }

        // La reserva existe, procedemos al check-in
        Habitacion h = r.getHabitacion();

        // 1. DISPONIBILIDAD y FECHAS como objetos Date
        h.setDisponibilidad(TipoDisponibilidad.OCUPADO);
        h.setDateIn(new java.util.Date()); // Aca estaba el problema
        // fecha de salida de la reserva
        h.setDateOut(r.getCheckOut());

        System.out.println("Check-In realizado. Habitación: " + h.getNumeroHabitacion() +
                " | Pasajero: " + r.getPasajero().getApellido());

        return true;
    }

    public boolean checkout(UUID id) throws datoInvalidoException {

        // Validación de entrada
        if (id == null) {
            throw new datoInvalidoException("ERROR! El ID de reserva no puede ser nulo.");
        }

        Reserva r = buscarReservaPorId(id);

        // Si la reserva NO existe.
        if (r == null) {
            throw new ReservaInexistenteExeption("ERROR! La reserva ingresada no existe.");

        } else {
            //Ejecutar Check-Out (sólo si r != null)
            Habitacion h = r.getHabitacion();

            h.setDisponibilidad(TipoDisponibilidad.DISPONIBLE);
            h.setDateIn(null);  // La habitación ya no tiene una fecha de check-in activa
            h.setDateOut(null); // La habitación está libre

            System.out.println("Check-Out exitoso para la reserva ID: " + id);

            listaReservas.remove(r); // Si la reserva debe ser eliminada después del check-out

            return true;
        }
    }
}
