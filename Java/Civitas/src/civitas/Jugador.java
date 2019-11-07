package civitas;
import java.lang.Math;
import java.util.ArrayList;

public class Jugador implements Comparable<Jugador>{
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected static int HotelesMax = 4;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    protected static float SaldoInicial = 7500;
    
    protected boolean encarcelado;
    private String nombre;
    private int numCasillaActual;
    private boolean puedeComprar;
    private float saldo;
	private Sorpresa salvoconducto;
	private ArrayList<TituloPropiedad> propiedades;
    
	Jugador(String tnombre){
		nombre = tnombre;
		encarcelado = false;
		numCasillaActual = 0;
		puedeComprar = true;
		saldo = SaldoInicial;
		salvoconducto = null;
		propiedades = new ArrayList<>();
	}
	
	protected Jugador(Jugador otro){
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
	
	private int getCasasMax(){
		return CasasMax;
	}
	int getCasasPorHotel(){
		return CasasPorHotel;
	}
	private int getHotelesMax(){
		return HotelesMax;
	}
	protected String getNombre(){
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
	protected ArrayList<TituloPropiedad> getPropiedades(){
		return propiedades;
	}
	boolean getPuedeComprar(){
		return puedeComprar;
	}
	protected float getSaldo(){
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
	
	boolean obtenerSalvoconducto(Sorpresa s){
		if(!encarcelado){
			salvoconducto = s;
		}
		return !encarcelado;
	}
	
	private void perderSalvoconducto(){
		salvoconducto.usada();
		salvoconducto = null;
	}
	
	boolean tieneSalvoconducto(){
		return salvoconducto != null;
	}
	
	boolean puedeComprarCasilla(){
		return !encarcelado;
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
		}
		return !encarcelado;
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
		return propiedades.isEmpty();
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
		String resul="Jugador: " + nombre + "; Casilla: " + numCasillaActual+ "; \nPropiedades:\n ";
		for(int i=0;i<propiedades.size();i++){
			 resul+=propiedades.get(i).toString() +"\n";
		}
		return resul;
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
			float cantidad = propiedad.getImporteCancelarHipoteca();
			if( puedoGastar(cantidad) ){
				result = propiedad.cancelarHipoteca(this);
				if( result ){
					Diario.getInstance().ocurreEvento("Jugador -- El jugador " + 
							nombre + " cancela la hipoteca de la propiedad " + propiedades.get(ip).toString());
				}
			}
		}
		return result;
	}

	boolean puedoEdificarCasa(TituloPropiedad propiedad){
		return propiedad.getNumCasas()<CasasMax;
	}

	boolean puedoEdificarHotel(TituloPropiedad propiedad){
		return (propiedad.getNumCasas()==CasasMax && propiedad.getNumHoteles() < HotelesMax);
	}

	boolean construirCasa(int ip){
		boolean result=false;
		boolean puedoEdificarCasa=false;
		if(!encarcelado){	//1
			boolean existe=existeLaPropiedad(ip); //2
			if(existe){
				TituloPropiedad propiedad=propiedades.get(ip); //3
				puedoEdificarCasa=this.puedoEdificarCasa(propiedad); //4
				float precio=propiedad.getPrecioEdificar(); //4.1
				if(this.puedoGastar(precio) && propiedad.getNumCasas() < this.getCasasMax()){
					puedoEdificarCasa=true;//4.2
				}
				if(puedoEdificarCasa){
					result=propiedad.construirCasa(this);//5
					if(result)
						Diario.getInstance().ocurreEvento("El jugador "+nombre+" construye casa en la propiedad "+ip); //6
				}
			}
		}
		return result; //1
	}

	boolean construirHotel(int ip){
		return true;
	}

    Boolean hipotecar(int ip) {
       	Boolean result=false;
	   	if(encarcelado){
			return result;
	   	}
		if(existeLaPropiedad(ip)){
			TituloPropiedad propiedad=propiedades.get(ip);
			result=propiedad.hipotecar(this);
		}
		if(result){
			Diario.getInstance().ocurreEvento("El jugador "+nombre+" hipoteca la propiedad "+ip);
		}
		return result;
    }
	
}
