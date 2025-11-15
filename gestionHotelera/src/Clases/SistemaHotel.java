package Clases;

import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.Iterator;
import Enums.TipoRol;

public class SistemaHotel <T extends Usuario> {
    ArrayList<T> gestorHotel;

    //Constructores
    public SistemaHotel() {
    }

    public SistemaHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = new ArrayList<>();
    }

    //getters y Setters
    public ArrayList<T> getGestorHotel() {
        return gestorHotel;
    }

    public void setGestorHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    //Metodos

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

    //Login, validar ingreso y retonar el usuario
    public Empleados gestionarAcceso(String nombreUsuario, String contrasenia) {
        Usuario empleado = buscarEmpleado(nombreUsuario);
        if (empleado == null) {
            System.out.println("ERROR! empleado no encontrado, acceso invalido");
            return null;
        }

        if (empleado instanceof Empleados) {
            if (((Empleados) empleado).validarContrasenia(contrasenia)) {
                if (((Empleados) empleado).tienePermisoSistema()) {
                    System.out.println("Acceso exitoso como: " + empleado.getTipoRol());
                    return (Empleados) empleado;

                } else {
                    System.out.println("ERROR! No tiene permiso sistema");
                }
            }
        }
        return null;
    }

    public void altaEmpleado(Empleados quienEjecuta, Empleados empleadoNuevo) throws datoInvalidoException {
        //Validaciones
        // Permiso del administrador
        if (quienEjecuta.getTipoRol() != TipoRol.ADMINISTRADOR) {
            System.out.println("ACCESO DENEGADO: Solo un ADMINISTRADOR puede dar de alta nuevos empleados.");
            return;
        }

        if (empleadoNuevo == null) {
            throw new datoInvalidoException("ERROR! El objeto Empleados a dar de alta no puede ser nulo.");
        }
        //Reviso que el empleado no este dado de alta para no duplicar
        if (buscarUsuario(empleadoNuevo.getDni()) != null) {
            throw new datoInvalidoException("ERROR! Ya existe un usuario (o empleado) con el DNI: " + empleadoNuevo.getDni());
        }

        this.gestorHotel.add((T)empleadoNuevo);

        System.out.println("Empleado " + empleadoNuevo.getNombre() + " agregado exitosamente al sistema.");
    }
}

    //Metodo para dar de baja a un empleado SOLO PUEDE EJECUTAR UN ADMINISTRADOR
    public void bajaEmpleado(Empleados quienEjecuta, int dni) throws datoInvalidoException {
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
            System.out.println("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de alta a un empleado SOLO PUEDE HACERLO EL RECEPCIONISTA
    public void altaPasajero(Empleados quienEjecuta, Pasajero pasajeroNuevo) throws datoInvalidoException {
        if (quienEjecuta.getTipoRol() == TipoRol.RECEPCIONISTA) {
            if (pasajeroNuevo == null) {
                throw new datoInvalidoException("ERROR! el empleado no existe");
            }
            gestorHotel.add((T) pasajeroNuevo);
            System.out.println("Pasajero agregado exitosamente");
        } else {
            System.out.println("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de baja un pasajero SOLO EJECUTA RECEPCIONISTA
    public void bajaPasajero(Empleados quienEjecuta, int dni) throws datoInvalidoException {
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
=======
                this.gestorHotel.add(empleado);
            }

            //Eliminar empleado
            public void eliminarEmpleado ( int dni) throws datoInvalidoException {
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
            public void agregarPasajero (T pasajero) throws datoInvalidoException {
                if (pasajero == null) {
                    throw new datoInvalidoException("Error!! campos vacios");
                }
                this.gestorHotel.add(pasajero);
            }

            //Eliminar pasajero
            public void eliminarPasajero ( int dni) throws datoInvalidoException {
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
            public void imprimirTodosUsuarios () {
                Iterator it = this.gestorHotel.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            }

            //Mostrar solo empleados
            public void mostrarEmpleados () {
                System.out.println("=== EMPLEADOS REGISTRADOS ===");
                boolean hayEmpleados = false;

                for (T usuario : gestorHotel) {
                    if (usuario instanceof Empleados) {
                        ((Empleados) usuario).imprimirDatos();
                        hayEmpleados = true;
                    }
                }

                if (!hayEmpleados) {
                    System.out.println("No hay empleados registrados.");
                }
            }

            //Listar Empleados
            public ArrayList<Empleados> ListarEmpleados () {
                ArrayList<Empleados> empleadosLista = new ArrayList<>();
                for (T usuario : this.gestorHotel) {
                    if (usuario instanceof Empleados) {
                        empleadosLista.add((Empleados) usuario);
                    }
                }

                return empleadosLista;
            }

            //Mostrar solo pasajeros
            public void mostrarPasajeros () {
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
            public ArrayList<Empleados> filtrarEmpleados () {
                ArrayList<Empleados> empleadosLista = new ArrayList<>();
                Iterator it = this.gestorHotel.iterator();
                while (it.hasNext()) {
                    if (it.next() instanceof Empleados) {
                        empleadosLista.add((Empleados) it.next());
                    }
                    if (!encontrado) {
                        System.out.println("No se encontró un Pasajero con DNI " + dni + ".");
                    }
                } else{
                    System.out.println("ACCESO DENEGADO: Solo un ADMINISTRADOR o RECEPCIONISTA puede realizar el check-out/baja de pasajeros.");
                }
            }
        }
    }
}