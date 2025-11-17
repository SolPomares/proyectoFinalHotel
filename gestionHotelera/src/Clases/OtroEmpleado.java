package Clases;

import Enums.TipoRol;
import Enums.Turno;
import java.util.List;
import java.util.UUID;;

public class OtroEmpleado extends Empleados{
    private Turno turno;
    private List<String> tareas;

    public OtroEmpleado(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public List<String> getTareas() {
        return tareas;
    }

    public void setTareas(List<String> tareas) {
        this.tareas = tareas;
    }
}
