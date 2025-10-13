import Enums.TipoRol;
import Enums.Turno;
import java.util.Date;

public class Recepcionista extends Empleados {
    private Turno turno;

    //constructor
    public Recepcionista(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, Turno turno) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
        this.turno = turno;
    }

    public Recepcionista(String nombre, String apellido, TipoRol tipoRol, String nombreUsuario, Turno turno) {
        super(nombre, apellido, tipoRol, nombreUsuario);
        this.turno = turno;
    }

    // Getter y Setter
    public Turno getTurno() {
        return this.turno;
    }

    public void setTurno(Turno turnoActual) {
        this.turno = turnoActual;
    }

    //TODOS ESTOS VAN CON EL GESTORHOTEL
    public boolean realizarCheckIn(Pasajero pasajero, Habitacion habitacion) {
        // OJO :
        // FALTA VINCULAR el return gestorHotel.getInstance().checkIn(pasajero, habitacion);
        System.out.println(this.getNombre() + " realiza el check-in para " + pasajero.getNombre());
        return true; // ACA AL Gestor ver linea de arriba
    }

    public boolean realizarCheckOut(Pasajero pasajero, Habitacion habitacion) {
        // VINCULAR gestorHotel.getInstance().checkOut(pasajero, habitacion);
        System.out.println(this.getNombre() + " realiza el check-Out para " + pasajero.getNombre());
        return false; // ACA AL Gestor ver linea
    }

    //Me parece que va en el gestor de Hotel
    public boolean crearReserva(Habitacion habitacion, Date fecha, String apellido) {
        //Vincular
        // VINCULAR Sistema.getInstance().creserva(pasajero, habitacion)
        boolean exito = SistemaHotel.crearReserva(habitacion, fecha, apellido);
        if (exito) {
            System.out.println("Reserva realizada con exito");
        } else {
            System.out.println("No fue posible crear la reserva");
        }
        return exito; //VER CON GESTOR HOTEL EN FECHA DEBERIA SER FECHA INICIO Y FIN
    }

}

