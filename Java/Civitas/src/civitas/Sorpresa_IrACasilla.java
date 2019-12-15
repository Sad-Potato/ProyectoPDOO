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
public class Sorpresa_IrACasilla extends Sorpresa{
	private int valor;
	private Tablero tablero;
	
	
	Sorpresa_IrACasilla(Tablero ttablero, int valor, String texto){
        super(texto);
        tablero=ttablero;
        this.valor = valor;
    }
    
    @Override
	public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPosicion = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPosicion);
            tablero.getCasilla(nuevaPosicion).recibeJugador(actual, todos);
        }
    }
}
