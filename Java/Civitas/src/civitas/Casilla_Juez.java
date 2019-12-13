/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;

/**
 *
 * @author Miguel Moles Mestre
 */

public class Casilla_Juez extends Casilla_Descanso {
	private static int carcel;
			
	Casilla_Juez(int numCasillaCarcel, String nombre){
		super(nombre);
		carcel = numCasillaCarcel;		
	}
	
	@Override
	void recibeJugador(int actual,ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual,todos)){
			informe(actual,todos);
			todos.get(actual).encarcelar(carcel);
        }
	}
	
	@Override
	public String toString(){
        return nombre + " (Tipo: Juez)";
    }
}
