/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class Casilla_Descanso {
    protected String nombre;
    private TipoCasilla tipo;
    
    Casilla_Descanso(String nombre){
        this.nombre = nombre;
	}

    public String getNombre(){
        return nombre;
    }

    protected void informe(int iactual,ArrayList<Jugador> todos){  
        Diario.getInstance().ocurreEvento(todos.get(iactual).toString() + " " + toString());
    }

    public Boolean jugadorCorrecto(int iactual,ArrayList<Jugador> todos){
		return iactual < todos.size();
    }

    void recibeJugador(int iactual,ArrayList<Jugador> todos){
		informe(iactual,todos);
		
    }

    public String toString(){
        return nombre + " (Tipo: Descanso)";
    }

}
