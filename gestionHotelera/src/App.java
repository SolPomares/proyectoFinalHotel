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
    private static Empleados usuarioActual = null;

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

            System.out.println("Sistema cargado exitosamente");

            // Menú principal
            menuPrincipalLoop();

        } catch (JSONException e) {
            throw new LecturaJsonException("Error cargando datos: " + e.getMessage());
        }catch (Exception e){
            throw new RuntimeException("Error Inesperado al inicializar el sistema");
        } finally {
            if(teclado != null) {
                teclado.close();
            }
        }
    }

    public static void menuPrincipalLoop() {
        int opcion = -1;

        while (opcion != 0) {
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
                    default:
                        System.out.println("Rol no reconocido o sin acceso.");
                        usuarioActual = null;
                        continue;
                }

                System.out.print("Ingrese una opción: ");
                try {
                    opcion = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Opción inválida. Ingrese un número.");
                    continue;
                }

                // Procesar opción según rol
                switch (usuarioActual.getTipoRol()) {
                    case ADMINISTRADOR:
                        manejarOpcionAdministrador(opcion);
                        break;
                    case RECEPCIONISTA:
                        manejarOpcionRecepcionista(opcion);
                        break;
                }
            }
        }
        System.out.println("👋 Saliendo del sistema. ¡Adiós!");
    }

    // LOGIN MEJORADO (del menú que me pasaste)
    public static Empleados login() {
        System.out.println("\n=== INGRESO AL SISTEMA ===");
        System.out.print("Usuario: ");
        String user = teclado.nextLine();
        System.out.print("Contraseña: ");
        String pass = teclado.nextLine();

        Usuario usuarioEncontrado = gestorUsuarios.gestionarAcceso(user, pass);

        if (usuarioEncontrado instanceof Empleados) {
            Empleados empleado = (Empleados) usuarioEncontrado;
            System.out.println("Login exitoso - " + empleado.getTipoRol());
            return empleado;
        }

        System.out.println("Acceso denegado. Verifique usuario, contraseña o permisos.");
        return null;
    }

    // MENÚS (del menú que me pasaste - más organizados)
    private static void mostrarMenuAdministrador() {
        System.out.println("--- MENÚ ADMINISTRADOR ---");
        System.out.println("1. Alta de Nuevo Empleado");
        System.out.println("2. Baja de Empleado (por DNI)");
        System.out.println("3. Listar Todos los Empleados");
        System.out.println("4. Listar Todos los Usuarios");
        System.out.println("5. Buscar Usuario por DNI");
        System.out.println("9. Cerrar Sesión");
        System.out.println("0. Salir del Programa");
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
        System.out.println("0. Salir del Programa");
    }

    // MANEJO DE OPCIONES (combinación de ambos)
    private static void manejarOpcionAdministrador(int opcion) {
        try {
            switch (opcion) {
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
}