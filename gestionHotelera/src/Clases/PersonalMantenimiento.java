package Clases;

import Enums.TipoRol;
import Enums.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;;

public class PersonalMantenimiento extends Empleados{
    private Turno turno;

    public PersonalMantenimiento(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, Turno turno) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
        this.turno = turno;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

}
