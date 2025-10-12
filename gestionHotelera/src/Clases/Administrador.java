

import Enums.TipoRol;

public class Administrador extends Empleados {
    private final TipoRol rol = TipoRol.ADMINISTRADOR;

    private String claveAdministracion;

   //constructor
    public Administrador(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, String claveAdministracion) {
        super(idUsuario, nombre, apellido, dni, TipoRol.ADMINISTRADOR, nombreUsuario, email, true);
        this.claveAdministracion = claveAdministracion;
    }

    public Administrador(String nombre, String apellido, TipoRol tipoRol, String nombreUsuario, String email, boolean acceso, String claveAdministracion) {
        super(nombre, apellido, TipoRol.ADMINISTRADOR, nombreUsuario, email, true);
        this.claveAdministracion = claveAdministracion;
    }

//constructor

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

    //metodos

    public void realizarBackUp() {
        System.out.println("-----------   Back Up -----------");
        System.out.println("---------------------------------");
        System.out.println(" BAK UP REALIZADO CON EXITO");
    }

    // CLASE: Administrador (Corrección de Métodos)
    public boolean asignarPermisos(SistemaHotel sistemaHotel, Empleados empleado) {
        if (empleado.tienePermisoSistema()) {
            System.out.println("[ADMIN] Usuario " + empleado.getNombreUsuario() + " YA POSEE ACCESO. No hay cambios.");
            return true;
        }
        // Delegamos la acción de seteo a true (otorgar)
        boolean gestionExitosa = sistemaHotel.gestionarPermisoAcceso(empleado);

        if (gestionExitosa) {
            System.out.println("[ADMIN] Permiso OTORGADO exitosamente a " + empleado.getNombreUsuario() + ".");
        } else {
            System.err.println("[ADMIN] ERROR: Falló la gestión de permisos.");
        }
        return gestionExitosa;
    }

    public boolean quitarPermisos(SistemaHotel sistemaHotel, Empleados empleado) {
        if (!empleado.tienePermisoSistema()) {
            System.out.println("[ADMIN] Usuario " + empleado.getNombreUsuario() + " YA TIENE EL ACCESO REVOCADO.");
            return true;
        }
        // Delegamos la acción y revocar a false (revocar)
        boolean gestionExitosa = sistemaHotel.gestionarPermisoAcceso(empleado);

        if (gestionExitosa) {
            System.out.println("[ADMIN] Permiso REVOCADO exitosamente a " + empleado.getNombreUsuario() + ".");
        } else {
            System.out.println("[ADMIN] ERROR: Falló la gestión de permisos.");
        }
        return gestionExitosa;
    }
}

