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

    public List<Habitacion> listarHabitacionesReservadas() {
        return listaHabitaciones.stream()
                .filter(h -> h.getDisponibilidad() == TipoDisponibilidad.OCUPADO || h.getDisponibilidad() == TipoDisponibilidad.RESERVADO)
                .collect(Collectors.toList());
    }

    public void mostrarHabitacionesDisponibles() {
        List<Habitacion> disponibles = listarHabitacionesDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay habitaciones disponibles.");
        } else {
            System.out.println("=== HABITACIONES DISPONIBLES ===");
            for (Habitacion hab : disponibles) {
                System.out.println("Habitación " + hab.getNumeroHabitacion() +
                        " - " + hab.getTipoHabitacion() +
                        " - $" + hab.getValorDiario() + "/noche");
            }
        }
    }



    //metodo para buscar habitacion por numero
public Habitacion obtenerHabitacionXNumero(int nroHabitacion){
    return listaHabitaciones.stream()
            .filter(h -> h.getNumeroHabitacion() == nroHabitacion)
            .findFirst() // Devuelve el primero que coincida
            .orElse(null); // Devuelve null si no se encuentra
}
    //Metodo para crear una reserva
    public void crearReserva (Pasajero pasajero, Habitacion habitacion, Date inicio, Date fin)
            throws habitacionOcupadaException, datoInvalidoException { // Añade datoInvalidoException

        if (pasajero == null || habitacion == null || inicio == null || fin == null) {
            throw new datoInvalidoException("ERROR! Los datos de pasajero, habitación o fechas no pueden ser nulos.");
        }

        if (habitacion.getDisponibilidad() == TipoDisponibilidad.OCUPADO ||
                habitacion.getTipoNODisponible() != null ||
                haySuperposicionDeFechas(habitacion, inicio, fin))
        {
            throw new habitacionOcupadaException ("ERROR! La habitacion seleccionada no esta disponible en esas fechas.");
        }

        Reserva r = new Reserva(pasajero, habitacion, inicio, fin);
        listaReservas.add(r);

        System.out.println("Reserva (ID: " + r.getIdReserva() + ") realizada con exito");
        
        habitacion.setDisponibilidad(TipoDisponibilidad.RESERVADO);
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

    public boolean haySuperposicionDeFechas(Habitacion habitacion, Date nuevoInicio, Date nuevoFin) {
        for (Reserva existente : listaReservas) {
            // Solo revisa las reservas de la misma habitación
            if (existente.getHabitacion().equals(habitacion)) {

                ///ACA Me ayude con AI - Lógica de superposición: (Inicio1 < Fin2) y (Fin1 > Inicio2)
                // Comprueba si el nuevo rango [nuevoInicio, nuevoFin] se cruza con [existente.getCheckIn(), existente.getCheckOut()]
                if (nuevoInicio.before(existente.getCheckOut()) && nuevoFin.after(existente.getCheckIn())) {
                    return true; // Hay superposición
                }
            }
        }
        return false; // No hay superposición



    }
}
