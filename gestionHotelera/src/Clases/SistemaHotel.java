
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




    /*
    //Administrador
    //Anadir empleado
    public void agregarEmpleado(T empleado) throws datoInvalidoException{
        if(empleado == null){
            throw new datoInvalidoException("Error!! campos vacios");
        }
        this.gestorHotel.add(empleado);
    }

    //Eliminar empleado
    public void eliminarEmpleado (int dni) throws datoInvalidoException{
        String dniComoTexto = String.valueOf(dni);
        int longitud = dniComoTexto.length();
        if(longitud != 8)
        {
            throw new datoInvalidoException("Error!! DNI invalido");
        }
        Iterator it = this.gestorHotel.iterator();
        while(it.hasNext()){
            int DNI = (int)it.next();
            if(DNI == dni){
                it.remove();
            }
            System.out.println("Empleado con DNI " +DNI+ " eliminado ");
        }

    }



    //Anadir pasajero
    public void agregarPasajero (T pasajero) throws datoInvalidoException{
        if(pasajero == null){
            throw new datoInvalidoException("Error!! campos vacios");
        }
        this.gestorHotel.add(pasajero);
    }

    //Eliminar pasajero
    public void eliminarPasajero (int dni) throws datoInvalidoException {
        String dniComoTexto = String.valueOf(dni);
        int longitud = dniComoTexto.length();
        if (longitud != 8) {
            throw new datoInvalidoException("Error!! DNI invalido");
        }
        Iterator it = this.gestorHotel.iterator();
        while (it.hasNext()) {
            int DNI = (int) it.next();
            if (DNI == dni) {
                it.remove();
            }
            ///Hacer verificador para asegurar que sea pasajero
            System.out.println("Pasajero con DNI " + DNI + " eliminado ");
        }
    }

    //Mostrar usuarios
        public void imprimirTodosUsuarios(){
            Iterator it = this.gestorHotel.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
        }

    //Mostrar solo empleados
    public void mostrarEmpleados(){
        Iterator it = this.gestorHotel.iterator();
        while(it.hasNext()){
            if(it.next() instanceof Empleados){
                Empleados empleados = (Empleados)it.next();
                empleados.imprimirDatos();
            }
        }
    }

    //Listar Empleados
    public ArrayList<Empleados> ListarEmpleados(){
        ArrayList<Empleados> empleadosLista = new ArrayList<>();
        Iterator it = this.gestorHotel.iterator();
        while(it.hasNext()){
            if(it.next() instanceof Empleados){
                empleadosLista.add((Empleados)it.next());
            }
        }

        return empleadosLista;
    }

    //Mostrar solo pasajeros
    public void mostrarPasajeros(){
        Iterator it = this.gestorHotel.iterator();
        while(it.hasNext()){
            if(it.next() instanceof Pasajero){
                Pasajero pasajero = (Pasajero) it.next();
                pasajero.imprimirDatos();
            }
        }
    }

    //Listar Pasajeros
    public ArrayList<Empleados> filtrarEmpleados(){
        ArrayList<Empleados> empleadosLista = new ArrayList<>();
        Iterator it = this.gestorHotel.iterator();
        while(it.hasNext()){
            if(it.next() instanceof Empleados){
                empleadosLista.add((Empleados)it.next());
            }
        }

        return empleadosLista;
    }*/















/*private List<Empleados> listaEmpleados;

    //CONSTRUCTOR
    public SistemaHotel(List<Empleados> listaEmpleados) {
        this.listaEmpleados = new ArrayList<>();
    }

    //getters y setters
    public List<Empleados> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    // Metodos


    public boolean gestionarPermisoAcceso(Empleados empleado) {
        if (!empleado.tienePermisoSistema()) {
            System.out.println("ACCESO DENEGADO: La cuenta de " + empleado.getNombreUsuario() +
                    " no está activa o no tiene permisos de acceso al sistema.");
            return false;
        } else {  // Si tiene permiso, valido la contraseña
            System.out.println("--------------------- AUTENTICACIÓN ----------------------");
            Scanner teclado = new Scanner(System.in);

            System.out.print("Usuario: " + empleado.getNombreUsuario() + "\n");
            System.out.print("Ingrese su contraseña: ");
            String contraseniaIngresada = teclado.nextLine();

            if (empleado.getContrasenia().equals(contraseniaIngresada)) {
                System.out.println("\nACCESO EXITOSO. Bienvenido(a), " + empleado.getNombreUsuario() + ".");
                return true;
            } else {
                System.out.println("\nACCESO DENEGADO: Contraseña incorrecta.");
                return false;
            }
        }
    }

   /* public void gestionarPermisoAcceso(Empleados empleado, boolean tieneAcceso) {
        // 1. Ejecuta setter de acceso de Empleados
        empleado.setAcceso(tieneAcceso);
        System.out.println(" El ACCESO A CAMBIADO");
        /// OJO A GUARDAR LO DE JSON PERSISTENCIA DE DATOS - BASE DE DATOS EMPLEADOS
    }

    public static void crearArchivo(String nombreArchivo)throws IOException {
        try{
            File archivo = new File(nombreArchivo); //tb puede ir la ruta del archivo
            PrintWriter escritura = new PrintWriter(archivo);//escribe el archivo
            escritura.close();
            System.out.println("El archivo: "+ nombreArchivo+" se creo correctamente");
        } catch (FileNotFoundException ex) {
            System.out.println("No se puede Escribir el archivo");
            throw new IOException(ex);
        }
    }*/


}

