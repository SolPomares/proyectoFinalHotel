
//import Clases.Empleados;
import java.util.ArrayList;

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

    //Administrador
    //Anadir empleado

    public void agregarEmpleado(T empleado){
        if(empleado == null){

        }

        this.gestorHotel.add(empleado);
    }













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

