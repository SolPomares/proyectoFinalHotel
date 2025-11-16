public class App {
    public static void main(String[] args) {
    }
}


        /// POSIBLE MENU QUE ME HIZO LA IA!!!!!!!!
        /*
        // Simulación de "Login" simple
        public static Empleados login(SistemaHotel<Usuario> sistemaHotel, Scanner scanner) {
            System.out.println("\n=== INGRESO AL SISTEMA ===");
            System.out.print("Usuario: ");
            String user = scanner.nextLine();
            System.out.print("Contraseña: ");
            String pass = scanner.nextLine(); // En un sistema real se debería usar JPasswordField o métodos más seguros.

            // Asumiendo que has corregido la lógica de login en SistemaHotel
            // Este método en el SistemaHotel es 'gestionarAcceso(String nombreUsuario, String contrasenia)'
            // Pero actualmente retorna 'null' en la mayoría de los casos.

            // Simularemos la búsqueda para el boceto:
            Usuario usuarioEncontrado = sistemaHotel.buscarEmpleado(user);

            if (usuarioEncontrado instanceof Empleados) {
                Empleados empleado = (Empleados) usuarioEncontrado;
                // Si tiene permiso y la contraseña es válida
                if (empleado.validarContrasenia(pass) && empleado.tienePermisoSistema()) {
                    return empleado;
                }
            }
            System.err.println("Acceso denegado. Verifique usuario, contraseña o permisos.");
            return null;
        }

        public static void main(String[] args) {
            // --- 1. Inicialización de Datos/Sistemas ---
            Scanner scanner = new Scanner(System.in);
            SistemaHotel<Usuario> gestorUsuarios = new SistemaHotel<>();
            SistemaHabitaciones gestorHabitaciones = new SistemaHabitaciones(new ArrayList<>(), new ArrayList<>());

            // NOTA: Recuerda que los constructores de Empleados necesitan la 'contrasenia'.
            // He añadido una contraseña aquí para la simulación.

            // Crear un Administrador y un Recepcionista iniciales
            Administrador admin = new Administrador(0, "Ana", "Gomez", 11111111, TipoRol.ADMINISTRADOR, "admin", "admin@hotel.com", true, "claveSecreta");
            admin.setContrasenia("1234"); // Necesitas settear la contraseña.

            Recepcionista recepcionista = new Recepcionista(0, "Beto", "Diaz", 22222222, TipoRol.RECEPCIONISTA, "recepcion", "recep@hotel.com", true, Turno.MANANA, gestorHabitaciones);
            recepcionista.setContrasenia("5678"); // Necesitas settear la contraseña.

            try {
                gestorUsuarios.altaEmpleado(admin, admin);
                gestorUsuarios.altaEmpleado(admin, recepcionista);
            } catch (Exception e) {
                System.err.println("Error al cargar empleados iniciales: " + e.getMessage());
            }

            Empleados usuarioActual = null; // Almacenará el usuario logueado.
            int opcion = -1;

            while (opcion != 0) {
                // Si nadie está logueado, forzamos el login
                if (usuarioActual == null) {
                    usuarioActual = login(gestorUsuarios, scanner);
                    if (usuarioActual == null) {
                        System.out.println("\nIntentar de nuevo o Salir (0 para salir).");
                        String input = scanner.nextLine();
                        if (input.equals("0")) {
                            opcion = 0;
                            continue;
                        }
                    }
                } else {

                    // --- 2. Menú Principal Dinámico (según el rol) ---
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
                            usuarioActual = null; // Forzar logout
                            continue;
                    }

                    System.out.print("Ingrese una opción: ");
                    try {
                        opcion = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println("Opción inválida. Ingrese un número.");
                        continue;
                    }

                    // --- 3. SWITCH CASE centralizado ---
                    switch (usuarioActual.getTipoRol()) {
                        case ADMINISTRADOR:
                            manejarOpcionAdministrador(opcion, gestorUsuarios, scanner, usuarioActual);
                            break;
                        case RECEPCIONISTA:
                            manejarOpcionRecepcionista(opcion, gestorUsuarios, gestorHabitaciones, scanner, usuarioActual);
                            break;
                    }
                }
            }
            System.out.println("Saliendo del sistema. ¡Adiós!");
            scanner.close();
        }

        // --- MÉTODOS DE MENÚ ---
        private static void mostrarMenuAdministrador() {
            System.out.println("--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Alta de Nuevo Empleado");
            System.out.println("2. Baja de Empleado (por DNI)");
            System.out.println("3. Listar Todos los Empleados");
            System.out.println("4. Asignar Permiso de Acceso");
            System.out.println("9. Cerrar Sesión");
            System.out.println("0. Salir del Programa");
        }

        private static void mostrarMenuRecepcionista() {
            System.out.println("--- MENÚ RECEPCIONISTA ---");
            System.out.println("1. Registrar Check-In de Pasajero");
            System.out.println("2. Registrar Check-Out de Pasajero");
            System.out.println("3. Dar de Alta Pasajero (sin reserva)");
            System.out.println("4. Mostrar Pasajeros Registrados");
            System.out.println("5. Crear Nueva Reserva"); // Falta implementar la creación de objetos para la reserva
            System.out.println("9. Cerrar Sesión");
            System.out.println("0. Salir del Programa");
        }

        // --- MÉTODOS DE ACCIÓN ---
        private static void manejarOpcionAdministrador(int opcion, SistemaHotel<Usuario> gestorUsuarios, Scanner scanner, Empleados admin) {
            try {
                switch (opcion) {
                    case 1:
                        System.out.println("\n--- ALTA EMPLEADO ---");
                        // Aquí iría la lógica para pedir todos los datos (nombre, dni, rol, etc.)
                        // Crear el objeto Empleado nuevo y llamar a:
                        // Empleados nuevoEmp = crearNuevoEmpleado(scanner);
                        // gestorUsuarios.altaEmpleado(admin, nuevoEmp);
                        System.out.println("[SIMULACIÓN] Alta de empleado simulada.");
                        break;
                    case 2:
                        System.out.print("DNI del empleado a dar de baja: ");
                        int dniBaja = Integer.parseInt(scanner.nextLine());
                        gestorUsuarios.bajaEmpleado(admin, dniBaja);
                        break;
                    case 3:
                        System.out.println("\n--- LISTA DE EMPLEADOS ---");
                        gestorUsuarios.ListarEmpleados().forEach(e -> e.imprimirDatos());
                        break;
                    case 4:
                        System.out.print("Nombre de usuario a asignar permisos: ");
                        String userPermiso = scanner.nextLine();
                        Empleados empleadoPermiso = (Empleados) gestorUsuarios.buscarEmpleado(userPermiso);
                        if (empleadoPermiso != null) {
                             El método asignarPermisos está en Administrador.java
                            ((Administrador) admin).asignarPermisos(gestorUsuarios, empleadoPermiso);
                        } else {
                            System.err.println("Empleado no encontrado.");
                        }
                        break;
                    case 9:
                        System.out.println("Cerrando sesión de Administrador...");
                        usuarioActual = null;
                        break;
                    case 0:
                        // Opción 0 se maneja en el while principal.
                        break;
                    default:
                        System.out.println("Opción no válida para Administrador.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Ocurrió un error en la operación: " + e.getMessage());
            }
        }

        private static void manejarOpcionRecepcionista(int opcion, SistemaHotel<Usuario> gestorUsuarios, SistemaHabitaciones gestorHabitaciones, Scanner scanner, Empleados recepcionista) {
            // Asignamos el objeto actual al tipo Recepcionista para acceder a sus métodos
            Recepcionista recep = (Recepcionista) recepcionista;
            try {
                switch (opcion) {
                    case 1:
                        System.out.println("\n--- CHECK-IN ---");
                        System.out.print("Ingrese ID de la Reserva para Check-In (UUID): ");
                        // Aquí se necesitaría el Pasajero y la Habitacion, pero se obtienen de la Reserva.
                        // UUID idReservaIn = UUID.fromString(scanner.nextLine());
                        // recep.realizarCheckIn(pasajeroDummy, habitacionDummy, idReservaIn);
                        System.out.println("[SIMULACIÓN] Check-in simulado. Necesitas una Reserva real.");
                        break;
                    case 2:
                        System.out.println("\n--- CHECK-OUT ---");
                        System.out.print("Ingrese ID de la Reserva para Check-Out (UUID): ");
                        // UUID idReservaOut = UUID.fromString(scanner.nextLine());
                        // recep.realizarCheckOut(pasajeroDummy, habitacionDummy, idReservaOut);
                        System.out.println("[SIMULACIÓN] Check-out simulado. Necesitas una Reserva real.");
                        break;
                    case 3:
                        System.out.println("\n--- ALTA PASAJERO ---");
                        // Aquí iría la lógica para pedir datos y crear un Pasajero.
                        // Pasajero nuevoPasajero = crearNuevoPasajero(scanner);
                        // gestorUsuarios.altaPasajero(recep, nuevoPasajero);
                        System.out.println("[SIMULACIÓN] Alta de pasajero simulada.");
                        break;
                    case 4:
                        gestorUsuarios.mostrarPasajeros();
                        break;
                    case 5:
                        System.out.println("\n--- CREAR RESERVA ---");
                        // Lógica para pedir Pasajero, Habitacion, fechas y llamar a:
                        // gestorHabitaciones.crearReserva(pasajero, habitacion, inicio, fin);
                        System.out.println("[SIMULACIÓN] Creación de reserva simulada.");
                        break;
                    case 9:
                        System.out.println("Cerrando sesión de Recepcionista...");
                        usuarioActual = null;
                        break;
                    case 0:
                        // Opción 0 se maneja en el while principal.
                        break;
                    default:
                        System.out.println("Opción no válida para Recepcionista.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Ocurrió un error en la operación: " + e.getMessage());
            }
        }
    }
        
    }/*

