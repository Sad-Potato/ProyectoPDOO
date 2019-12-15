package civitas;

import java.util.ArrayList;

public class test4{
    public static void main(String args[]){
        Jugador jugador=new Jugador("Sergio");
        jugador.propiedades.add(new TituloPropiedad("Mi puta casa", 100, 100, 100, 100, 100));
        Jugador_Especulador evolucion=new Jugador_Especulador(jugador,100);
        System.out.println(evolucion.toStringPropiedades() + evolucion.toString() + "\n");

        // VistaTextual vista=new VistaTextual();
        // ArrayList<String> nombres=new ArrayList<>();
        // nombres.add("Sergio");
        // nombres.add("Maki-chan");
        // CivitasJuego juego=new CivitasJuego(nombres);
        // Controlador game=new Controlador(juego,vista);
        // game.juega();
    }
};