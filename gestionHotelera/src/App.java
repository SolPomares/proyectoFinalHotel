import Clases.*;
import Enums.TipoRol;
import Enums.Turno;
import Excepciones.AccesoDenegadoException;
import Excepciones.LecturaJsonException;
import Excepciones.datoInvalidoException;
import ManejoJSON.Utilidades;
import Excepciones.JSONException;

import java.sql.SQLOutput;
import java.util.*;

// Comenzandogit add
public class App {
    private static SistemaHotel<Usuario> gestorUsuarios;
    private static SistemaHabitaciones gestorHabitaciones;
    private static final Scanner teclado = new Scanner(System.in);
    private static Usuario usuarioActual = null;

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
            List<Usuario> usuariosCargados = Utilidades.cargarUsuarios();
            List<Habitacion> habitacionesCargadas = Utilidades.cargarHabitaciones();

            // Si es nulo, creamos una lista vacía para evitar el NullPointerException
            /// Aca me ayude con chat pq no salia
            ArrayList<Usuario> usuarios = (usuariosCargados != null)
                    ? new ArrayList<>(usuariosCargados)
                    : new ArrayList<>();

            ArrayList<Habitacion> habitaciones = (habitacionesCargadas != null)
                    ? new ArrayList<>(habitacionesCargadas)
                    : new ArrayList<>();

            gestorUsuarios = new SistemaHotel<>(usuarios);
            gestorHabitaciones = new SistemaHabitaciones(habitaciones, new ArrayList<>());

            cargarAdminGenerico();

            System.out.println("Sistema cargado exitosamente");

            // Menú principal
            menuPrincipalLoop();

        } catch (JSONException e) {
            throw new LecturaJsonException("Error cargando datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("---ERROR REAL CAPTURADO (¡ESTO ES LO QUE NECESITAMOS!) ---");
            e.printStackTrace();
            System.err.println("----------------------------------------------------------");
            throw new RuntimeException("Error Inesperado al inicializar");
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
                    ADMIN_PASS,
                    true
            );

            try {
                //Aqui simplificamoes metodo para que si o si cargu un usuario inicial y no quede null que se arrastra
                gestorUsuarios.agregar(adminInicial);//falta hacer el metodo para empleado asi q use este porque el sistemaHotel acepta t elemntos
                System.out.println("[INFO] Administrador genérico ('admin'/'admin123') cargado.");
            } catch (Exception e) { // <-- Cambiado de datoInvalidoException a Exception
                System.err.println("[ERROR] Error al cargar Admin inicial: " + e.getMessage());
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
                System.out.println("[INFO] Administrador genérico ('recepcion'/'recepcion123') cargado.");
            } catch (Exception e) { // <-- Cambiado de datoInvalidoException a Exception
                System.err.println("[ERROR] Error al cargar Recep inicial: " + e.getMessage());
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
                System.out.println("[INFO] Administrador genérico ('pasajero/'pasajero123') cargado.");
            } catch (Exception e) { // <-- Cambiado de datoInvalidoException a Exception
                System.err.println("[ERROR] Error al cargar pasajero inicial: " + e.getMessage());
            }
        }
    }


    private static String leerString() {
        String linea;
        do {
            linea = teclado.nextLine().trim();
        } while (linea.isEmpty());
        return linea;
    }

    private static int leerOpcion() {
        int opcionNum = teclado.nextInt();
        teclado.nextLine();

        if (opcionNum < 0) {
            throw new datoInvalidoException("El numero debe ser positivo.");
        }

        return opcionNum;
    }

    public static void menuPrincipalLoop() {
        int opcionMenu = -1;

        do {
            // Si nadie está logueado, forzamos el login
            if (usuarioActual == null) {
                //Aca puse este try/catch porque si ponias mal el login se rompia el programa
               try {
                   usuarioActual = login();
               }catch (AccesoDenegadoException e){
                   System.out.println("ERROR DE VALIDACION - Vuelva a intentar");
               }
                if (usuarioActual == null) {
                    System.out.println("\n¿Intentar de nuevo o Salir? (0 para salir, cualquier tecla para reintentar): ");
                    String input = teclado.nextLine();
                    if (input.equals("0")) {
                        break;
                    }
                }

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
                        //opcionMenu = -1; //vuelvo al ingreso
                }
            }
            System.out.println("Desea Salir presione 0");
            opcionMenu = leerOpcion();
        } while (opcionMenu != 0);
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
    private static void mostrarMenuAdministrador() {

        System.out.println("--- MENÚ ADMINISTRADOR ---");
        System.out.println("1. Alta de Nuevo Empleado");
        System.out.println("2. Baja de Empleado (por DNI)");
        System.out.println("3. Listar Todos los Empleados");
        System.out.println("4. Listar Todos los Usuarios");
        System.out.println("5. Buscar Usuario por DNI");
        System.out.println("6. Volver al Mnu");
        System.out.println("9. Cerrar Sesión");

        int opcionM = leerOpcion();
        manejarOpcionAdministrador(opcionM);
    }

    private static void mostrarMenuRecepcionista() {
        System.out.println("--- MENÚ RECEPCIONISTA ---");
        System.out.println("1. Registrar Check-In de Pasajero");
        System.out.println("2. Registrar Check-Out de Pasajero");
        System.out.println("3. Dar de Alta Pasajero");
        System.out.println("4. Dar de Baja Pasajero");
        System.out.println("5. Mostrar Pasajeros Registrados");
        System.out.println("6. Crear Nueva Reserva");
        System.out.println("9. Cerrar Sesión");
        System.out.println("8. Salir del Programa");
        int opcionM = leerOpcion();
        manejarOpcionRecepcionista(opcionM);
    }

    private static void mostrarMenuPasajero() {
        System.out.println("\n--- ZONA DE CLIENTES ---");
        System.out.println("1. Listar Habitaciones Disponibles");
        System.out.println("2. Realizar Nueva Reserva");
        System.out.println("3. Cambiar contraseña");
        System.out.println("9. Cerrar Sesión");
        System.out.println("8. Salir del Programa");
        int opcionM = leerOpcion();
        manejarOpcionPasajero(opcionM);
    }

    // MANEJO DE OPCIONES
    private static void manejarOpcionAdministrador(int opcionM) {
        try {
            switch (opcionM) {

                case 1: {
                    try {

                        System.out.println("\n--- ALTA EMPLEADO ---");
                        // Lógica para crear empleado (simulada por ahora)
                        int opcionSeguir = 1;
                        do {

                            System.out.println("¿Qué tipo de empleado desea crear?");
                            System.out.println("1. Recepcionista");
                            System.out.println("2. Administrador");
                            System.out.println("3. Otro Empleado");
                            System.out.println("4. Salir del Programa");
                            System.out.print("Seleccione tipo: ");
                            int tipo = leerOpcion(); //

                            System.out.println("Ingrese los datos del nuevo empleado:");
                            System.out.print("Nombre: ");
                            String nombre = leerString();

                            System.out.print("Apellido: ");
                            String apellido = leerString();

                            System.out.print("DNI: ");
                            int dni = leerOpcion();

                            System.out.print("Email: ");
                            String eMail = leerString(); // (Tu variable era 'EMail')

                            System.out.print("Nombre de Usuario (para login): ");
                            String nombreUsuarioEmp = leerString();

                            System.out.print("Contraseña (para login): ");
                            String contraseniaEmp = leerString();

                            System.out.print("¿Dar acceso al sistema? (S/N): ");
                            boolean acceso = leerString().equalsIgnoreCase("S");

                            Empleados nuevoEmpleado = null;

                            switch (tipo) {

                                case 1: { // Crear Recepcionista
                                    System.out.print("Turno (MANANA, TARDE, NOCHE): ");
                                    Turno turno = Turno.valueOf(leerString().toUpperCase());

                                    nuevoEmpleado = new Recepcionista(
                                            UUID.randomUUID(), nombre, apellido,
                                            dni, TipoRol.RECEPCIONISTA, nombreUsuarioEmp, eMail, acceso,
                                            turno, contraseniaEmp, gestorHabitaciones);
                                    break;
                                }

                                case 2: {
                                    nuevoEmpleado = new Administrador(
                                            UUID.randomUUID(), nombre, apellido, dni,
                                            TipoRol.ADMINISTRADOR, nombreUsuarioEmp, eMail,
                                            contraseniaEmp, acceso
                                    );
                                    break;
                                }

                                case 3: {
                                    System.out.print("Turno (MANANA, TARDE O NOCHE ?): ");
                                    Turno turno = Turno.valueOf(leerString().toUpperCase());

                                    nuevoEmpleado = new PersonalMantenimiento(
                                            UUID.randomUUID(), nombre, apellido, dni, TipoRol.MANTENIMIENTO,
                                            nombreUsuarioEmp, eMail, acceso, turno
                                    );

                                    break;
                                }

                                case 4: {
                                    System.out.println("Volvemos");
                                    mostrarMenuAdministrador();
                                    break;
                                }

                                default: {
                                    // 'nuevoEmpleado' permanece null
                                    if (nuevoEmpleado != null) {
                                        // (Casteo de 'usuarioActual' a (Empleados) es necesario para la firma)
                                        gestorUsuarios.AltaEmpleado((Empleados) usuarioActual, nuevoEmpleado);
                                        System.out.println("Empleado creado exitosamente.");
                                    }
                                    // Se ejecuta si 'tipo' no es 1, 2, o 3
                                    System.out.println("Tipo no válido. Operación cancelada.");

                                    System.out.println("Quiere Salir presione 5");
                                    opcionSeguir = leerOpcion();

                                    break;

                                }

                            }
                        } while (opcionSeguir != 5);

                    } catch (datoInvalidoException e) {
                        System.out.println("Error de datos: " + e.getMessage());
                    } catch (IllegalArgumentException e) {
                        // Captura si el usuario escribe un Turno inválido
                        System.out.println("Error: El valor ingresado (ej. Turno) no es válido.");
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }

                    break;
                }


                case 2: {
                    System.out.print("\n--- BAJA EMPLEADO ---\nDNI del empleado a dar de baja: ");
                    int dniBaja = Integer.parseInt(teclado.nextLine());
                    gestorUsuarios.bajaEmpleado((Empleados) usuarioActual, dniBaja);

                    break;
                }

                case 3: {
                    System.out.println("\n--- LISTA DE EMPLEADOS ---");
                    gestorUsuarios.ListarEmpleados().forEach(e -> e.imprimirDatos());
                    break;
                }

                case 4: {
                    System.out.println("\n--- TODOS LOS USUARIOS ---");
                    gestorUsuarios.imprimirTodosUsuarios();
                    break;
                }

                case 5: {
                    System.out.print("\n--- BUSCAR USUARIO ---\nIngrese DNI: ");
                    int dniBuscar = Integer.parseInt(teclado.nextLine());
                    Usuario usuario = gestorUsuarios.buscarUsuario(dniBuscar);
                    if (usuario != null) {
                        usuario.imprimirDatos();
                    } else {
                        System.out.println("Usuario no encontrado");
                    }
                    break;
                }

                case 6: {
                    System.out.println("Volvemos");
                    mostrarMenuAdministrador();
                    break;
                }

                case 9: {
                    System.out.println("Cerrando sesión de Administrador...");
                    usuarioActual = null;
                    break;
                }

                case 8: {
                    System.out.println("Saliendo del programa...");
                    break;
                }

                default: {
                    System.out.println("Opción no válida para Administrador.");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manejarOpcionRecepcionista(int opcionM) {
        Recepcionista recep = (Recepcionista) usuarioActual;
        try {
            switch (opcionM) {
                case 1:
                    System.out.println("\n--- CHECK-IN ---");
                    System.out.print("Ingrese ID de la Reserva para Check-In (UUID): ");
                    UUID reservaId = UUID.fromString(teclado.nextLine());
                    boolean checkInExitoso = gestorHabitaciones.checkin(reservaId);
                    if (checkInExitoso) {
                        System.out.println("✅ Check-in realizado exitosamente");
                    } else {
                        System.out.println("La reserva ingresada no existe");
                    }
                    break;
                case 2:
                    System.out.println("\n--- CHECK-OUT ---");
                    System.out.print("Ingrese ID de la Reserva para Check-Out (UUID): ");
                    UUID idReservaOut = UUID.fromString(teclado.nextLine());
                    boolean checkOutExitoso = gestorHabitaciones.checkout(idReservaOut);
                    if (checkOutExitoso) {
                        System.out.println("Check-out realizado exitosamente");
                    } else {
                        System.out.println("La reserva ingresada no existe");
                    }
                    break;
                case 3:
                    System.out.println("\n--- ALTA PASAJERO ---");
                    System.out.println(" INGRESO DE DATOS DEL PASAJERO");

                    System.out.print("Nombre: ");
                    String nombre = leerString();

                    System.out.print("Apellido: ");
                    String apellido = leerString();

                    System.out.print("DNI: ");
                    int dni = leerOpcion();

                    System.out.print("Email: ");
                    String eMail = leerString();

                    System.out.println("Domicilio de origen:");
                    String domicilioOrigen = leerString();

                    Pasajero pasajeroNuevo = new Pasajero(nombre, apellido, dni, TipoRol.PASAJERO, domicilioOrigen);
                    gestorUsuarios.altaPasajero((Empleados) usuarioActual, pasajeroNuevo);

                    break;
                case 4:
                    System.out.print("\n--- BAJA PASAJERO ---\nDNI del pasajero a dar de baja: ");
                    System.out.println(" Ingrese el dni del pasajero que desea dar de baja: ");
                    int dniBaja = Integer.parseInt(teclado.nextLine());
                    gestorUsuarios.bajaPasajero((Empleados) usuarioActual, dniBaja);
                    break;
                case 5:
                    System.out.println("\n--- PASAJEROS REGISTRADOS ---");
                    gestorUsuarios.mostrarPasajeros();
                    break;
                case 6:
                    System.out.println("Cerrando sesión de Recepcionista...");
                    usuarioActual = null;
                    break;
                case 7: {
                    System.out.println("Volvemos");
                    mostrarMenuRecepcionista();
                    break;
                }
                case 8:
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

    private static void manejarOpcionPasajero(int opcionM) {
        Pasajero pasajero = (Pasajero) usuarioActual;
        try {
            switch (opcionM) {
                case 1: // Listar Habitaciones Disponibles
                    System.out.println("\n--- HABITACIONES DISPONIBLES ---");
                    gestorHabitaciones.mostrarHabitacionesDisponibles();

                    List<Habitacion> disponibles = gestorHabitaciones.listarHabitacionesDisponibles();
                    if (disponibles.isEmpty()) {
                        System.out.println("No hay habitaciones disponibles en este momento.");
                    } else {
                        disponibles.forEach(h ->
                                System.out.println(
                                        "Nro: " + h.getNumeroHabitacion() +
                                                " | Tipo: " + h.getTipoHabitacion() +
                                                " | Precio: $" + h.getValorDiario()
                                ));
                    }

                    break;

                case 2: // Realizar Nueva Reserva
                    System.out.println("\n--- CREACIÓN DE RESERVA ---");
                    System.out.println("Ingrese número de habitación a reservar: ");
                    int numHab = leerOpcion();
                    Habitacion habitacionElegida = gestorHabitaciones.obtenerHabitacionXNumero(numHab);

                    if (habitacionElegida == null) {
                        System.out.println("Habitación no encontrada.");
                        break;
                    }

                    System.out.println("Ingrese la cantidad de días de reserva: ");
                    int dias = leerOpcion();
                    Date fechaIn = new Date();
                    Date fechaOut = new Date(fechaIn.getTime() + (long) dias * 24 * 60 * 60 * 1000);

                    gestorHabitaciones.crearReserva(pasajero, habitacionElegida, fechaIn, fechaOut);

                    break;

                case 3:
                    System.out.println("\n--- CAMBIAR CONTRASEÑA ---");
                    System.out.println("Ingrese su Contraseña Actual: ");
                    String oldPass = leerString();

                    if (pasajero.validarContrasenia(oldPass)) {
                        System.out.println("Contraseña Modificada");
                    } else {
                        System.out.println("Contraseña actual incorrecta. Operación cancelada.");
                    }
                    break;
                case 4: {
                    System.out.println("Volvemos");
                    mostrarMenuPasajero();
                    break;
                }

                case 9:
                    System.out.println("EXIT - Gracias por Visitarnos");
                    usuarioActual = null;
                    break;

                default:
                    System.out.println("EXIT - Gracias por Visitarnos");
                    System.out.println("Opción inválida.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error en la operación de Pasajero: " + e.getMessage());
        }
    }
}
