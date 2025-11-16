package Clases;
import java.util.List;
import java.util.UUID;

import Enums.TipoRol;

public class Pasajero extends Usuario {
    //Atributos propios
    private String Origen;
    private String DomicilioOrigen;
    private List<String> Historial;

    //Constructor


    public Pasajero(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String origen, String domicilioOrigen, List<String> historial) {
        super(idUsuario, nombre, apellido, dni, tipoRol);
        Origen = origen;
        DomicilioOrigen = domicilioOrigen;
        Historial = historial;
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

    public void imprimirDatos(){
        System.out.println("getNombre() = " + getNombre() + " Apellido " + getApellido());
         ///poner que mas queremos se vea del pasajero

    }

}
