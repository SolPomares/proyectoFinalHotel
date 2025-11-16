package Clases;
import Enums.TipoDisponibilidad;
import Enums.TipoHabitacion;

import java.util.Date;
import java.util.UUID;

public class Habitacion {
    //Atributos
    private static int contador = 0;
    private int numeroHabitacion;
    private double valorDiario;
    private TipoHabitacion tipoHabitacion;
    private TipoDisponibilidad disponibilidad;
    private int capacidad;
    private String dateIn;
    private String dateOut;

    public Habitacion(int numero, double valorDiario, TipoHabitacion tipoHabitacion, TipoDisponibilidad disponibilidad, int capacidad, String dateIn, String dateOut) {
        this.numeroHabitacion = contador++;
        this.valorDiario = valorDiario;
        this.tipoHabitacion = tipoHabitacion;
        this.disponibilidad = TipoDisponibilidad.DISPONIBLE;
        this.capacidad = capacidad;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    //Gets y sets
    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Habitacion.contador = contador;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }

    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public TipoDisponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(TipoDisponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }


}
