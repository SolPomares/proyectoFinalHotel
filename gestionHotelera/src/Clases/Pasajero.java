package Clases;
import java.util.List;
import java.util.UUID;

import Enums.TipoRol;
import Interfaces.IValidarContrasenia;

public class Pasajero extends Usuario implements IValidarContrasenia {
    //Atributos propios
    private String Origen;
    private String DomicilioOrigen;
    private List<String> Historial;
    private String nombreUsuario;
    private String clavePasajero;

    //Constructor

    public Pasajero(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String origen, String domicilioOrigen, List<String> historial, String nombreUsuarioPasajero, String clavePasajero) {
        super(idUsuario, nombre, apellido, dni, tipoRol);
        Origen = origen;
        DomicilioOrigen = domicilioOrigen;
        Historial = historial;
        this.nombreUsuario = nombreUsuarioPasajero;
        this.clavePasajero = clavePasajero;
    }

    public Pasajero(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String origen) {
        super(idUsuario, nombre, apellido, dni, tipoRol);
        Origen = origen;
    }

    public Pasajero() {
    }

    //Gets y sets
    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getDomicilioOrigen() {
        return DomicilioOrigen;
    }

    public void setDomicilioOrigen(String domicilioOrigen) {
        DomicilioOrigen = domicilioOrigen;
    }

    public List<String> getHistorial() {
        return Historial;
    }

    public void setHistorial(List<String> historial) {
        Historial = historial;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuarioPasajero) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClavePasajero() {
        return clavePasajero;
    }

    public void setClavePasajero(String clavePasajero) {
        this.clavePasajero = clavePasajero;
    }

    public void imprimirDatos(){
        System.out.println("getNombre() = " + getNombre() + " Apellido " + getApellido());
        System.out.println("DNI : " + getDni()+ "Origen = " + Origen);
        System.out.println("Historial = " + Historial);

    }

    @Override
    public boolean validarContrasenia(String contraseniaAValidar) {
        return this.clavePasajero.equals(contraseniaAValidar);
    }
}
