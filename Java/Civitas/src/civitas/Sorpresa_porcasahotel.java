package civitas;
import java.util.ArrayList;

public class Sorpresa_porcasahotel extends Sorpresa2{
    Sorpresa_porcasahotel(){


    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
                        informe(actual, todos);
			todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
		}
    }
}