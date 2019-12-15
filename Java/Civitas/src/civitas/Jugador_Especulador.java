package civitas;
import java.util.ArrayList;

public class Jugador_Especulador extends Jugador{
    private static int FactorEspeculador=2;
    private float fianza;


    Jugador_Especulador(Jugador tjugador,float tfianza){
        super(tjugador);
        fianza=tfianza;
        for(TituloPropiedad p:propiedades){
            p.actualizaPropietarioPorConversion(this);
        }
    }

    @Override
    protected boolean debeSerEncarcelado(){
        if(!encarcelado && tieneSalvoconducto()){
            Diario.getInstance().ocurreEvento("Jugador -- "
                + nombre + " usa su carta sorpresa para evitar la carcel");
            perderSalvoconducto();
            return false;
        }
        return !encarcelado;
    }

    public String toString(){
        String resul="Jugador Especulador: " + nombre + "; Posicion: " + String.valueOf(numCasillaActual) + "; Saldo: " + String.valueOf(getSaldo())+
        "; Propiedades: " + String.valueOf(propiedades.size()) + "; Encarcelado: " 
        + Respuestas.values()[encarcelado ? 1:0] + "; Fianza: " + fianza + "\n";
        return resul;
    }


}