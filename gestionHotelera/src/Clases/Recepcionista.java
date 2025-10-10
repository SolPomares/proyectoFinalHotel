package Clases;

import Enums.TipoRol;

public class Recepcionista extends Empleados{
    private boolean accseso;

    public Recepcionista(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean accseso) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email);
        this.accseso = accseso;
    }

    public Recepcionista(String nombre, String apellido, TipoRol tipoRol, String nombreUsuario, String email, boolean accseso) {
        super(nombre, apellido, tipoRol, nombreUsuario, email);
        this.accseso = accseso;
    }

    public boolean isAccseso() {
        return accseso;
    }

    public void setAccseso(boolean accseso) {
        this.accseso = accseso;
    }
}
