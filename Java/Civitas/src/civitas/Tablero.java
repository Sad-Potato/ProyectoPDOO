package civitas;
import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private boolean tieneJuez;
    
    public Tablero(int tNumCasillaCarcel){
        numCasillaCarcel = tNumCasillaCarcel;
        porSalida = 0;
        tieneJuez = false;
        casillas = new ArrayList<>();
    }
    
    private boolean correcto(){
        return casillas.size() > numCasillaCarcel;
    }
    
    private boolean correcto(int numCasilla){
        return correcto() && (casillas.size() > numCasilla);
    }
    
    public int getCarcel(){
        return numCasillaCarcel;
    }
    
    public int getPorSalida(){
        int t = porSalida;
        if (t != 0){
            porSalida--;
        }
        return t;
    }
    
    public void añadeCasilla(Casilla casilla){
        casillas.add(casilla);
        if (casillas.size() >= numCasillaCarcel){
            casillas.add(new Casilla("Carcel"));
        }
    }
    
    public void añadeJuez(){
        if (!tieneJuez){
            añadeCasilla(new Casilla("Juez"));
            tieneJuez = true;
        }
    }
    
    public Casilla getCasilla(int numCasilla){
        return (numCasilla >= 0) && (numCasilla < casillas.size()) ? casillas.get(numCasilla) : null;
    }
    
    public int nuevaPosicion(int actual, int tirada){
        if(!correcto()){
            return -1;
        }
        int ncasilla = (actual + tirada) % casillas.size();
        if(ncasilla != actual + tirada){
            porSalida++;
        }
        return ncasilla;
    }
    
    public int calcularTirada(int origen, int destino){
        return (destino-origen+casillas.size())%casillas.size();
    }
    
}
