/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

public class Casilla {
    private String nombre;
    private static int carcel;
    private float importe;
    private TipoCasilla tipo;
    private TituloPropiedad tituloPropiedad;
    private MazoSorpresas mazo;
    
    Casilla(String tnombre){
        tipo=TipoCasilla.DESCANSO;
        nombre=tnombre;
    }

    Casilla (TituloPropiedad titulo){
        init();
        tipo=TipoCasilla.CALLE;
        nombre=titulo.getNombre();
        tituloPropiedad=titulo;
    }

    Casilla (float cantidad,String tnombre){
        init();
        tipo=TipoCasilla.IMPUESTO;
        importe=cantidad;
        nombre=tnombre;
    }

    Casilla (int numCasillaCarcel,String tnombre){
        init();
        tipo=TipoCasilla.JUEZ;
        carcel=numCasillaCarcel;
        nombre=tnombre;
    }

    Casilla (MazoSorpresas tmazo,String tnombre){
        init();
        tipo=TipoCasilla.SORPRESA;
        mazo=tmazo;
        nombre=tnombre;
    }

    public String getNombre(){
        return nombre;
    }

    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }

    private void informe(int iactual,ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento(todos.get(iactual).toString() + " " + toString());
    }

    private void init(){
		carcel=0;
		importe=0;
		tituloPropiedad=null;
		mazo=null;
    }

    public Boolean jugadorCorrecto(int iactual,ArrayList<Jugador> todos){
		return iactual<todos.size();
    }

    void recibeJugador(int iactual,ArrayList<Jugador> todos){
		switch(tipo){
			case CALLE:
				recibeJugador_calle(iactual,todos);
			case IMPUESTO:
				recibeJugador_impuesto(iactual,todos);
			case JUEZ:
				recibeJugador_juez(iactual,todos);
			case SORPRESA:
				recibeJugador_sorpresa(iactual,todos);
			default:
				informe(iactual,todos);
		}
    }

    private void recibeJugador_calle(int actual,ArrayList<Jugador> todos){
		if(jugadorCorrecto(actual, todos)){
			informe(actual,todos);
		}
		Jugador jugador = todos.get(actual);
		if( tituloPropiedad.tienePropietario() ){
			jugador.puedeComprarCasilla();
			tituloPropiedad.tramitarAlquiler(jugador);
		}
    }

    private void recibeJugador_impuesto(int actual,ArrayList <Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            this.informe(actual,todos);
            todos.get(actual).pagaImpuesto(importe);
        }
    }

    private void recibeJugador_juez(int actual,ArrayList <Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
                informe(actual,todos);
                todos.get(actual).encarcelar(carcel);
        }
    }

    private void recibeJugador_sorpresa(int actual,ArrayList <Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
			informe(actual, todos);
			mazo.siguiente().aplicarAJugador(actual, todos);
		}
    }

    public String toString(){
        return "Nombre: " + nombre;
    }

}
