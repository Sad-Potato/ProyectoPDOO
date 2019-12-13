/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

/**
 *
 * @author phoenix
 */
public class Casilla_Sorpresa extends Casilla_Descanso {
	private MazoSorpresas mazo;
	
	Casilla_Sorpresa(MazoSorpresas mazo,String nombre){
		super(nombre);
		this.mazo = mazo;
	}
	
	@Override
	void recibeJugador(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual,todos)){
			informe(actual, todos);
			mazo.siguiente().aplicarAJugador(actual, todos);
		}
	}
	
	@Override
	public String toString(){
        return nombre + " (Tipo: Sorpresa)";
    }

}
