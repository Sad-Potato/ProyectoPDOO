package civitas;
import java.util.ArrayList;

public class Jugador_Especulador extends Jugador{
    private static int FactorEspeculador=2;
    private float fianza;


    public Jugador_Especulador(Jugador tjugador,float tfianza){
        super(tjugador);
        fianza=tfianza;
        for(TituloPropiedad p:propiedades){
            p.actualizaPropietarioPorConversion(this);
        }
    }

    // Not sure if its like this.
    @Override
    protected int getCasasMax(){
        return super.getCasasMax()*2;
    }

    @Override
    protected int getHotelesMax(){
        return super.getHotelesMax()*2;
    }

    /**********************************************/

    @Override
    protected boolean debeSerEncarcelado(){
        if(!encarcelado && tieneSalvoconducto()){
            Diario.getInstance().ocurreEvento("Jugador Especulador-- "
            + nombre + " usa su carta sorpresa para evitar la carcel");
            perderSalvoconducto();
            return false;
        }
        else if(!tieneSalvoconducto()){
            Diario.getInstance().ocurreEvento("Jugador Especulador-- "
            + nombre + " usa fianza para evitar la carcel.");
            //?¿ Ambiguous 
            this.paga(fianza);
            return false;
        }
        return !encarcelado;
    }

    @Override   
    boolean pagaImpuesto(float cantidad){
        return !encarcelado && paga(cantidad/2);
    }

    @Override
    public String toString(){
        String resul="Jugador Especulador: " + nombre + "; Posicion: " + String.valueOf(numCasillaActual) + "; Saldo: " + String.valueOf(getSaldo())+
        "; Propiedades: " + String.valueOf(propiedades.size()) + "; Encarcelado: " 
        + Respuestas.values()[encarcelado ? 1:0] + "; Fianza: " + fianza + "\n";
        return resul;
    }


}