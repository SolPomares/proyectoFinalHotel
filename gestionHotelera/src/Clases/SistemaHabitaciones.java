import java.util.ArrayList;

public class SistemaHabitaciones {
    //Atributos
    private ArrayList<Habitacion> listaHabitaciones;
    private ArrayList<Reserva>  listaReservas;

    //Constructor
    public SistemaHabitaciones(ArrayList<Habitacion> listaHabitaciones, ArrayList<Reserva> listaReservas) {
        this.listaHabitaciones = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
    }
}
