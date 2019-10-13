package civitas;
import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    private void init(){
        sorpresas = new ArrayList<>();
        cartasEspeciales = new ArrayList<>();
        barajada = false;
        usadas = 0;
    }
    
    public MazoSorpresas(){
        init();
        debug = false;
    }
    
    public MazoSorpresas(boolean tdebug){
        init();
        debug = tdebug;
        Diario.getInstance().ocurreEvento("MazoSorpresas -- modo debug : " + debug);
    }
    
    public void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    public Sorpresa siguiente(){
        if(!barajada || usadas >= sorpresas.size()){
            Collections.shuffle(sorpresas);
            usadas = 0;
            barajada = true;
        }
        ultimaSorpresa = sorpresas.get(0);
        sorpresas.set(0, sorpresas.get(sorpresas.size()-1));
        sorpresas.set(sorpresas.size()-1, ultimaSorpresa);
        usadas++;
        return ultimaSorpresa;
    }
    
    public void inhabilitarCartaEspecial(Sorpresa s){
        int i = sorpresas.indexOf(s);
        if(i >= 0){
            sorpresas.remove(i);
            cartasEspeciales.add(s);
            Diario.getInstance().ocurreEvento("MazoSorpresas -- "
                    + "Carta especial deshabilitada");
        }
    }
    
    public void habilitarCartaEspecial(Sorpresa s){
        int i = cartasEspeciales.indexOf(s);
        if(i >= 0){
            cartasEspeciales.remove(i);
            sorpresas.add(s);
            Diario.getInstance().ocurreEvento("MazoSorpresas -- "
                    + "Carta especial habilitada");
        }
    }
    
}
