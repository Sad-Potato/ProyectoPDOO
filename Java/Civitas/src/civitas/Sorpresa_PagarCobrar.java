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
public class Sorpresa_PagarCobrar extends Sorpresa{
	
	private int valor;
	
	Sorpresa_PagarCobrar(int valor, String texto){
        super(texto);
        this.valor = valor;
    }
	
	public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
			informe(actual, todos);
			todos.get(actual).modificarSaldo(valor);
		}
	}
	
}
