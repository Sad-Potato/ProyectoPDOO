package civitas;
import java.util.ArrayList;

public class Sorpresa_salircarcel extends Sorpresa2{
    Sorpresa_salircarcel(){


    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
			boolean b = false;
			for(int i = 0; i < todos.size() && !b; i++){
				b = todos.get(i).tieneSalvoconducto();
			}
			if(!b){
				todos.get(actual).obtenerSalvoconducto(this);
				salirDelMazo();
			}
		}
    }
}