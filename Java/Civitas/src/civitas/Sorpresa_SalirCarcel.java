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
public class Sorpresa_SalirCarcel extends Sorpresa{
	
	private MazoSorpresas mazo;
	
	Sorpresa_SalirCarcel(MazoSorpresas mazo, String texto){
        super(texto);
        this.mazo = mazo;
    }
	
	@Override
	public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
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
		
		mazo.inhabilitarCartaEspecial(this);
		
	}
	
	void usada(){
		
		mazo.habilitarCartaEspecial(this);
		
	}
	
}
