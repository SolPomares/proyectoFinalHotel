
//import Clases.Empleados;
import Excepciones.datoInvalidoException;
import java.util.ArrayList;
import java.util.Iterator;
import Enums.TipoRol;

public class SistemaHotel <T extends Usuario>{
    ArrayList<T> gestorHotel;

    public SistemaHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = new ArrayList<>();
    }

    public ArrayList<T> getGestorHotel() {
        return gestorHotel;
    }

    public void setGestorHotel(ArrayList<T> gestorHotel) {
        this.gestorHotel = gestorHotel;
    }

    //Metodo para buscar un usuario por dni
    public Usuario buscarUsuario (int dni){
        for(T usuario : this.gestorHotel){
            if(usuario.getDni() == dni ){
                return usuario;
            }
        }

        return null;
    }

    //Metodo para buscar especificamente empleados
    public Usuario buscarEmpleado(String nombreDeUsuario){
        for(T empleado : this.gestorHotel){
            if(empleado instanceof Empleados){
                if(((Empleados) empleado).getNombreUsuario().equals(nombreDeUsuario)){
                    return empleado;
                }
            }
        }

        return null;
    }

    //Metodo para login, validar ingreso y retonar el usuario
    public Empleados gestionarAcceso(String nombreUsuario, String contrasenia){
        Usuario empleado = buscarEmpleado(nombreUsuario);
        if(empleado == null){
            System.out.println("ERROR! empleado no encontrado, acceso invalido");
        }
        if(empleado instanceof Empleados){
            if(((Empleados) empleado).validarContrasenia(contrasenia)){
                if(((Empleados) empleado).tienePermisoSistema()){
                    System.out.println("Acceso exitoso como: " + empleado.getTipoRol());
                }
                else{
                    System.out.println("ERROR! No tiene permiso sistema");
                }
            }
        }

        return null;
    }

    //Metodo QUE COMIENZAN A IMPLEMENTAR EL LOGIN
    //Metodo para alta y baja de empleados SOLO PUEDE HACERLO UN ADMINISTRADOR
    public void altaEmpleado(Empleados quienEjecuta, Empleados empleadoNuevo) throws datoInvalidoException{
        if(quienEjecuta.getTipoRol() == TipoRol.ADMINISTRADOR){
            if(empleadoNuevo == null){
                throw new datoInvalidoException("ERROR! el empleado no existe");
            }
            gestorHotel.add((T)empleadoNuevo);
            System.out.println("Empleado" + empleadoNuevo.getNombre()+ "agregado exitosamente");
        }
        else{
            System.out.println("ERROR! No tiene permiso sistema");
        }

    }

    //Metodo para dar de baja a un empleado SOLO PUEDE EJECUTAR UN ADMINISTRADOR
    public void bajaEmpleado (Empleados quienEjecuta, int dni) throws datoInvalidoException{
        if(quienEjecuta.getTipoRol() == TipoRol.ADMINISTRADOR){
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
        }
        else{
            System.out.println("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de alta a un empleado SOLO PUEDE HACERLO EL RECEPCIONISTA
    public void altaPasajero (Empleados quienEjecuta, Pasajero pasajeroNuevo) throws datoInvalidoException{
        if(quienEjecuta.getTipoRol() == TipoRol.RECEPCIONISTA){
            if(pasajeroNuevo == null){
                throw new datoInvalidoException("ERROR! el empleado no existe");
            }
            gestorHotel.add((T)pasajeroNuevo);
            System.out.println("Pasajero agregado exitosamente");
        }
        else{
            System.out.println("ERROR! No tiene permiso sistema");
        }
    }

    //Metodo para dar de baja un pasajero SOLO EJECUTA RECEPCIONISTA
    public void bajaPasajero (Empleados quienEjecuta, int dni) throws datoInvalidoException{
        if(quienEjecuta.getTipoRol() == TipoRol.RECEPCIONISTA){
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
            if (!encontrado) {
                System.out.println("No se encontró un Pasajero con DNI " + dni + ".");
            }
        } else {
            System.out.println("ACCESO DENEGADO: Solo un ADMINISTRADOR o RECEPCIONISTA puede realizar el check-out/baja de pasajeros.");
        }
    }

}

















