package Clases;
import Enums.TipoRol;
import Enums.Turno;
import Interfaces.IValidarContrasenia;

import java.util.Date;
import java.util.UUID;

public class Recepcionista extends Empleados implements IValidarContrasenia {
    private Turno turno;
    private String claveRecepcion;
    private SistemaHabitaciones gestorHabitaciones;

    //constructor


    public Recepcionista(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, Turno turno, String claveRecepcion, SistemaHabitaciones gestorHabitaciones) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
        this.turno = turno;
        this.claveRecepcion = claveRecepcion;
        this.gestorHabitaciones = gestorHabitaciones;
    }

    // Getter y Setter
    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turnoActual) {
        this.turno = turnoActual;
    }

    public String getClaveRecepcion() {
        return claveRecepcion;
    }

    public void setClaveRecepcion(String claveRecepcion) {
        this.claveRecepcion = claveRecepcion;
    }

    public SistemaHabitaciones getGestorHabitaciones() {
        return gestorHabitaciones;
    }

    public void setGestorHabitaciones(SistemaHabitaciones gestorHabitaciones) {
        this.gestorHabitaciones = gestorHabitaciones;
    }

    //Conexion de metodos con el gestor hotel
    public boolean realizarCheckIn(Pasajero pasajero, Habitacion habitacion, UUID idReserva) {
        System.out.println(this.getNombre() + " realiza el check-in para " + pasajero.getNombre());
        return gestorHabitaciones.checkin(idReserva);
    }

    public boolean realizarCheckOut(Pasajero pasajero, Habitacion habitacion,  UUID idReserva) {
        System.out.println(this.getNombre() + " realiza el check-Out para " + pasajero.getNombre());
        return gestorHabitaciones.checkout(idReserva);
    }

    @Override
    public boolean validarContrasenia(String contraseniaAValidar) {
        return this.claveRecepcion(contraseniaAValidar);
    }
}

