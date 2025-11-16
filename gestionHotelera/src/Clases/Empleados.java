package Clases;
import Enums.TipoRol;
import java.util.UUID;


public abstract class Empleados extends Usuario {
    private String nombreUsuario;
    private String email;
    private boolean acceso;

    //constructores
    public Empleados(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso) {
        super(idUsuario, nombre, apellido, dni, tipoRol);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
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

    //metodo para validar
    public boolean tienePermisoSistema(){
        if(this.acceso){
            return true;
        }else{
            System.out.println("Solicitar permiso al administrador");
            return false;
        }
    }

}
