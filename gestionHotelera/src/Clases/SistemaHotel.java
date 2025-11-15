package Clases;

import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.Iterator;
import Enums.TipoRol;

public class SistemaHotel <T extends Usuario> {
    ArrayList<T> gestorHotel;

    //Constructores
    public SistemaHotel() {
        this.gestorHotel = new ArrayList<>();
    }

    public SistemaHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = new ArrayList<>(gestorHotel);
    }

    //getters y Setters
    public ArrayList<T> getGestorHotel() {
        return gestorHotel;
    }

    public void setGestorHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    // Busqueda

    public Usuario buscarUsuario(int dni) {
        for (T usuario : this.gestorHotel) {
            if (usuario.getDni() == dni) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarEmpleado(String nombreDeUsuario) {
        for (T usuario : this.gestorHotel) { // Usamos 'usuario' para la iteración
            if (usuario instanceof Empleados empleado) { // Pattern Matching (limpieza)
                if (empleado.getNombreUsuario().equals(nombreDeUsuario)) {
                    return empleado;
                }
            }
        }
        return null;
    }

    // VALIDACIONES Y SEguridad

    public Empleados gestionarAcceso(String nombreUsuario, String contrasenia) {
        Usuario empleado = buscarEmpleado(nombreUsuario);
        if (empleado == null) {
            System.out.println("ERROR! empleado no encontrado, acceso invalido");
            return null;
        }

        if (empleado instanceof Empleados emp) {
            if (emp.validarContrasenia(contrasenia)) {
                if (emp.tienePermisoSistema()) {
                    System.out.println("Acceso exitoso como: " + emp.getTipoRol());
                    return emp; // Devuelve el Empleado validado

                } else {
                    System.err.println("ERROR! No tiene permiso sistema");
                }
            }
        }
        return null;
    }

    private Empleados validarCredenciales(String nombreUsuario, String contrasenia) {
        // gestionarAcceso ya se encarga de buscar, validar contraseña y permiso de sistema.
        Empleados empleadoValidado = gestionarAcceso(nombreUsuario, contrasenia);

        if(empleadoValidado == null) {
            return null;
        }
        return empleadoValidado;
    }

    public boolean validarAdmin(String nombreUsuarioAdmin, String contraseniaAdmin) {

        Empleados adminValidado = validarCredenciales(nombreUsuarioAdmin, contraseniaAdmin);

        if (adminValidado != null && adminValidado.getTipoRol() == TipoRol.ADMINISTRADOR) {
            System.out.println("Autenticación de ADMINISTRADOR exitosa.");
            return true;
        } else {
            System.err.println(" ACCESO DENEGADO: La acción requiere credenciales de ADMINISTRADOR.");
            return false;
        }
    }

    public boolean validarRecepcionista(String nombreUsuarioRecep, String contraseniaRecep) {

        Empleados empleadoValidado = validarCredenciales(nombreUsuarioRecep, contraseniaRecep);

        if (empleadoValidado != null) {
            TipoRol rol = empleadoValidado.getTipoRol();

            if (rol == TipoRol.RECEPCIONISTA || rol == TipoRol.ADMINISTRADOR) {
                System.out.println(" Autenticación de Recepcionista/Admin exitosa.");
                return true;
            }
        }

        System.err.println(" ACCESO DENEGADO: La acción requiere credenciales válidas de RECEPCIONISTA o ADMINISTRADOR.");
        return false;
    }

    // Metodo para dar de baja a un empleado SOLO PUEDE EJECUTAR UN ADMINISTRADOR
    public void bajaEmpleado(Empleados quienEjecuta, int dni) throws datoInvalidoException {
        if (quienEjecuta.getTipoRol() != TipoRol.ADMINISTRADOR) {
            System.err.println("ERROR! No tiene permiso sistema para dar de baja empleados.");
            return;
        }

        String dniComoTexto = String.valueOf(dni);
        if (dniComoTexto.length() != 8) {
            throw new datoInvalidoException("Error!! DNI invalido (debe tener 8 dígitos).");
        }

        Usuario usuarioABorrar = buscarUsuario(dni);

        if (usuarioABorrar == null || !(usuarioABorrar instanceof Empleados)) {
            System.out.println("No se encontró un Empleado con DNI " + dni + ".");
            return;
        }

        // Eliminación
        this.gestorHotel.remove(usuarioABorrar);
        System.out.println("✅ Empleado con DNI " + dni + " eliminado.");
    }

    // Metodo para dar de alta a un pasajero SOLO PUEDE HACERLO EL RECEPCIONISTA (o Admin)
    public void altaPasajero(Empleados quienEjecuta, Pasajero pasajeroNuevo) throws datoInvalidoException {
        if (quienEjecuta.getTipoRol() != TipoRol.RECEPCIONISTA && quienEjecuta.getTipoRol() != TipoRol.ADMINISTRADOR) {
            System.err.println("ERROR! No tiene permiso sistema para dar de alta pasajeros.");
            return;
        }

        if (pasajeroNuevo == null) {
            throw new datoInvalidoException("ERROR! El objeto Pasajero a dar de alta no puede ser nulo.");
        }

        if (buscarUsuario(pasajeroNuevo.getDni()) != null) {
            throw new datoInvalidoException("ERROR! Ya existe un usuario con el DNI: " + pasajeroNuevo.getDni());
        }

        gestorHotel.add((T) pasajeroNuevo);
        System.out.println("✅ Pasajero " + pasajeroNuevo.getNombre() + " agregado exitosamente.");
    }

    // Metodo para dar de baja un pasajero SOLO EJECUTA RECEPCIONISTA (o Admin)
    public void bajaPasajero(Empleados quienEjecuta, int dni) throws datoInvalidoException {
        if (quienEjecuta.getTipoRol() != TipoRol.RECEPCIONISTA && quienEjecuta.getTipoRol() != TipoRol.ADMINISTRADOR) {
            System.err.println("ACCESO DENEGADO: Solo un ADMINISTRADOR o RECEPCIONISTA puede realizar el check-out/baja de pasajeros.");
            return;
        }

        String dniComoTexto = String.valueOf(dni);
        if (dniComoTexto.length() != 8) {
            throw new datoInvalidoException("Error!! DNI invalido (debe tener 8 dígitos).");
        }

        Usuario usuarioABorrar = buscarUsuario(dni);

        if (usuarioABorrar == null || !(usuarioABorrar instanceof Pasajero)) {
            System.out.println("No se encontró un Pasajero con DNI " + dni + ".");
            return;
        }

        // Eliminación (Check-out)
        this.gestorHotel.remove(usuarioABorrar);
        System.out.println("✅ Pasajero con DNI " + dni + " eliminado/check-out realizado.");
    }

    // --- MÉTODOS DE MOSTRAR / LISTAR ---

    public void imprimirTodosUsuarios() {
        System.out.println("=== USUARIOS REGISTRADOS ===");
        for (T usuario : this.gestorHotel) {
            // El método imprimirDatos() es abstracto en Usuario
            usuario.imprimirDatos();
        }
    }

    public void mostrarEmpleados() {
        System.out.println("=== EMPLEADOS REGISTRADOS ===");
        boolean hayEmpleados = false;

        for (T usuario : gestorHotel) {
            if (usuario instanceof Empleados empleado) {
                empleado.imprimirDatos();
                hayEmpleados = true;
            }
        }

        if (!hayEmpleados) {
            System.out.println("No hay empleados registrados.");
        }
    }

    public ArrayList<Empleados> ListarEmpleados() {
        ArrayList<Empleados> empleadosLista = new ArrayList<>();
        for (T usuario : this.gestorHotel) {
            if (usuario instanceof Empleados empleado) {
                empleadosLista.add(empleado);
            }
        }
        return empleadosLista;
    }

    public void mostrarPasajeros() {
        System.out.println("=== PASAJEROS REGISTRADOS ===");
        boolean hayPasajeros = false;

        for (T usuario : gestorHotel) {
            if (usuario instanceof Pasajero pasajero) {
                pasajero.imprimirDatos();
                hayPasajeros = true;
            }
        }

        if (!hayPasajeros) {
            System.out.println("No hay pasajeros registrados.");
        }
    }

}