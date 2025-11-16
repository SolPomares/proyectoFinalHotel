package Clases;

import Excepciones.AccesoDenegadoException;
import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import Enums.TipoRol;
import Interfaces.IValidarContrasenia;
import Excepciones.ListaVaciaException;
import java.util.stream.Collectors;

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


    //Metodo para buscar un usuario por dni
    public Usuario buscarUsuario(int dni) {
        for (T usuario : this.gestorHotel) {
            if (usuario.getDni() == dni) {
                return usuario;
            }
        }

        return null;
    }

    //Metodo para buscar especificamente empleados
    public Usuario buscarEmpleado(String nombreDeUsuario) {
        for (T empleado : this.gestorHotel) {
            if (empleado instanceof Empleados) {
                if (((Empleados) empleado).getNombreUsuario().equals(nombreDeUsuario)) {
                    return empleado;
                }
            }
        }

        return null;
    }

    // Busca por nombre de usuario
    public Usuario buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        for (T usuario : this.gestorHotel) {
            // Comprobar si es un Empleado y si el nombre coincide
            if (usuario instanceof Empleados empleado && empleado.getNombreUsuario().equals(nombreUsuario)) {
                return empleado;
            }
            // Comprobar si es un Pasajero y si el nombre coincide
            if (usuario instanceof Pasajero pasajero && pasajero.getNombreUsuario().equals(nombreUsuario)) {
                return pasajero;
            }
        }
        return null;
    }

    //Metodo para login, validar ingreso y retonar el usuario
    //Lo cambio por: ampliar uso del metodo y como estaba el lleva a error la mayoria de las veces -
    public Usuario gestionarAcceso(String nombreUsuario, String contraseniaAValidar) {
        Usuario usuario = buscarUsuarioPorNombreUsuario(nombreUsuario);
        //Si no hay empleado dretorna null
        if (usuario == null) {
            System.out.println("ERROR! empleado no encontrado, acceso invalido");
            return null;
        }
        //aca verifica que sea un empleado que pueda loguearse
        if (usuario instanceof IValidarContrasenia validador) {
            if (validador.validarContrasenia(contraseniaAValidar)) {
                //Si accede aca seguimos con permisos y rol
                if (usuario instanceof Empleados empleado) {
                    if (empleado.tienePermisoSistema()) {
                        System.out.println("El empleado con nombreUsuario = " + nombreUsuario + " tiene ACCESO EXITOSO");
                        return empleado;
                    } else {
                        System.out.println("El empleado con nombreUsuario = " + nombreUsuario + "NO TIENE ACCESO.");
                        System.out.println("Solicitar acceso en administracion");
                        return empleado;
                    }
                }
                if (usuario instanceof Pasajero pasajero) {
                    System.out.println("El pasajero con nombreUsuario = " + nombreUsuario + " tiene ACCESO EXITOSO");
                    return pasajero;
                }

            } else {
                System.out.println("ERROR! Contraseña incorrecta.");
            }
        } else {
            throw new AccesoDenegadoException("ERROR! El usuario encontrado no tiene credenciales de acceso.");
        }

        return null; // Falla de login
    }


    //Metodo QUE COMIENZAN A IMPLEMENTAR EL LOGIN
    //Metodo para alta y baja de empleados SOLO PUEDE HACERLO UN ADMINISTRADOR
    public void altaEmpleado(Empleados quienEjecuta, Empleados empleadoNuevo) throws datoInvalidoException, AccesoDenegadoException {
        if (quienEjecuta.getTipoRol() == TipoRol.ADMINISTRADOR) {
            if (empleadoNuevo == null) {
                throw new datoInvalidoException("ERROR! el empleado no existe");
            }
            gestorHotel.add((T) empleadoNuevo);
            System.out.println("Empleado" + empleadoNuevo.getNombre() + "agregado exitosamente");
        } else {
            throw new AccesoDenegadoException("ERROR! No tiene permiso sistema");
        }

    }

    //Metodo para dar de baja a un empleado SOLO PUEDE EJECUTAR UN ADMINISTRADOR
    public void bajaEmpleado(Empleados quienEjecuta, int dni) throws datoInvalidoException, AccesoDenegadoException {
        if (quienEjecuta.getTipoRol() == TipoRol.ADMINISTRADOR) {
            String dniComoTexto = String.valueOf(dni);
            if (dniComoTexto.length() != 8) {
                throw new datoInvalidoException("Error!! DNI invalido (debe tener 8 dígitos).");
            }
            Iterator<T> it = this.gestorHotel.iterator();
            boolean encontrado = false;
            while (it.hasNext()) {
                T usuario = it.next();
                if (usuario instanceof Empleados && usuario.getDni() == dni) {
                    it.remove();
                    encontrado = true;
                    System.out.println("Empleado con DNI " + dni + " eliminado.");
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontró un Empleado con DNI " + dni + ".");
            }
        } else {
            throw new AccesoDenegadoException("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de alta a un empleado SOLO PUEDE HACERLO EL RECEPCIONISTA
    public void altaPasajero(Empleados quienEjecuta, Pasajero pasajeroNuevo) throws datoInvalidoException, AccesoDenegadoException {
        if (quienEjecuta.getTipoRol() == TipoRol.RECEPCIONISTA) {
            if (pasajeroNuevo == null) {
                throw new datoInvalidoException("ERROR! el empleado no existe");
            }
            gestorHotel.add((T) pasajeroNuevo);
            System.out.println("Pasajero agregado exitosamente");
        } else {
            throw new AccesoDenegadoException("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de baja un pasajero SOLO EJECUTA RECEPCIONISTA
    public void bajaPasajero(Empleados quienEjecuta, int dni) throws datoInvalidoException, AccesoDenegadoException {
        if (quienEjecuta.getTipoRol() == TipoRol.RECEPCIONISTA) {
            String dniComoTexto = String.valueOf(dni);
            if (dniComoTexto.length() != 8) {
                throw new datoInvalidoException("Error!! DNI invalido (debe tener 8 dígitos).");
            }
            Iterator<T> it = this.gestorHotel.iterator();
            boolean encontrado = false;
            while (it.hasNext()) {
                T usuario = it.next();
                if (usuario instanceof Pasajero && usuario.getDni() == dni) {
                    it.remove();
                    encontrado = true;
                    System.out.println("Pasajero con DNI " + dni + " eliminado/check-out realizado.");
                    break;
                }
            }
        } else {
            throw new AccesoDenegadoException("ERROR NO TIENE ACCESO");
        }
    }

    //Eliminar empleado
    public void eliminarEmpleado(int dni) throws datoInvalidoException {
        if (String.valueOf(dni).length() != 8) {
            throw new datoInvalidoException("Error!! DNI inválido");
        }
        Iterator<T> it = this.gestorHotel.iterator();
        boolean encontrado = false;

        while (it.hasNext()) {
            T usuario = it.next();
            if (usuario instanceof Empleados && usuario.getDni() == dni) {
                it.remove();
                System.out.println("Empleado con DNI " + dni + " eliminado ");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró empleado con DNI: " + dni);
        }

    }

    //Anadir pasajero
    public void agregarPasajero(T pasajero) throws datoInvalidoException {
        if (pasajero == null) {
            throw new datoInvalidoException("Error!! campos vacios");
        }
        this.gestorHotel.add(pasajero);
    }

    //Eliminar pasajero
    public void eliminarPasajero(int dni) throws datoInvalidoException {
        if (String.valueOf(dni).length() != 8) {
            throw new datoInvalidoException("Error!! DNI inválido");
        }

        Iterator<T> it = this.gestorHotel.iterator();
        boolean encontrado = false;

        while (it.hasNext()) {
            T usuario = it.next();
            if (usuario instanceof Pasajero && usuario.getDni() == dni) {
                it.remove();
                System.out.println("Pasajero con DNI " + dni + " eliminado ");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró pasajero con DNI: " + dni);
        }
    }


    //Mostrar usuarios
    public void imprimirTodosUsuarios() {
        Iterator it = this.gestorHotel.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    //Listar Empleados
    public ArrayList<Empleados> ListarEmpleados() {
        ArrayList<Empleados> empleadosLista = new ArrayList<>();
        for (T usuario : this.gestorHotel) {
            if (usuario instanceof Empleados) {
                empleadosLista.add((Empleados) usuario);
            }
        }

        return empleadosLista;
    }

    //Mostrar solo pasajeros
    public void mostrarPasajeros() {
        System.out.println("=== PASAJEROS REGISTRADOS ===");
        boolean hayPasajeros = false;

        for (T usuario : gestorHotel) {
            if (usuario instanceof Pasajero) {
                ((Pasajero) usuario).imprimirDatos();
                hayPasajeros = true;
            }
        }

        if (!hayPasajeros) {
            System.out.println("No hay pasajeros registrados.");
        }
    }

    //Listar Pasajeros
    public ArrayList<Empleados> filtrarEmpleados() {
        ArrayList<Empleados> empleadosLista = new ArrayList<>();
        Iterator<T> it = this.gestorHotel.iterator();
        while (it.hasNext()) {
            T usuario = it.next(); //
            if (usuario instanceof Empleados empleado) {
                empleadosLista.add(empleado);
            }
        }
        return empleadosLista;
    }


    public ArrayList<Usuario> listarPorRol(TipoRol rol) throws datoInvalidoException, ListaVaciaException {
        if (rol == null) {
            throw new datoInvalidoException("ERROR! El rol de búsqueda no puede ser nulo.");
        }

        ArrayList<Usuario> usuariosPorRol = this.gestorHotel.stream()
                .filter(u -> u.getTipoRol() == rol)
                .collect(Collectors.toCollection(ArrayList::new));

        if (usuariosPorRol.isEmpty()) {
            throw new ListaVaciaException("ERROR! No se encontraron usuarios con el rol: " + rol);
        }
        return usuariosPorRol;
    }

    public ArrayList<Empleados> listarPorPermisos(boolean tieneAcceso) throws ListaVaciaException {
        ArrayList<Empleados> empleadosFiltrados = this.gestorHotel.stream()
                .filter(u -> u instanceof Empleados)
                .map(u -> (Empleados) u)
                .filter(e -> e.tienePermisoSistema() == tieneAcceso)
                .collect(Collectors.toCollection(ArrayList::new));

        if (empleadosFiltrados.isEmpty()) {
            String mensaje = tieneAcceso ? " con acceso al sistema." : " sin acceso al sistema.";
            throw new ListaVaciaException("ERROR! No se encontraron empleados");
        }
        return empleadosFiltrados;
    }

    public ArrayList<Pasajero> listarPasajerosPorOrigen(String origen) throws datoInvalidoException, ListaVaciaException {
        if (origen == null || origen.trim().isEmpty()) {
            throw new datoInvalidoException("ERROR! El origen de búsqueda no puede ser vacío.");
        }
/// Aca me ayude con chat
        ArrayList<Pasajero> pasajerosFiltrados = this.gestorHotel.stream()
                .filter(u -> u instanceof Pasajero)
                .map(u -> (Pasajero) u)
                .filter(p -> ((Pasajero) p).getOrigen().equalsIgnoreCase(origen))
                .collect(Collectors.toCollection(ArrayList::new));

        if (pasajerosFiltrados.isEmpty()) {
            throw new ListaVaciaException("ERROR! No se encontraron pasajeros provenientes de: " + origen);
        }
        return pasajerosFiltrados;
    }

    public ArrayList<Pasajero> listarPasajerosSistema() throws ListaVaciaException {
        ArrayList<Pasajero> pasajerosLista = new ArrayList<>();

        for (T usuario : this.gestorHotel) {
            if (usuario instanceof Pasajero) {
                pasajerosLista.add((Pasajero) usuario);
            }
        }

        if (pasajerosLista.isEmpty()) {
            throw new ListaVaciaException("ERROR! No hay pasajeros registrados en el sistema.");
        }
        return pasajerosLista;
    }

    public ArrayList<Pasajero> ordenarPasajerosPorDni() throws ListaVaciaException {
        ArrayList<Pasajero> pasajeros = listarPasajerosSistema();
/// Revisar no estoy segura si es asi o List sort--- casi segura que es asi
        Collections.sort(pasajeros, (p1, p2) -> Integer.compare(p1.getDni(), p2.getDni()));

        return pasajeros;
    }
}


