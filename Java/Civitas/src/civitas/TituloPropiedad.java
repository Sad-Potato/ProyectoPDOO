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
        return numCasas+numHoteles;
    }
    
    Boolean comprar(Jugador jugador){
      boolean result=false;
      if(!tienePropietario()){
          propietario=jugador;
          result=true;
          propietario.paga(getPrecioCompra());
      }
      return result;
    }
    
    Boolean construirCasa(Jugador jugador){
        throw new UnsupportedOperationException("No implementado");
    }
    
    Boolean construirHotel(Jugador jugador){
        
        throw new UnsupportedOperationException("No implementado");
    }
    
    Boolean derruirCasas(int n,Jugador jugador){
        if(jugador==propietario && numCasas>=n){
            numCasas-=n;
            return true;
        }
        else{
            return false;
        }
    }
    
    private Boolean esEsteElPropietario(Jugador jugador){
        return jugador==propietario;
    }
    
    public Boolean getHipotecado(){
        return hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return getImporteHipoteca()*factorInteresesHipoteca;
    }
    
    private float getImporteHipoteca(){
        return (float) (hipotecaBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }
    
    String getNombre(){
        return nombre;
    }
    
    int getNumCasas(){
        return numCasas;
    }
    
    int getNumHoteles(){
        return numHoteles;
    }
    
    private float getPrecioAlquiler(){
        return (float) ((propietarioEncarcelado() || hipotecado) ? 0 : alquilerBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
    }
    
    float getPrecioCompra(){
        return precioCompra;
    }
    
    float getPrecioEdificar(){
        return precioEdificar;
    }
    
    private float getPrecioVenta(){
        return precioCompra+(numCasas+5*numHoteles)*precioEdificar*factorRevalorizacion;
    }
    
    Jugador getPropietario(){
        return propietario;
    }
    
    Boolean hipotecar(Jugador jugador){
        boolean salida=false;
        if(!hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            hipotecado=true;
            salida=true;
        }
        return salida;
    }
    
    private Boolean propietarioEncarcelado(){
        Boolean resul=(!(propietario.isEncarcelado()) || propietario==null); 
                return resul;
    }
    
    Boolean tienePropietario(){
        return propietario!=null;
    }
    
    TituloPropiedad(String nombre,float ab,float fr,float hb,float pc,float pe){
        this.nombre=nombre;
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
    
    @Override
    public String toString(){
        return "Nombre de la propiedad: " + nombre + "; Precio de compra: "+ String.valueOf(precioCompra) + 
                "; Precio de edificar: " + String.valueOf(precioEdificar) + "; Propietario: " + propietario.getNombre() + ";";
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && esEsteElPropietario(jugador)){
            float p=this.getPrecioAlquiler();
            jugador.pagaAlquiler(p);
            propietario.recibe(p);
        }
    }
    
    Boolean vender(Jugador jugador){
        if(jugador==propietario && !hipotecado){
            jugador.recibe(getPrecioVenta());
            propietario=null;
            numCasas=0;
            numHoteles=0;
            return true;
        } else {
            return false;
        }
    }
    
}
