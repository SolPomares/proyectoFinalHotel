package Clases;

import Clases.Habitacion;
import Clases.Pasajero;
import Enums.TipoDisponibilidad;
import Excepciones.habitacionOcupadaException;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import Clases.Reserva;

public class SistemaHabitaciones {
    //Atributos
    private ArrayList<Habitacion> listaHabitaciones;
    private ArrayList <Reserva>  listaReservas;

    //Constructor
    public SistemaHabitaciones(ArrayList<Habitacion> listaHabitaciones, ArrayList<Reserva> listaReservas) {
        this.listaHabitaciones = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
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
    //Check-in
    public boolean checkin (UUID id){
        Reserva r = buscarReservaPorId(id);
        if(r != null){
            System.out.println("ERROR! La reserva ingresada no existe");
            return false;
        }
        else{
            Habitacion h = r.getHabitacion();
            h.setDisponibilidad(TipoDisponibilidad.OCUPADO);
            h.setDateIn(new Date());
            h.setDateOut(new Date());

            System.out.println("Check-In realizado. Habitación: " + h.getNumeroHabitacion() +
                    " | Pasajero: " + r.getPasajero().getApellido());

            return true;
        }
    }

    public boolean checkout(UUID id){
        Reserva r = buscarReservaPorId(id);
        if(r != null){
            System.out.println("ERROR! La reserva ingresada no existe");
            return false;
        }
        else{
            Habitacion h = r.getHabitacion();
            h.setDisponibilidad(TipoDisponibilidad.DISPONIBLE);
            h.setDateIn(null);
            h.setDateOut(new Date());

            return true;
        }
    }









}
