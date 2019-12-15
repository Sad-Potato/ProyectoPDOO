package civitas;
import java.lang.Math;
import GUI.Dado;
import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    protected static float SaldoInicial = 7500;
    
    protected boolean encarcelado;
    protected String nombre;
    protected int numCasillaActual;
    protected boolean puedeComprar;
    protected float saldo;
    protected Sorpresa_SalirCarcel salvoconducto;
    protected ArrayList<TituloPropiedad> propiedades;

    public Jugador(String tnombre){
            nombre = tnombre;
            encarcelado = false;
            numCasillaActual = 0;
            puedeComprar = true;
            saldo = SaldoInicial;
            salvoconducto = null;
            propiedades = new ArrayList<>();
    }

    public Jugador(Jugador otro){
            nombre = otro.nombre;
            encarcelado = otro.encarcelado;
            numCasillaActual = otro.numCasillaActual;
            puedeComprar = otro.puedeComprar;
            saldo = otro.saldo;
            salvoconducto = otro.salvoconducto;
            propiedades = new ArrayList<>(otro.propiedades);		
    }

    public Boolean isEncarcelado(){
            return encarcelado;
    }

    protected int getCasasMax(){
            return CasasMax;
    }
    int getCasasPorHotel(){
            return CasasPorHotel;
    }
    protected int getHotelesMax(){
            return HotelesMax;
    }
    public String getNombre(){
            return nombre;
    }
    int getNumCasillaActual(){
            return numCasillaActual;
    }
    private float getPrecioLibertad(){
            return PrecioLibertad;
    }
    private float getPremioPasoSalida(){
            return PasoPorSalida;
    }

    public ArrayList<TituloPropiedad> getPropiedades(){
            return propiedades;
    }
    boolean getPuedeComprar(){
            return puedeComprar;
    }
    public float getSaldo(){
            return saldo;
    }
    boolean enBancarrota(){
            return saldo < 0;
    }

    int cantidadCasasHoteles(){
            int sum = 0;
            for(TituloPropiedad i:propiedades){
                    sum += i.getNumCasas()+i.getNumHoteles();
            }
            return sum;
    }

    boolean comprar(TituloPropiedad titulo){
            boolean result=false;
            if(encarcelado){
                    return result;
            }
            if(puedeComprar){
                    float precio=titulo.getPrecioCompra();
                    if(puedoGastar(precio)){
                            result=titulo.comprar(this);
                            if(result){
                                    propiedades.add(titulo);
                                    Diario.getInstance().ocurreEvento("El jugador "+nombre+" compra la propiedad "+ titulo.toString());
                            }
                            puedeComprar=false;
                    }
            }
            return result;
    }
	
        
	
    protected boolean debeSerEncarcelado(){
        if(!encarcelado && tieneSalvoconducto()){
            Diario.getInstance().ocurreEvento("Jugador -- "
                + nombre + " usa su carta sorpresa para evitar la carcel");
            perderSalvoconducto();
            return false;
        }
        return !encarcelado;
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            Diario.getInstance().ocurreEvento("Jugador -- "
                    + nombre + " es mandado a la carcel");
        }
        return encarcelado;
    }
	
    boolean obtenerSalvoconducto(Sorpresa_SalirCarcel s){
            if(!encarcelado){
                    salvoconducto = s;
            }
            return !encarcelado;
    }

    protected void perderSalvoconducto(){
            salvoconducto.usada();
            salvoconducto = null;
    }

    boolean tieneSalvoconducto(){
            return salvoconducto != null;
    }

    boolean puedeComprarCasilla(){
            puedeComprar=(!encarcelado);
            return puedeComprar;
    }

    boolean modificarSaldo(float cantidad){
            saldo += cantidad;
            return true;
    }

    boolean paga(float cantidad){
            return modificarSaldo(cantidad * -1);
    }

    boolean pagaImpuesto(float cantidad){
            return !encarcelado && paga(cantidad);
    }

    boolean pagaAlquiler(float cantidad){
            return !encarcelado && paga(cantidad);
    }

    boolean recibe(float cantidad){
            return !encarcelado && modificarSaldo(cantidad);
    }

    boolean moverACasilla(int numCasilla){
            if(!encarcelado){
                    numCasillaActual = numCasilla;
                    puedeComprar = false;
                    Diario.getInstance().ocurreEvento("Jugador -- "
                                    + nombre + " se ha movido a la casilla " + String.valueOf(numCasilla));
                    return true;
            }
            else{
                return encarcelado;
            }
    }

    private boolean puedoGastar(float precio){
            return !encarcelado && saldo >= precio;
    } 

    private boolean existeLaPropiedad(int ip){
            return ip < propiedades.size();
    }

    boolean vender(int ip){
            if(encarcelado || !existeLaPropiedad(ip) || !propiedades.get(ip).vender(this)){
                    return false;
            }
            Diario.getInstance().ocurreEvento("Jugador -- "
                            + nombre + " ha vendido la casilla " + propiedades.get(ip).getNombre());
            propiedades.remove(ip);
            return true;
    }


    boolean tieneAlgoQueGestionar(){
            return !propiedades.isEmpty();
    }

    boolean salirCarcelPagando(){
            if(encarcelado && paga(PrecioLibertad)){
                    encarcelado = false;
                    Diario.getInstance().ocurreEvento("Jugador -- "
                                    + nombre + " paga para salir de la carcel");
                    return true;
            } else {
                    return false;
            }
    }

    boolean salirCarcelTirando(){
            boolean b = Dado.getInstance().salgoDeLaCarcel();
            if(b){
                    encarcelado = false;
                    Diario.getInstance().ocurreEvento("Jugador -- "
                                    + nombre + " consigue salir de la carcel tirando dados");
            }
            return b;
    }


    boolean pasaPorSalida(){
            saldo += PasoPorSalida;
            Diario.getInstance().ocurreEvento("Jugador -- "
                            + nombre + " ha pasado por la casilla de salida");
            return true;
    }

    public String toString(){
            String resul="Jugador: " + nombre + "; Posicion: " + String.valueOf(numCasillaActual) + "; Saldo: " + String.valueOf(getSaldo())+
                         "; Propiedades: " + String.valueOf(propiedades.size()) + "; Encarcelado: " + Respuestas.values()[encarcelado ? 1:0] +"\n";
            return resul;
    }

    public String toStringPropiedades(){
            String resul="Propiedades:\n";
            for(int i=0;i<propiedades.size();i++){
                    resul+=propiedades.get(i).toString() +"\n";
            }
            return resul+"\n";
    }

    public int compareTo(Jugador otro){
            return (int) Math.signum(saldo - otro.saldo);
    }

    boolean cancelarHipoteca(int ip){
            boolean result = false;
            if( encarcelado ){
                    return result;
            }
            if( existeLaPropiedad(ip) ){
                    TituloPropiedad propiedad = propiedades.get(ip);
                    if( puedoGastar( propiedad.getImporteCancelarHipoteca() ) ){
                            result = propiedad.cancelarHipoteca(this);
                            if( result ){
                                    Diario.getInstance().ocurreEvento("Jugador -- El jugador " + 
                                                    toString() + " cancela la hipoteca de la propiedad " + propiedad.toString());
                            }
                    }
            }
            return result;
    }

    private boolean puedoEdificarCasa(TituloPropiedad prop){
            return  puedoGastar( prop.getPrecioEdificar() ) &&
                            prop.getNumCasas() < this.getCasasMax();
    }

    boolean construirCasa(int ip){
            boolean result=false;
            if(!encarcelado){	//1
                    boolean existe=existeLaPropiedad(ip); //2
                    if(existe){
                            TituloPropiedad propiedad=propiedades.get(ip); //3
                            if(this.puedoEdificarCasa(propiedad)){
                                    result=propiedad.construirCasa(this);//5
                                    if(result)
                                            Diario.getInstance().ocurreEvento("\nEl jugador -- " + toString()  + 
                                                    " construye casa en la propiedad "+ propiedad.toString()); //6
                            }
                    }
            }
            return result; //1
    }

    private boolean puedoEdificarHotel(TituloPropiedad prop){
            return  puedoGastar( prop.getPrecioEdificar() ) &&
                            prop.getNumHoteles() < this.getHotelesMax() &&
                            prop.getNumCasas() >= CasasPorHotel;
    }

    boolean construirHotel(int ip){
            boolean result = false;
            if(encarcelado){
                    return result;
            }
            if( existeLaPropiedad(ip) ){
                    TituloPropiedad prop = propiedades.get(ip);
                    if(puedoEdificarHotel(prop)){
                            result = prop.construirHotel(this);
                            prop.derruirCasas( CasasPorHotel , this);
                            if(result) 
                                Diario.getInstance().ocurreEvento("\nJugador --  "  + toString()  + 
                                            " construye hotel en la propiedad " + prop.toString());
                    }
            }
            return result;
    }

    boolean hipotecar(int ip) {
       	boolean result=false;
		TituloPropiedad propiedad;
	   	if(encarcelado){
			return result;
	   	}
		if(existeLaPropiedad(ip)){
			propiedad=propiedades.get(ip);
			result=propiedad.hipotecar(this);
			if(result){
				Diario.getInstance().ocurreEvento("El jugador "+ toString() +
							" hipoteca la propiedad "+ propiedad.toString() );
			}
		}
		return result;
	}

}