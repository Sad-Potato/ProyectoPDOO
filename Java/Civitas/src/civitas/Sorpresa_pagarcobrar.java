package civitas;
import java.util.ArrayList;

public class Sorpresa_pagarcobrar extends Sorpresa2{
    Sorpresa_pagarcobrar(){


    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
			informe(actual, todos);
			todos.get(actual).modificarSaldo(valor);
		}
    }
}