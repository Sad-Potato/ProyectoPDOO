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
        propietario=jugador;
    }
    
    Boolean cancelarHipoteca(Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    int cantidadCasasHoteles(){
        return numCasas+numHoteles
    }
    
    Boolean comprar(Jugador jugador){
      throw new UnsupportedOperationException("No implementado");
    }
    
    Boolean construirCasa(Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    Boolean construirHotel(Jugador jugador){
        
        throw new UnsupportedOperationException("No implementado");
    }
    
    Boolean derruirCasas(int n,Jugador jugador){
        if(jugador==propietario && numCasas>=n){
            numCasas-n;
            return true
        }
        else{
            return false
        }
    }
    
    private Boolean esEsteElPropietario(Jugador jugador){
        return jugador==propietario
    }
    
    public Boolean getHipotecado(){
        return hipotecado
    }
    
    float getImporteCancelarHipoteca(){
        return getImporteHipoteca*factorInteresesHipoteca
    }
    
    private float getImporteHipoteca(){
        return hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5))
    }
    
    String getNombre(){
        return nombre
    }
    
    int getNumCasas(){
        return numCasas
    }
    
    int getNumHoteles(){
        return numHoteles
    }
    
    private float getPrecioAlquiler(){
        return (propietarioEncarcelado || hipotecado) ? 0 : alquilerBase*(1+(numCasas*0.5)+(numHoteles*2.5))
    }
    
    float getPrecioCompra(){
        return precioCompra
    }
    
    float getPrecioEdificar(){
        return precioEdificar
    }
    
    private float getPrecioVenta(){
        return precioCompra+(numCasas+5*numHoteles)*precioEdificar*factorRevalorizacion
    }
    
    Jugador getPropietario(){
        return propietario
    }
    
    Boolean hipotecar(Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    private Boolean propietarioEncarcelado(){
        (!propietario.isEncarcelado() || propietario==null) ? return true : return false;
    }
    
    Boolean tienePropietario(){
        propietario==null ? false : true;
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
        if(jugador==propietario && !jugador.hipotecado){
            jugador.recibe(getPrecioVenta());
            propietario=null;
            numCasas=0;
            numHoteles=0;
            return true
        }
        else{
            return false
        }
    }
    
}
