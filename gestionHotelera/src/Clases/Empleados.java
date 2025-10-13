
import Enums.TipoRol;

public abstract class Empleados extends Clases.Usuario {
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private boolean acceso;

    //constructores

    public Empleados(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso) {
        super(idUsuario, nombre, apellido, dni, tipoRol);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.acceso = acceso;
    }

    public Empleados(String nombre, String apellido, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso) {
        super(nombre, apellido, tipoRol);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.acceso = acceso;
    }


    //getters y Setters


    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isAcceso() {
        return acceso;
    }

    public void setAcceso(boolean acceso) {
        this.acceso = acceso;
    }

    //Metodos
    public void imprimirDatos(){
        System.out.println("--------------------------------------------------");
        System.out.println("----------       DATOS GENERALES     -------------");
       System.out.println("ID: "+ super.getIdUsuario()+ " Rol: "+ getTipoRol());
        System.out.println(" Nombre y Apellido = "+ super.getNombre()+ super.getApellido() + " // DNI= "+ super.getDni());
        System.out.println("nombreUsuario = " + nombreUsuario);
        System.out.println("email = " + email);
        System.out.println("--------------------------------------------------");
        System.out.println("--------------------------------------------------");
    }

    public void imprimirUsuario(){
        System.out.println("--------------------------------------------------");
        System.out.println("----------          USUARIO          -------------");
        System.out.println("nombreUsuario = " + nombreUsuario);
        System.out.println("email = " + email);
        System.out.println("--------------------------------------------------");
        System.out.println("--------------------------------------------------");
    }

    public boolean validarContrasenia(String contrasenia){
        if(this.getContrasenia().equals(contrasenia)) {
            System.out.println("Acceso Permitido");
            return true;
        }else{
            System.out.println("Error En contraseña ingresada");
            return false;
        }
    }

    public boolean tienePermisoSistema(){
        if(this.acceso){
            return true;
        }else{
            System.out.println("Solicitar permiso al administrador");
            return false;
        }

    }


}
