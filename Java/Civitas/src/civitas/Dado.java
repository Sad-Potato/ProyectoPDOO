/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.Random;

public class Dado {
    private Random random=new Random();
    private int ultimoResultado;
    private Boolean debug;
    private static final Dado instance=new Dado();
    private static final int SalidaCarcel=5;
    
    private Dado(){
        ultimoResultado=0;
        debug=false;
    }
    
    static public Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        if(debug){
            ultimoResultado=1;
        }
        else{
            ultimoResultado=random.nextInt(6)+1;
        }
        return ultimoResultado;
    }
    
    Boolean salgoDeLaCarcel(){
        return tirar()>=SalidaCarcel;
    }
    
    int quienEmpieza(int n){
        Random casual=new Random();
        return casual.nextInt(n);
    }
    
    void setDebug(Boolean d){
        debug=d;
		Diario.getInstance().ocurreEvento("Se ha " + (d?"activado":"desactivado")+" el modo debug del dado.");
    }
    
    public int getUltimoResultado(){
        return ultimoResultado;
    }
}
