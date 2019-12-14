
package civitas;
import java.util.ArrayList;

public abstract class Sorpresa{
	protected String texto;
    
	protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Sorpresa -- " + this.toString() +
                 " -- Se aplica sorpresa al jugador " + todos.get(actual).getNombre());
    }
	
	Sorpresa(String texto){
		this.texto = texto;
	}

    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return actual >= 0 && actual < todos.size();
    }
      
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
	
    
	public String toString(){
		return texto;
	}
}
