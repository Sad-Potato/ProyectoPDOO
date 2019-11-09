
package civitas;
import java.util.ArrayList;

public class Sorpresa {
    private int valor;
    private TipoSorpresa tipo;
    private String texto;
    private MazoSorpresas mazo;
    private Tablero tablero;
    
    private void init(){
        valor = -1;
        mazo = null;
        tablero = null;
		texto = null;
    }
    
    Sorpresa(TipoSorpresa ttipo, int tvalor, String ttexto){
        init();
        tipo = ttipo;
        valor = tvalor;
        texto = ttexto;
    }
    Sorpresa(TipoSorpresa ttipo, Tablero ttablero){
        init();
        tipo = ttipo;
        tablero = ttablero;
    }
    Sorpresa(TipoSorpresa ttipo, Tablero ttablero, int tvalor, String ttexto){
        init();
        tipo = ttipo;
        tablero=ttablero;
        valor = tvalor;
        texto = ttexto;
    }
    Sorpresa(TipoSorpresa ttipo, MazoSorpresas tmazo){
        init();
        tipo = ttipo;
        mazo = tmazo;
    }
    
    public boolean jugadorCorrecto (int actual, ArrayList<Jugador> todos){
        return actual >= 0 && actual < todos.size();
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Sorpresa -- "
                + "Se aplica sorpresa al jugador " + todos.get(actual).getNombre());
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        switch(tipo){
            case IRCARCEL:
                aplicarAJugador_irCarcel(actual, todos); break;
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos); break;
            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos); break;
            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos); break;
            case PORCASAHOTEL:
               aplicarAJugador_porCasaHotel(actual, todos); break;
            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos); break;
        }
    }
    
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPosicion = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPosicion);
            tablero.getCasilla(nuevaPosicion).recibeJugador(actual, todos);
        }
    }
	
	private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
			informe(actual, todos);
			todos.get(actual).modificarSaldo(valor);
		}
	}
	
	private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
                        informe(actual, todos);
			todos.get(actual).modificarSaldo(valor*todos.get(actual).cantidadCasasHoteles());
		}
	}
	
	private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
                informe(actual, todos);
			for(int i = 0; i < todos.size(); i++){
				Sorpresa a=new Sorpresa(
						TipoSorpresa.PAGARCOBRAR,
						tablero,
						valor * (i==actual ? todos.size()-1 : -1 ),
						"AplicarAJugador_porJugador");
                                a.aplicarAJugador(i, todos);
			}
		}
	}
	
	private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos){
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
