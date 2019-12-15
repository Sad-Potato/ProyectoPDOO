package civitas;
import java.util.ArrayList;

public class Sorpresa_Especulador extends Sorpresa{
    private int valor;
	
	Sorpresa_Especulador(int tvalor, String ttexto){
        super(ttexto);
        this.valor = tvalor;
    }
    
    @Override
	public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            Jugador tjugador=todos.get(actual);
            tjugador=((Jugador_Especulador)new Jugador_Especulador(todos.get(actual),valor));
        }
    }
}
