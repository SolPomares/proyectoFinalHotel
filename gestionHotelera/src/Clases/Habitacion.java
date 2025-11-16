package Clases;
import Enums.TipoDisponibilidad;
import Enums.TipoHabitacion;
import Enums.TipoNODisponible;

import java.util.Date;
import java.util.UUID;

public class Habitacion {
    //Atributos
    private static int contador = 0;
    private int numeroHabitacion;
    private double valorDiario;
    private TipoHabitacion tipoHabitacion;
    private TipoDisponibilidad disponibilidad;
    private TipoNODisponible tipoNODisponible = null;
    private int capacidad;
    private Date dateIn;
    private Date dateOut;

//contructor

    public Habitacion() {
    }

    public Habitacion(int numeroHabitacion, double valorDiario, TipoHabitacion tipoHabitacion, TipoDisponibilidad disponibilidad, TipoNODisponible tipoNODisponible, int capacidad, Date dateIn, Date dateOut) {
        this.numeroHabitacion = numeroHabitacion;
        this.valorDiario = valorDiario;
        this.tipoHabitacion = tipoHabitacion;
        this.disponibilidad = disponibilidad;
        this.tipoNODisponible = tipoNODisponible;
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

    public Date getDateIn() {
        return dateIn;
    }

    public TipoNODisponible getTipoNODisponible() {
        return tipoNODisponible;
    }

    public void setTipoNODisponible(TipoNODisponible tipoNODisponible) {
        this.tipoNODisponible = tipoNODisponible;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numeroHabitacion=" + numeroHabitacion +
                ", valorDiario=" + valorDiario +
                ", tipoHabitacion=" + tipoHabitacion +
                ", disponibilidad=" + disponibilidad +
                ", capacidad=" + capacidad +
                '}';
    }

    public String mostrarTodosDatosHabitacion() {
        return "Habitacion{" +
                "numeroHabitacion=" + numeroHabitacion +
                ", valorDiario=" + valorDiario +
                ", tipoHabitacion=" + tipoHabitacion +
                ", disponibilidad=" + disponibilidad +
                ", tipoNODisponible=" + tipoNODisponible +
                ", capacidad=" + capacidad +
                ", dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                '}';
    }
}
