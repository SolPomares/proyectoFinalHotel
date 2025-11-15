package Clases;

import java.util.Date;
import java.util.UUID;
import Clases.Pasajero

public class Reserva {
    //Atributos
    private UUID idReserva;
    private Pasajero pasajero;
    private Habitacion habitacion;
    private Date checkIn;
    private Date checkOut;

    //Constructor
    public Reserva( Pasajero pasajero, Habitacion habitacion, Date checkIn, Date checkOut) {
        this.idReserva = UUID.randomUUID();
        this.pasajero = pasajero;
        this.habitacion = habitacion;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    //Constructor vacio
    public Reserva() {
    }

    //Gets y sets
    public UUID getIdReserva() {
        return idReserva;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    //Metodo para mostrar datos
    public void imprimir() {
        System.out.println("Reserva ID: " + idReserva);
        System.out.println("Pasajero: " + pasajero.getNombre() + " " + pasajero.getApellido());
        System.out.println("Habitación Nro: " + habitacion.getNumeroHabitacion());
        System.out.println("Período: " + checkIn + " a " + checkOut);
    }



}
