public class Habitacion {
    //Atributos
    private int numero;
    private double precioNoche;
    private TipoHabitacion tipo;
    private boolean disponible;

    //Constructor
    public Habitacion(int numero, double precioNoche, TipoHabitacion tipo, boolean disponible) {
        this.numero = numero;
        this.precioNoche = precioNoche;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    //Gets y sets
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }



}
