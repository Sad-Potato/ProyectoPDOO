package juegoTexto;

import civitas.OperacionesJuego;
import civitas.CivitasJuego;
import java.util.ArrayList;

public class test{
    public static void main(String args[]){
        VistaTextual vista=new VistaTextual();
        ArrayList<String> nombres=new ArrayList<String>();
        nombres.add("Sergio");
        nombres.add("Maki-chan");
        nombres.add("Don Juanjo");
        CivitasJuego juego=new CivitasJuego(nombres);
        Controlador game=new Controlador(juego,vista);
        game.juega();
    }
};