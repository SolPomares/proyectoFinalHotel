import Clases.*;
import Enums.TipoRol;
import Enums.Turno;
import Excepciones.AccesoDenegadoException;
import Excepciones.LecturaJsonException;
import Excepciones.datoInvalidoException;
import Excepciones.habitacionOcupadaException;
import ManejoJSON.Utilidades;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class App {
    private static SistemaHotel<Usuario> gestorUsuarios;
    private static SistemaHabitaciones gestorHabitaciones;
    private static Scanner teclado = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    // Cambiado a tipo Usuario para permitir login de Pasajeros
    private static Usuario usuarioActual = null;

    // Credenciales del administrador genérico
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public static void main(String[] args) {
        try {
            // Cargar el sistema completo desde JSON
            System.out.println("Inicializando sistema hotelero...");
            ArrayList<Usuario> usuarios = new ArrayList<>(Utilidades.cargarUsuarios());
            ArrayList<Habitacion> habitaciones = new ArrayList<>(Utilidades.cargarHabitaciones());

            gestorUsuarios = new SistemaHotel<>(usuarios);
            gestorHabitaciones = new SistemaHabitaciones(habitaciones, new ArrayList<>());

            cargarAdminGenerico();

            System.out.println("Sistema cargado exitosamente");

            // Menú principal
            menuPrincipalLoop();

        } catch (JSONException e) {
            throw new LecturaJsonException("Error cargando datos: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error Inesperado al inicializar el sistema");
        } finally {
            if (teclado != null) {
                teclado.close();
            }
        }
    }

    //Cargo ese primer admin
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
                    ADMIN_PASS, // ClaveAdministracion (contraseña)
                    true
            );

            try {
                gestorUsuarios.agregarPasajero(adminInicial);//falta hacer el metodo para empleado asi q use este porque el sistemaHotel acepta t elemntos
                System.out.println("[INFO] Administrador genérico ('admin'/'admin123') cargado.");
            } catch (datoInvalidoException e) {
                System.err.println("[ERROR] Error al cargar Admin inicial: " + e.getMessage());
            }
        }
    }

    private static String leerString() {
        String opcion = teclado.next();
        teclado.nextLine();
        return opcion;
    }

    private static int leerOpcion() {
        int opcionNum = teclado.nextInt();

        if (opcionNum <= 0){ throw new datoInvalidoException("El numero debe ser positivo.");}

        return opcionNum;
    }

    public static void menuPrincipalLoop() {
        int opcionMenu = -1;

        while (opcionMenu != 0) {
            // Si nadie está logueado, forzamos el login
            if (usuarioActual == null) {
                usuarioActual = login();
                if (usuarioActual == null) {
                    System.out.println("\n¿Intentar de nuevo o Salir? (0 para salir, cualquier tecla para reintentar): ");
                    String input = teclado.nextLine();
                    if (input.equals("0")) {
                        break;
                    }
                    continue;
                }
            } else {
                // Mostrar menú según el rol
                System.out.println("\n=============================================");
                System.out.println("  Bienvenido, " + usuarioActual.getNombre() + " (" + usuarioActual.getTipoRol() + ")");
                System.out.println("=============================================");

                switch (usuarioActual.getTipoRol()) {
                    case ADMINISTRADOR:
                        mostrarMenuAdministrador();
                        break;
                    case RECEPCIONISTA:
                        mostrarMenuRecepcionista();
                        break;
                    case PASAJERO:
                        mostrarMenuPasajero();
                        break;
                    default:
                        System.out.println("Rol no reconocido o sin acceso.");
                        usuarioActual = null;
                        opcionMenu = -1; //vuelvo al ingreso
                }
            }
        }
    }

    //LOGIN
    public static Usuario login() throws AccesoDenegadoException {
        System.out.println("\n=== INGRESO AL SISTEMA ===");
        System.out.println("Usuario: ");
        String user = leerString();
        System.out.println("Contraseña: ");
        String pass = leerString();

        Usuario usuarioEncontrado = gestorUsuarios.gestionarAcceso(user, pass);

        if (usuarioEncontrado != null) {
            return usuarioEncontrado;
        }
        throw new AccesoDenegadoException("Acceso denegado. Verifique usuario, contraseña o permisos.");
    }

    //Menus

    private static int mostrarMenuAdministrador() {
        System.out.println("--- MENÚ ADMINISTRADOR ---");
        System.out.println("1. Alta de Nuevo Empleado");
        System.out.println("2. Baja de Empleado (por DNI)");
        System.out.println("3. Listar Todos los Empleados");
        System.out.println("4. Listar Todos los Usuarios");
        System.out.println("5. Buscar Usuario por DNI");
        System.out.println("9. Cerrar Sesión");
        System.out.println("0. Salir del Programa");
        return leerOpcion();
    }

    private static int mostrarMenuRecepcionista() {
        System.out.println("--- MENÚ RECEPCIONISTA ---");
        System.out.println("1. Registrar Check-In de Pasajero");
        System.out.println("2. Registrar Check-Out de Pasajero");
        System.out.println("3. Dar de Alta Pasajero");
        System.out.println("4. Dar de Baja Pasajero");
        System.out.println("5. Mostrar Pasajeros Registrados");
        System.out.println("6. Crear Nueva Reserva");
        System.out.println("9. Cerrar Sesión");
        System.out.println("0. Salir del Programa");
        return leerOpcion();
    }

    private static int mostrarMenuPasajero() {
        System.out.println("\n--- ZONA DE CLIENTES ---");
        System.out.println("1. Listar Habitaciones Disponibles");
        System.out.println("2. Realizar Nueva Reserva");
        System.out.println("9. Cerrar Sesión");
        System.out.println("0. Salir del Programa");
        return leerOpcion();
    }


    // MANEJO DE OPCIONES
    private static void manejarOpcionAdministrador(int opcionM) {
        try {
            switch (opcionM) {
                case 1:
                    System.out.println("\n--- ALTA EMPLEADO ---");
                    // Lógica para crear empleado (simulada por ahora)
                    System.out.println("🛠️ Funcionalidad en desarrollo - necesitarías implementar crearEmpleado()");
                    break;
                case 2:
                    System.out.print("\n--- BAJA EMPLEADO ---\nDNI del empleado a dar de baja: ");
                    int dniBaja = Integer.parseInt(teclado.nextLine());
                    gestorUsuarios.bajaEmpleado(usuarioActual, dniBaja);
                    break;
                case 3:
                    System.out.println("\n--- LISTA DE EMPLEADOS ---");
                    gestorUsuarios.ListarEmpleados().forEach(e -> e.imprimirDatos());
                    break;
                case 4:
                    System.out.println("\n--- TODOS LOS USUARIOS ---");
                    gestorUsuarios.imprimirTodosUsuarios();
                    break;
                case 5:
                    System.out.print("\n--- BUSCAR USUARIO ---\nIngrese DNI: ");
                    int dniBuscar = Integer.parseInt(teclado.nextLine());
                    Usuario usuario = gestorUsuarios.buscarUsuario(dniBuscar);
                    if (usuario != null) {
                        usuario.imprimirDatos();
                    } else {
                        System.out.println("Usuario no encontrado");
                    }
                    break;
                case 9:
                    System.out.println("Cerrando sesión de Administrador...");
                    usuarioActual = null;
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida para Administrador.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manejarOpcionRecepcionista(int opcion) {
        Recepcionista recep = (Recepcionista) usuarioActual;
        try {
            switch (opcion) {
                case 1:
                    System.out.println("\n--- CHECK-IN ---");
                    System.out.print("Ingrese ID de la Reserva para Check-In (UUID): ");
                    String idReservaIn = teclado.nextLine();
                    boolean checkInExitoso = recep.realizarCheckIn(null, null, UUID.fromString(idReservaIn));
                    if (checkInExitoso) {
                        System.out.println("✅ Check-in realizado exitosamente");
                    }
                    break;
                case 2:
                    System.out.println("\n--- CHECK-OUT ---");
                    System.out.print("Ingrese ID de la Reserva para Check-Out (UUID): ");
                    String idReservaOut = teclado.nextLine();
                    boolean checkOutExitoso = recep.realizarCheckOut(null, null, UUID.fromString(idReservaOut));
                    if (checkOutExitoso) {
                        System.out.println("Check-out realizado exitosamente");
                    }
                    break;
                case 3:
                    System.out.println("\n--- ALTA PASAJERO ---");
                    // Lógica para crear pasajero (simulada)
                    System.out.println("🛠️ Funcionalidad en desarrollo - necesitarías implementar crearPasajero()");
                    break;
                case 4:
                    System.out.print("\n--- BAJA PASAJERO ---\nDNI del pasajero a dar de baja: ");
                    int dniPasajero = Integer.parseInt(teclado.nextLine());
                    gestorUsuarios.bajaPasajero(usuarioActual, dniPasajero);
                    break;
                case 5:
                    System.out.println("\n--- PASAJEROS REGISTRADOS ---");
                    gestorUsuarios.mostrarPasajeros();
                    break;
                case 6:
                    System.out.println("\n--- CREAR RESERVA ---");
                    // Lógica para crear reserva (simulada)
                    System.out.println("🛠️ Funcionalidad en desarrollo - necesitarías implementar crearReserva()");
                    break;
                case 9:
                    System.out.println("🔒 Cerrando sesión de Recepcionista...");
                    usuarioActual = null;
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida para Recepcionista.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manejarOpcionPasajero(int opcion) {
        Pasajero pasajero = (Pasajero) usuarioActual;
        try {
            switch (opcion) {
                case 1: // Listar Habitaciones Disponibles
                    System.out.println("\n--- HABITACIONES DISPONIBLES ---");
                    gestorHabitaciones.listarHabitacionesDisponibles()
                    break;
                case 2: // Realizar Nueva Reserva
                    System.out.println("\n--- CREACIÓN DE RESERVA ---");
                    int numHab = leerOpcion("Ingrese número de habitación a reservar: ");
                    Habitacion habitacionElegida = gestorHabitaciones.(numHab);
                    if (habitacionElegida == null) {
                        System.out.println("Habitación no encontrada.");
                        break;
                    }
                    System.out.println("Ingrese la cantidad de días de reserva: ");
                    int dias = leerOpcion();
                    if (dias <= 0) throw new datoInvalidoException("La cantidad de días debe ser positiva.");

                    // Simulación de fechas
                    Date fechaIn = new Date();
                    Date fechaOut = new Date(fechaIn.getTime() + (long) dias * 24 * 60 * 60 * 1000);

                    gestorHabitaciones.crearReserva(pasajero, habitacionElegida, fechaIn, fechaOut);
                    System.out.println("Reserva creada exitosamente para la habitación " + numHab);
                    break;

                case 3: // Modificar Datos Personales
                    System.out.println("\n--- MODIFICAR DATOS PERSONALES ---");
                    System.out.println("Confirme su contraseña actual para continuar: ");
                    String passActual = leerString();

                    if (pasajero.validarContrasenia(passActual)) {
                        System.out.println("Funcionalidad pendiente: Implementar la actualización de datos personales en SistemaHotel.");
                    } else {
                        System.out.println("Contraseña incorrecta. Operación cancelada.");
                    }
                    break;

                case 4: // Cambiar Contraseña
                    System.out.println("\n--- CAMBIAR CONTRASEÑA ---");
                    System.out.println("Ingrese su Contraseña Actual: ");
                    String oldPass = leerString();

                    if (pasajero.validarContrasenia(oldPass)) {
                        System.out.println("");
                    } else {
                        System.out.println("Contraseña actual incorrecta. Operación cancelada.");
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error en la operación de Pasajero: " + e.getMessage());
        }
    }
}
