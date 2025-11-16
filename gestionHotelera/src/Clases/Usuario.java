package Clases;
import Excepciones.*;
import Enums.TipoRol;
//import org.json.JSONObject;
//import org.json.JSONException;

public abstract class Usuario {
    //Atributos
    private static int contadorUsuario = 1; //prueba git bash
    private int idUsuario;
    private String nombre;
    private String apellido;
    private int dni;
    private TipoRol tipoRol;


    //constructor
    public Usuario(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol) {
        this.idUsuario = contadorUsuario++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.tipoRol = tipoRol;
    }

    //Constructor infimo para JSON Deserializacion
    public Usuario() {
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

    public TipoRol getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(TipoRol tipoRol) {
        this.tipoRol = tipoRol;
    }

    //metodos
    public abstract void imprimirDatos();
}

