package Clases;

import java.util.Scanner;

public class GestorMenu {
    private SistemaHotel<Usuario> gestorUsuarios;
    private SistemaHabitaciones gestorHabitaciones;
    private Scanner teclado;
    private Usuario usuarioActual;

    //constructor

    public GestorMenu(SistemaHotel<Usuario> gestorUsuarios, SistemaHabitaciones gestorHabitaciones, Scanner teclado, Usuario usuarioActual) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorHabitaciones = gestorHabitaciones;
        this.teclado = teclado;
        this.usuarioActual = usuarioActual;
    }
}
