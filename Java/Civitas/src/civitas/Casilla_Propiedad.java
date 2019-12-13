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
public class Casilla_Propiedad extends Casilla_Descanso {
	
	private TituloPropiedad tituloPropiedad;
	
	Casilla_Propiedad (TituloPropiedad titulo){
        super(titulo.getNombre());
        tituloPropiedad=titulo;
    }
	
	TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }
	
	@Override
	void recibeJugador(int actual,ArrayList<Jugador> todos){
			if(jugadorCorrecto(actual, todos)){
			informe(actual,todos);
			Jugador jugador = todos.get(actual);
			if( !tituloPropiedad.tienePropietario() ) 
				jugador.puedeComprarCasilla();
			else{      
				tituloPropiedad.tramitarAlquiler(jugador);
			}
		}   
	}
	
	
	@Override
	public String toString(){
        return tituloPropiedad.toString();
    }
}
