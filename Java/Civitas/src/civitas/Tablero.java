/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;


public class Tablero {
    private int numCasillaCarcel;
    private int porSalida;
    private ArrayList<Casilla> casillas;
    private Boolean tieneJuez;
  
    public Tablero(int indexcarcel){
      if(indexcarcel>=1){
          numCasillaCarcel=indexcarcel;
      }
      else{
          numCasillaCarcel=1;
      }
      casillas=new ArrayList<>();
      Casilla obj =new Casilla("Salida");
      casillas.add(obj);
      porSalida=0;
      tieneJuez=false;
    }

    private Boolean correcto(){
        return casillas.size()>numCasillaCarcel && tieneJuez;
    }

    private Boolean correcto(int numCasilla){
        return correcto() && numCasilla<casillas.size();
    }

    public int getCarcel(){
        return numCasillaCarcel;
    }

    public int getPorSalida(){
        int copia=porSalida;
        if(porSalida>0){
            porSalida-=1;
        }
        return copia;
    }

    public void añadeCasilla(Casilla casilla){
        Casilla Carcel=new Casilla("Carcel");
        if(casillas.size()==numCasillaCarcel){
            casillas.add(Carcel);
        }
        casillas.add(casilla);
    }

    public void añadeJuez(){
        if(!tieneJuez){
            Casilla Juez=new Casilla("Juez");
            tieneJuez=true;
        }
    }
    
    public Casilla getCasilla(int numCasilla){
            return correcto(numCasilla) ? casillas.get(numCasilla):null;
    }
 
    public int nuevaPosicion(int actual,int tirada){
        int resultado=actual+tirada;
        if(correcto(actual)){
            if(!(actual+tirada<casillas.size())){
                resultado=resultado%casillas.size();
                porSalida++;
            }
        }
        else{
            resultado=-1;
        }
        return resultado;
    }
    
    public int calcularTirada(int origen,int destino){
        int resultado;
        if(destino-origen>0){
            resultado=destino-origen;
        }
        else{
            resultado=destino-origen+casillas.size();
        }
        return resultado;
    }
    
    
    
}