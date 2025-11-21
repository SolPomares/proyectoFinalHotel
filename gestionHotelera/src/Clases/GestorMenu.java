package Clases;

import Enums.TipoRol;
import Enums.Turno;
import Excepciones.AccesoDenegadoException;
import Excepciones.datoInvalidoException;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GestorMenu {
    private SistemaHotel<Usuario> gestorUsuarios;
    private SistemaHabitaciones gestorHabitaciones;
    private Scanner teclado;
    private Usuario usuarioActual;

    //constructor
    public GestorMenu(SistemaHotel<Usuario> gestorUsuarios, SistemaHabitaciones gestorHabitaciones, Scanner teclado, Usuario usuarioActual) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorHabitaciones = gestorHabitaciones;
        this.teclado = new Scanner(System.in);
        this.usuarioActual = null;
    }

    //metodos
    public void iniciarMenus() {
        System.out.println("Bienvenido al Sistema de Gestión Hotelera");
        menuPrincipalLoop();

        teclado.close();
    }

    private void menuPrincipalLoop() {
        boolean programaActivo = true;

        while (programaActivo) {
            // Si nadie está logueado, forzamos el login
            if (usuarioActual == null) {
                //Aca puse este try/catch porque si ponias mal el login se rompia el programa
                try {
                    usuarioActual = login();
                } catch (AccesoDenegadoException e) {
                    System.out.println("ERROR DE VALIDACION - Vuelva a intentar");
                    System.out.println("Presione enter para intentar de nuevo");
                    teclado.nextLine();
                    continue;
                }

                // Mostrar menú según el rol
                System.out.println("\n=============================================");
                System.out.println("  Bienvenido, " + usuarioActual.getNombre() + " (" + usuarioActual.getTipoRol() + ")");
                System.out.println("=============================================");

                switch (usuarioActual.getTipoRol()) {
                    case ADMINISTRADOR:
                        programaActivo = mostrarMenuAdministrador();
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
                }
            }
            System.out.println("Aplicación finalizada correctamente.");
        }
    }

    //LOGIN
    public  Usuario login() throws AccesoDenegadoException {
        System.out.println("\n=== INGRESO AL SISTEMA ===");
        System.out.println("Usuario: ");
        String user = leerString();
        System.out.println("Contraseña: ");
        String pass = leerString();

        // aca se gestiona el acceso
        Usuario usuarioEncontrado = gestorUsuarios.gestionarAcceso(user, pass);

        if (usuarioEncontrado != null) {
            return usuarioEncontrado;
        }
        throw new AccesoDenegadoException("Acceso denegado. Verifique usuario, contraseña o permisos.");
    }

    //Menus
    private boolean mostrarMenuAdministrador() {

        boolean seguirEnPrograma = true;

        int opcionSalida = 0;

        do {

            System.out.println("---          MENÚ ADMINISTRADOR        ---");
            System.out.println("1. Alta de Nuevo Empleado");
            System.out.println("2. Baja de Empleado (por DNI)");
            System.out.println("3. Listar Todos los Empleados");
            System.out.println("4. Listar Todos los Usuarios");
            System.out.println("5. Buscar Usuario por DNI");
            System.out.println("6. LEER Archivo Habitaciones ");
            System.out.println("7. GRABAR Archivo Habitaciones ");
            System.out.println("8. Hacer BackUp de Usuarios");
            System.out.println("9. Leer BackUp de Usuarios");
            System.out.println("10. Asignar o Quitar Permisos de sistema");
            System.out.println("0. Cerrar Sesión");
            System.out.println("------------------------------------------");

            System.out.print("Elija una opción: ");
            int opcionM = leerOpcion();

            //convertimos a pasajero para usar sus metodos
            //Pasajero pasajero = (Pasajero) usuarioActual;

            try {
                switch (opcionM) {

                    case 1: {
                        int opcion = leerOpcion();
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
                                        pausa();
                                        break;
                                    }

                                    case 2: {
                                        nuevoEmpleado = new Administrador(
                                                UUID.randomUUID(), nombre, apellido, dni,
                                                TipoRol.ADMINISTRADOR, nombreUsuarioEmp, eMail,
                                                contraseniaEmp, acceso
                                        );
                                        pausa();
                                        break;
                                    }

                                    case 3: {
                                        System.out.print("Turno (MANANA, TARDE O NOCHE ?): ");
                                        Turno turno = Turno.valueOf(leerString().toUpperCase());

                                        nuevoEmpleado = new PersonalMantenimiento(
                                                UUID.randomUUID(), nombre, apellido, dni, TipoRol.MANTENIMIENTO,
                                                nombreUsuarioEmp, eMail, acceso, turno
                                        );

                                        pausa();
                                        break;
                                    }

                                    case 4: {
                                        System.out.println("Volvemos");
                                        pausa();
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

                                        pausa();
                                        break;

                                    }

                                }
                            } while (opcion != 0 && opcion != 9);
                            return true;

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
                        pausa();
                        break;
                    }

                    case 3: {
                        System.out.println("\n--- LISTA DE EMPLEADOS ---");
                        gestorUsuarios.ListarEmpleados().forEach(e -> e.imprimirDatos());
                        pausa();
                        break;
                    }

                    case 4: {
                        System.out.println("\n--- TODOS LOS USUARIOS ---");
                        gestorUsuarios.imprimirTodosUsuarios();
                        pausa();
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
                        pausa();
                        break;
                    }

                    case 6: {
                        System.out.println("Volvemos");
                        mostrarMenuAdministrador();
                        pausa();
                        break;
                    }

                    case 9: {
                        System.out.println("Cerrando sesión de Administrador...");
                        usuarioActual = null;
                        pausa();
                        break;
                    }

                    case 0: {
                        System.out.println("Saliendo del programa...");
                        pausa();
                        return false;

                    }

                    default: {
                        System.out.println("Opción no válida para Administrador.");
                        pausa();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (true);
    }

    private boolean mostrarMenuRecepcionista() {
        boolean seguirEnPrograma = true;
        int opcionSalida = 0;

        do {
            System.out.println("---       MENÚ RECEPCIONISTA       ---");
            System.out.println("1. Registrar Check-In de Pasajero");
            System.out.println("2. Registrar Check-Out de Pasajero");
            System.out.println("3. Dar de Alta Pasajero");
            System.out.println("4. Dar de Baja Pasajero");
            System.out.println("5. Mostrar Pasajeros Registrados");
            System.out.println("6. Crear Nueva Reserva");
            System.out.println("7. Listar reservas");
            System.out.println("8. Ocupar habitacion (por limpieza/mantenimientp");
            System.out.println("9. Cerrar Sesión");
            System.out.println("0. Salir del Programa");

            int opcionM = leerOpcion();

            try {
                switch (opcionM) {
                    case 1: {
                        System.out.println("\n--- CHECK-IN ---");
                        System.out.print("Ingrese ID de la Reserva para Check-In (UUID): ");
                        UUID reservaId = UUID.fromString(teclado.nextLine());
                        boolean checkInExitoso = gestorHabitaciones.checkin(reservaId);
                        if (checkInExitoso) {
                            System.out.println("Check-in realizado exitosamente");
                        } else {
                            System.out.println("La reserva ingresada no existe");
                        }
                        pausa();
                        break;
                    }
                    case 2: {
                        System.out.println("\n--- CHECK-OUT ---");
                        System.out.print("Ingrese ID de la Reserva para Check-Out (UUID): ");
                        UUID idReservaOut = UUID.fromString(teclado.nextLine());
                        boolean checkOutExitoso = gestorHabitaciones.checkout(idReservaOut);
                        if (checkOutExitoso) {
                            System.out.println("Check-out realizado exitosamente");
                        } else {
                            System.out.println("La reserva ingresada no existe");
                        }
                        pausa();
                        break;
                    }
                    case 3: {
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
                        pausa();
                        break;
                    }
                    case 4: {
                        System.out.print("\n--- BAJA PASAJERO ---\nDNI del pasajero a dar de baja: ");
                        System.out.println(" Ingrese el dni del pasajero que desea dar de baja: ");
                        int dniBaja = Integer.parseInt(teclado.nextLine());
                        gestorUsuarios.bajaPasajero((Empleados) usuarioActual, dniBaja);
                        pausa();
                        break;
                    }
                    case 5: {
                        System.out.println("\n--- PASAJEROS REGISTRADOS ---");
                        gestorUsuarios.mostrarPasajeros();
                        pausa();
                        break;
                    }
                    case 6: {
                        System.out.println("Cerrando sesión de Recepcionista...");
                        usuarioActual = null;
                        pausa();
                        break;
                    }
                    case 7: {
                        System.out.println("Volvemos");
                        mostrarMenuRecepcionista();
                        pausa();
                        break;
                    }
                    case 0: {
                        System.out.println("Saliendo del programa...");
                        pausa();
                        return false;

                    }
                    default: {
                        System.out.println("Opción no válida para Recepcionista.");
                        pausa();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());

            }
        } while (true);
    }

    private boolean mostrarMenuPasajero() {

        boolean seguirEnPrograma = true;

        int opcionSalida = 0;

        do {
            System.out.println("\n---      ZONA DE CLIENTES     ---");
            System.out.println("1. Listar Habitaciones Disponibles");
            System.out.println("2. Realizar Nueva Reserva");
            System.out.println("3. Cambiar contraseña");
            System.out.println("9. Cerrar Sesión");
            System.out.println("0. Salir del Programa");
            int opcionM = leerOpcion();

            //convertimos a pasajero para usar sus metodos
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
        } while (true);
    }


    // Metodos accesorios
    private void pausa() {
        System.out.println("\nPresione Enter para volver al menú...");
        teclado.nextLine();
    }

    private  String leerString() {
        String linea;
        do {
            linea = teclado.nextLine().trim();
        } while (linea.isEmpty());
        return linea;
    }

    private int leerOpcion() {
        int opcionNum = teclado.nextInt();
        teclado.nextLine();

        if (opcionNum < 0) {
            throw new datoInvalidoException("El numero debe ser positivo.");
        }

        return opcionNum;
    }
}
