import Clases.*;
import Enums.TipoRol;
import Enums.Turno;
import Excepciones.AccesoDenegadoException;
import Excepciones.LecturaJsonException;
import Excepciones.datoInvalidoException;
import ManejoJSON.Utilidades;
import Excepciones.JSONException;

import java.util.*;


public class App {
    // Credenciales del administrador genérico
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    //Credenciales recepcion genericos
    private static final String RECEP_USER = "recepcion";
    private static final String RECEP_PASS = "recepcion123";

    //Credenciales pasajero generico
    private static final String PASAJERO_USER = "pasajero";
    private static final String PASAJERO_PASS = "pasajero123";

    public static void main(String[] args) {
        try {
            // Cargar el sistema completo desde JSON
            System.out.println("Inicializando sistema hotelero...");
            //cargo desde JSON
            List<Usuario> usuariosCargados = Utilidades.cargarUsuarios();
            List<Habitacion> habitacionesCargadas = Utilidades.cargarHabitaciones();

            //convierto a ARRAY para poder trabajarlo
            // Si es nulo, creamos una lista vacía para evitar el NullPointerException
            /// Aca me ayude con chat pq no salia
            ArrayList<Usuario> usuarios = (usuariosCargados != null)
                    ? new ArrayList<>(usuariosCargados)
                    : new ArrayList<>();

            ArrayList<Habitacion> habitaciones = (habitacionesCargadas != null)
                    ? new ArrayList<>(habitacionesCargadas)
                    : new ArrayList<>();


            //inicio Sitemas gestores
            SistemaHotel<Usuario>gestorUsuarios = new SistemaHotel<>(usuarios);
            SistemaHabitaciones gestorHabitaciones = new SistemaHabitaciones(habitaciones, new ArrayList<>());

            //Carga 1 usuario de cada uno por defecto
            // SAbemos que no es buena practica
            cargarAdminGenerico();
            cargarRecepcionistaGenerico();
            cargarPasajeroGenerico();

            System.out.println("Sistema cargado exitosamente");

            // Menú principal
           GestorMenu menu = new GestorMenu(gestorUsuarios, gestorHabitaciones);

        } catch (JSONException e) {
            throw new LecturaJsonException("Error cargando datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error de sistema encontrado");
            e.printStackTrace();
            System.err.println("----------------------------------------------------------");
            throw new RuntimeException("Error Inesperado al inicializar");
        } finally {
            if (teclado != null) {
                teclado.close();
            }
        }
    }

    //Cargo ese primer admin/recepcionista/pasajero
    private static void cargarAdminGenerico() {
        Usuario adminExistente = gestorUsuarios.buscarUsuarioPorNombreUsuario(ADMIN_USER);

        if (adminExistente == null) {
            Administrador adminInicial = new Administrador(
                    UUID.randomUUID(),
                    "Sol", "Pomares",
                    12345678,
                    TipoRol.ADMINISTRADOR,
                    ADMIN_USER,
                    "admin@hotel.com",
                    ADMIN_PASS,
                    true
            );

            try {
                //Aqui simplificamoes metodo para que si o si cargu un usuario inicial y no quede null que se arrastra
                gestorUsuarios.agregar(adminInicial);//falta hacer el metodo para empleado asi q use este porque el sistemaHotel acepta t elemntos
                System.out.println(" Administrador genérico ('admin') cargado.");
            } catch (Exception e) { // <-- Cambiado de datoInvalidoException a Exception
                System.err.println("Error al cargar Admin inicial: " + e.getMessage());
            }
        }
    }

    private static void cargarRecepcionistaGenerico() {
        Usuario RecepcionistaExistente = gestorUsuarios.buscarUsuarioPorNombreUsuario(RECEP_USER);

        if (RecepcionistaExistente == null) {
            Recepcionista recepInicial = new Recepcionista(
                    UUID.randomUUID(),
                    "Merlina", "Fernandez",
                    45290824,
                    TipoRol.RECEPCIONISTA,
                    RECEP_USER,
                    "recep@hotel.com",
                    true,
                    Turno.MANANA,
                    RECEP_PASS

            );

            try {
                //Aqui simplificamoes metodo para que si o si cargu un usuario inicial y no quede null que se arrastra
                gestorUsuarios.agregar(recepInicial);//falta hacer el metodo para empleado asi q use este porque el sistemaHotel acepta t elemntos
                System.out.println("Administrador genérico ('recepcion') cargado.");
            } catch (Exception e) { // <-- Cambiado de datoInvalidoException a Exception
                System.err.println("Error al cargar Recep inicial: " + e.getMessage());
            }
        }
    }

    private static void cargarPasajeroGenerico() {
        Usuario PasajeroExistente = gestorUsuarios.buscarUsuarioPorNombreUsuario(PASAJERO_USER);

        if (PasajeroExistente == null) {
            Pasajero pasajeroInicial = new Pasajero(
                    UUID.randomUUID(),
                    "Joel", "Beteta",
                    87654321,
                    TipoRol.PASAJERO,
                    PASAJERO_USER,
                    PASAJERO_PASS
            );

            try {
                //Aqui simplificamoes metodo para que si o si cargu un usuario inicial y no quede null que se arrastra
                gestorUsuarios.agregar(pasajeroInicial);//falta hacer el metodo para empleado asi q use este porque el sistemaHotel acepta t elemntos
                System.out.println("[Administrador genérico ('pasajero/'pasajero123') cargado.");
            } catch (Exception e) {
                System.err.println("Error al cargar pasajero inicial: " + e.getMessage());
            }
        }
    }

