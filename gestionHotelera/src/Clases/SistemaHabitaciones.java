import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class SistemaHabitaciones {
    //Atributos
    private ArrayList<Habitacion> listaHabitaciones;
    private ArrayList<Reserva>  listaReservas;

    //Constructor
    public SistemaHabitaciones(ArrayList<Habitacion> listaHabitaciones, ArrayList<Reserva> listaReservas) {
        this.listaHabitaciones = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
    }

    //Metodo para busacr reserva por id
    public Reserva buscarReservaPorId (UUID id){
        Reserva r = new Reserva();
        for(int i = 0; i < listaReservas.size(); i++){
            if(r.getIdReserva().equals(listaReservas.get(i).getIdReserva())){
                r = listaReservas.get(i);
            }
        }

        return r;
    }

    //Metodo para crear una reserva
    public Reserva crearReserva (Pasajero pasajero, Habitacion habitacion, Date inicio, Date fin){
        if(habitacion.getDisponibilidad() == TipoDisponibilidad.DISPONIBLE){

        }
        else{

        }
    }






}
