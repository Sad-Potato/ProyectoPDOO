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
public class Sorpresa_PorJugador extends Sorpresa{
	
	private int valor;
	
	Sorpresa_PorJugador(int valor, String texto){
        super(texto);
        this.valor = valor;
    }
	
	public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
                informe(actual, todos);
			for(int i = 0; i < todos.size(); i++){
				
				Sorpresa a=new Sorpresa_PagarCobrar(
						valor * (i==actual ? todos.size()-1 : -1 ),
						"AplicarAJugador_porJugador");
				
                a.aplicarAJugador(i, todos);
			}
		}
	}
}
