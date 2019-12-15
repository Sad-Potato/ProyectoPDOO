/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.util.ArrayList;
import civitas.Jugador;
import civitas.Sorpresa_Especulador;
import civitas.Jugador_Especulador;
import civitas.TituloPropiedad;
import civitas.CivitasJuego;

/**
 *
 * @author xpat
 */

public class TestP5{
    public static void main(String args[]){
        CivitasView vista=new CivitasView();
        Dado.createInstance(vista);
        Dado.getInstance().setDebug(true);
        CapturaNombres nombres=new CapturaNombres(vista, true);
        ArrayList<String> names;
        names=nombres.getNombres();
        CivitasJuego juego=new CivitasJuego(names);
        Controlador controller=new Controlador(juego, vista);
        vista.setCivitasJuego(juego);
        vista.actualizarVista();
        
        // VistaTextual vista=new VistaTextual();
        // ArrayList<String> nombres=new ArrayList<>();
        // nombres.add("Sergio");
        // nombres.add("Maki-chan");
        // CivitasJuego juego=new CivitasJuego(nombres);
        // Controlador game=new Controlador(juego,vista);
        // game.juega();
    }
};