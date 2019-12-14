package civitas;
import java.util.ArrayList;

public class Sorpresa_ircarcel extends Sorpresa2{
    Sorpresa_ircarcel(){


    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
}