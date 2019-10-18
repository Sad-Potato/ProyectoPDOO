package civitas;
import java.util.ArrayList;
import java.util.Random;


public class TestP1 {
    public static void main(String args[]){
		ArrayList<String> nombres = new ArrayList<>();
		nombres.add("Miguel");
		nombres.add("Sergio");
		nombres.add("Juan");
		nombres.add("Pepe");
        CivitasJuego juego = new CivitasJuego(nombres);
		
		ArrayList<Jugador> testSubjects = new ArrayList<>();
		testSubjects.add(new Jugador("Dummy 1"));
		testSubjects.add(new Jugador("Dummy 2"));
		testSubjects.add(new Jugador("Dummy 3"));
		testSubjects.add(new Jugador("Dummy 4"));
		System.out.println(testSubjects.get(0).toString());
    }
}
