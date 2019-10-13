package civitas;
import java.util.ArrayList;
import java.util.Random;


public class TestP1 {
    public static void main(String args[]){
		ArrayList<String> nombres = new ArrayList<>();
		nombres.add("Miguel");
		nombres.add("Sergio");
		nombres.add("Urbano");
		nombres.add("Gaspar");
        CivitasJuego juego = new CivitasJuego(nombres);
		
    }
}
