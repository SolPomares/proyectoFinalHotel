package Clases;

public abstract class Usuarios {
    // datos generales
    private static int contador = 0;
    private int idUsuario;
    private String nombre;
    private String apellido;
    private int dni;

    //constructor
    public Usuarios(int idUsuario, String nombre, String apellido, int dni) {
        this.idUsuario = contador ++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    //constructor minimo


    public Usuarios(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    //Getters y setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    //metodos
    public abstract String imprimirDatos();
}
