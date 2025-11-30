package Clases;
import Enums.TipoRol;
import Interfaces.IValidarContrasenia;
import java.util.UUID;

public class Administrador extends Empleados implements IValidarContrasenia {
    private final TipoRol rol = TipoRol.ADMINISTRADOR;
    private String claveAdministracion;

   //constructor

    public Administrador(UUID idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, String claveAdministracion , boolean acceso) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email, acceso);
        this.claveAdministracion = claveAdministracion;
    }

    //Getters y Setters
    public TipoRol getRol() {
        return rol;
    }

    public String getClaveAdministracion() {
        return claveAdministracion;
    }

    public void setClaveAdministracion(String claveAdministracion) {
        this.claveAdministracion = claveAdministracion;
    }

    @Override
    public boolean validarContrasenia(String contraseniaAValidar) {
        return this.claveAdministracion.equals(contraseniaAValidar);
    }

}

