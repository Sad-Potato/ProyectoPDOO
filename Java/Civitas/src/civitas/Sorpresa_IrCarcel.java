
package civitas;

import java.util.ArrayList;

public class Sorpresa_IrCarcel extends Sorpresa{
	private Tablero tablero;
	
    Sorpresa_IrCarcel(Tablero tablero, String texto){
		super(texto);
		this.tablero = tablero;
    }

	@Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
}