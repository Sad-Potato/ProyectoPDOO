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
public class Casilla_Impuesto extends Casilla_Descanso {
    private float importe;
	
	Casilla_Impuesto(float cantidad ,String nombre){
		super(nombre);
		importe = cantidad;	
	}
	
	@Override
	void recibeJugador(int actual,ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual,todos)){
            this.informe(actual,todos);
            todos.get(actual).pagaImpuesto(importe);
        }
	}
	
	@Override
	public String toString(){
        return nombre + " (Tipo: Impuesto, Importe: " + importe + ")";
    }
}
