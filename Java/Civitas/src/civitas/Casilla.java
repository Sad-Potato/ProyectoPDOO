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
    
    Casilla(TipoCasilla type,String nombr){
        tipo=type;
        nombre=nombr;
    }

    Casilla (TipoCasilla type,TituloPropiedad titulo){
        init();
        tipo=type;
        nombre=titulo.getNombre();
        tituloPropiedad=titulo;
    }

    Casilla (TipoCasilla type,float cantidad,String nombr){
        init();
        tipo=type;
        importe=cantidad;
        nombre=nombr;
    }

    Casilla (TipoCasilla type,int numCasillaCarcel,String nombr){
        init();
        tipo=type;
        carcel=numCasillaCarcel;
        nombre=nombr;
    }

    Casilla (TipoCasilla type,MazoSorpresas Mazo,String nombr){
        init();
        tipo=type;
        mazo=Mazo;
        nombre=nombr;
    }

    public String getNombre(){
        return nombre;
    }

    TituloPropiedad getTituloPropiedad(){
        return tituloPropiedad;
    }

    private void informe(int iactual,ArrayList <Jugador> todos){
        Diario.getInstance().ocurreEvento(todos.get(iactual).toString() + " " + this.toString());
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

    }

    private void recibeJugador_calle(int iactual,ArrayList<Jugador> todos){
        
    }

    private void recibeJugador_impuesto(int iactual,ArrayList <Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            this.informe(iactual,todos);
            todos.get(iactual).pagaImpuesto(importe);
        }
    }

    private void recibeJugador_juez(int iactual,ArrayList <Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
                informe(iactual,todos);
                todos.get(iactual).encarcelar(carcel);
        }
    }

    private void recibeJugador_sorpresa(int iactual,Jugador todos){
        
    }

    public String toString(){
        return "SquareType: " + nombre;
    }
}
