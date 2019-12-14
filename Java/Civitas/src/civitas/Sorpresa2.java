package civitas;
import java.util.ArrayList;

public abstract class Sorpresa2{
    private int valor;
    private TipoSorpresa tipo;
    private String texto;
    private MazoSorpresas mazo;
    private Tablero tablero;

    protected void init(){
        valor = -1;
        mazo = null;
        tablero = null;
		texto = null;
    }

    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return actual >= 0 && actual < todos.size();
    }

    protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Sorpresa -- " + this.toString() +
                 " -- Se aplica sorpresa al jugador " + todos.get(actual).getNombre());
    }

    void salirDelMazo(){
		if(tipo == TipoSorpresa.SALIRCARCEL){
			mazo.inhabilitarCartaEspecial(this);
		}
    }
    
    void usada(){
		if(tipo == TipoSorpresa.SALIRCARCEL){
			mazo.habilitarCartaEspecial(this);
		}
    }
    
    public String toString(){
		return texto;
	}

}