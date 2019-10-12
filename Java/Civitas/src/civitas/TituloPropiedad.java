/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;


public class TituloPropiedad {
    private float alquilerBase;
    private static float factorInteresesHipoteca;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private Boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    private Jugador propietario;
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        
    }
    
    Boolean cancelarHipoteca(Jugador jugador){
        
    }
    
    int cantidadCasasHoteles(){
        
    }
    
    Boolean comprar(Jugador jugador){
        
    }
    
    Boolean construirCasa(Jugador jugador){
        
    }
    
    Boolean construirHotel(Jugador jugador){
        
        
    }
    
    Boolean derruirCasas(int n,Jugador jugador){
        
    }
    
    private Boolean esEsteElPropietario(Jugador jugador){
        
    }
    
    public Boolean getHipotecado(){
        
    }
    
    float getImporteCancelarHipoteca(){
        return getImporteHipoteca*factorInteresesHipoteca
    }
    
    private float getImporteHipoteca(){
        return hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5))
    }
    
    String getNombre(){
        
    }
    
    int getNumCasas(){
        
    }
    
    int getNumHoteles(){
        
    }
    
    private float getPrecioAlquiler(){
        return (propietarioEncarcelado || hipotecado) ? 0 : alquilerBase*(1+(numCasas*0.5)+(numHoteles*2.5))
    }
    
    float getPrecioCompra(){
        
    }
    
    float getPrecioEdificar(){
        
    }
    
    private getPrecioVenta(){
        
    }
    
    Jugador getPropietario(){
        
    }
    
    Boolean hipotecar(Jugador jugador){
        
    }
    
    private Boolean propietarioEncarcelado(){
        
    }
    
    Boolean tienePropietario(){
        
    }
    
    TituloPropiedad(String nom,float ab,float fr,float hb,float pc,float pe){
        this.nombre=nom;
        this.alquilerBase=ab;
        this.factorRevalorizacion=fr;
        this.hipotecaBase=hb;
        this.precioCompra=pc;
        this.precioEdificar=pe;
        this.numCasas=0;
        this.numHoteles=0;
        this.hipotecado=false;
        this.propietario=null;
    }
    
    public String toString(){
        return "Nombre de la propiedad: " + nombre.to_String() + " Precio de compra: "+ precioCompra.to_String() + " Precio de edificar: " + precioEdificar.to_String() + " Propietario: " + propietario.toString();
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(this.esEsteElPropietario(jugador) && tienePropietario()){
            float p=this.getPrecioAlquiler();
            jugador.pagaAlquiler(p);
            propietario.recibe(p);
        }
    }
    
    Boolean vender(Jugador jugador){
        
    }
    
}
