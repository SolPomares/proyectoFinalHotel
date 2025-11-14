package Clases;
import Enums.TipoRol;
import Enums.Turno;
import java.util.Date;
import java.util.UUID;

public class Recepcionista extends Empleados {
    private Turno turno;
    private SistemaHabitaciones gestorHabitaciones;

    //constructor
    public Recepcionista(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, Turno turno, SistemaHabitaciones gestorHabitaciones) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
        this.turno = turno;
        this.gestorHabitaciones = gestorHabitaciones;
    }

    // Getter y Setter
    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turnoActual) {
        this.turno = turnoActual;
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

}

