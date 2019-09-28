/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private Boolean barajada;
    private int usadas;
    private Boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    private void init(){
        usadas=0;
        sorpresas=new ArrayList<>();
        cartasEspeciales=new ArrayList<>();
        barajada=false;
    }
    
    public MazoSorpresas(Boolean d){
        debug=d;
        this.init();
        Diario.getInstance().ocurreEvento("Se ha " + (d?"activado":"desactivado")+" el modo debug del mazo.");
    }
    
    public MazoSorpresas(){
        init();
        debug=false;
    }
    
    public void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    public Sorpresa siguiente(){
        Sorpresa copia;
        if((barajada==false || usadas==sorpresas.size()) && !debug){
            Collections.shuffle(sorpresas);
            usadas=0;
            barajada=true;
        }
        usadas++;
        ultimaSorpresa=sorpresas.get(0);
        for(int i=0;i<sorpresas.size()-1;i++){
            sorpresas.set(i,sorpresas.get(i+1));
        }
        sorpresas.set(sorpresas.size()-1,ultimaSorpresa);
        return ultimaSorpresa;
    }
    
    public void inhabilitarCartaEspecial(Sorpresa sorpresa){
        Boolean satisfactoria=false;
        for(int i=0;i<sorpresas.size();i++){
            if(sorpresa==sorpresas.get(i)){
                cartasEspeciales.add(sorpresa);
                sorpresas.remove(i);
                satisfactoria=true;
            }
        }
        if(satisfactoria){
            Diario.getInstance().ocurreEvento("Se ha eliminado una carta sorpresa.");
        }
    }
    
    public void habilitarCartaEspecial(Sorpresa sorpresa){
        Boolean satisfactoria=false;
        for(int i=0;i<cartasEspeciales.size();i++){
            if(sorpresa==cartasEspeciales.get(i)){
                sorpresas.add(sorpresa);
                satisfactoria=true;
            }
        }
        if(satisfactoria==true){
            Diario.getInstance().ocurreEvento("Se ha añadido una carta sorpresa.");
        }
    }
    
}
