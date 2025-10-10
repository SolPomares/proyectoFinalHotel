package Clases;

import java.util.Scanner;

public abstract class Empleados extends Usuarios{
    private String nombreUsuario;
    private String email;
    private String contrasenia;

    //constructores
    public Empleados(int idUsuario, String nombre, String apellido, int dni, String nombreUsuario, String email, String contrasenia) {
        super(idUsuario, nombre, apellido, dni);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public Empleados(String nombre, String apellido, String nombreUsuario, String email, String contrasenia) {
        super(nombre, apellido);
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
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

    public void imprimirDatos(){
        System.out.println("--------------------------------------------------");
        System.out.println("----------       DATOS GENERALES     -------------");
       System.out.println("ID: "+ super.getIdUsuario());
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


}
