package Clases;

import Enums.TipoRol;

public class Administrador extends Empleados {
private final TipoRol rol = TipoRol.ADMINISTRADOR;
private String claveAdministracion;

//constructor

    public Administrador(int idUsuario, String nombre, String apellido, int dni, TipoRol tipoRol, String nombreUsuario, String email, String claveAdministracion) {
        super(idUsuario, nombre, apellido, dni, tipoRol, nombreUsuario, email);
        this.claveAdministracion = claveAdministracion;
    }

    public Administrador(String nombre, String apellido, TipoRol tipoRol, String nombreUsuario, String email, String claveAdministracion) {
        super(nombre, apellido, tipoRol, nombreUsuario, email);
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

    //metodos

  public void realizarBackUp(){
      System.out.println("-----------   Back Up -----------");
      System.out.println("---------------------------------");
      System.out.println(" BAK UP REALIZADO CON EXITO");
  }

  public boolean asignarPermisos(SistemaHotel sistemaHotel, Empleados e, boolean tieneAcceso) {
      //Esto se me complico porque el admin otorga el acceso y lo delega a al sistemade gestion)
      boolean gestionExitosa = sistemaHotel.gestionarPermisoAcceso(empleado, tieneAcceso);
      if (tieneAcceso) estado = "OTORGADO : REVOCADO";
      System.out.println("estado = " + estado);
      return gestionExitosa;
  }

  public boolean quitarPermisos(SistemaHotel sistemaHotel, Empleados e, boolean tieneAcceso){
        
  }


}
