package civitas;
import java.util.ArrayList;

public class Sorpresa_porjugador extends Sorpresa2{
    Sorpresa_porjugador(){


    }

    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            for(int i = 0; i < todos.size(); i++){
                Sorpresa a=new Sorpresa(
                        TipoSorpresa.PAGARCOBRAR,
                        tablero,
                        valor * (i==actual ? todos.size()-1 : -1 ),
                        "AplicarAJugador_porJugador");
                                a.aplicarAJugador(i, todos);
            }
        }
    }
}