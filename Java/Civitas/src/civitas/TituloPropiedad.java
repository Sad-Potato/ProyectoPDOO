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
        Boolean result=false;
        if(hipotecado && esEsteElPropietario(jugador)){
            propietario.paga(getImporteCancelarHipoteca());
            hipotecado=false;
            result=true;
        }
        return result;
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
        Boolean b=esEsteElPropietario(jugador);
        if(b){
            jugador.paga(precioEdificar);
            numCasas+=1;
        }
        return b;
    }
    
    Boolean construirHotel(Jugador jugador){
        Boolean b=esEsteElPropietario(jugador);
        if(b){
            propietario.paga(precioEdificar*5);
            numHoteles+=1;
        }
        return b;
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
        return (float) (alquilerBase*(1+(numCasas*0.5)+(numHoteles*2.5)));
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
        return "Propiedad: " + nombre + "; Precio de compra: "+ String.valueOf(precioCompra) + 
                "; Precio de edificar: " + String.valueOf(precioEdificar) + "; Casas: " + String.valueOf(numCasas) + "; Hoteles: " + String.valueOf(numHoteles);
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
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
