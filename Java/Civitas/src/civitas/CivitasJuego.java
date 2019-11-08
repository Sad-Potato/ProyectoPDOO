package civitas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


public class CivitasJuego{
	private int indiceJugadorActual;
	private ArrayList<Jugador> jugadores;
	private GestorEstados gestorEstados;
	private MazoSorpresas mazo;
	private Tablero tablero;
	private EstadosJuego estado;
        
        
	private void avanzaJugador(){
		Jugador jugadorActual=jugadores.get(indiceJugadorActual);
		int posicionActual=jugadorActual.getNumCasillaActual();
		int tirada=Dado.getInstance().tirar();
		int posicionNueva=tablero.nuevaPosicion(posicionActual,tirada);
		Casilla casilla=tablero.getCasilla(posicionNueva);
                casilla.toString();
		contabilizarPasosPorSalida(jugadorActual);
                jugadores.get(0).toString();
		casilla.recibeJugador(indiceJugadorActual, jugadores);
		contabilizarPasosPorSalida(jugadorActual); 
	}

	public CivitasJuego(ArrayList <String> nombres){
		jugadores=new ArrayList<>();
		for(int i=0;i<nombres.size();i++){
			jugadores.add(new Jugador(nombres.get(i)));
		}
		gestorEstados=new GestorEstados();
		estado=gestorEstados.estadoInicial();
		indiceJugadorActual=Dado.getInstance().quienEmpieza(nombres.size());
		mazo=new MazoSorpresas();
		inicializarTablero(mazo);
		inicializarMazoSorpresas(tablero);

	}

	public boolean cancelarHipoteca(int ip){
		return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
	}

	public boolean comprar(){
		Jugador jugador = jugadores.get(indiceJugadorActual);
		int num = jugador.getNumCasillaActual();
		Casilla casilla = tablero.getCasilla(num);
		TituloPropiedad titulo = casilla.getTituloPropiedad();
		return jugador.comprar(titulo);
	}

	public boolean construirCasa(int ip){
		return jugadores.get(indiceJugadorActual).construirCasa(ip);
	}

	public boolean construirHotel(int ip){
		return jugadores.get(indiceJugadorActual).construirHotel(ip);
	}

	
	private void contabilizarPasosPorSalida(Jugador jugadorActual){
		while(tablero.getPorSalida()>0){
			jugadorActual.pasaPorSalida();
		}
	}

	public boolean finalDelJuego(){
		boolean result=false;
		for(int i=0;i<jugadores.size();i++){
			if(jugadores.get(i).enBancarrota()){
				result=true;
			}
		}
		return result;
	}

	public Casilla getCasillaActual(){
		return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
	}

	public Jugador getJugadorActual(){
		return jugadores.get(indiceJugadorActual);
	}

	public boolean hipotecar(int ip){
		return jugadores.get(indiceJugadorActual).hipotecar(ip);
	}

	public String infoJugadorTexto(){
		return jugadores.get(indiceJugadorActual).toString();
	}

	private void inicializarMazoSorpresas(Tablero tablero){
		mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 500, "Concurso de belleza, cobra 500."));
		mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -500, "Multa de trafico, paga 500."));
		mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, tablero.getCarcel(), "Visita a tu compañero de la carcel"));
		mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 2, "Ruleta rusa."));
		mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 3, "Ruleta rusa."));
		mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 50, "Dineros por cada casa"));
		mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, -50, "Dineros (negativos) por casas"));
		mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
		mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero, 0, "Sorpresa! Carcel"));
	}

	private void inicializarTablero(MazoSorpresas mazo){
		tablero = new Tablero(5);
		float revalorizacion = (float) 1.15;
		// =>  La casilla del inicio se introduce automáticamente
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Calle Caramelo", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Plaza Galleta", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(mazo, "Esquina de las Pizzas"));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Estación de los gofres", 50, revalorizacion, 150, 200, 120)
		));
		// =>  Aquí meterá tablero a la carcel
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Ruta glaseada", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Rio Cremoso", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(mazo, "Puente Hamburguesa"));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Zoo de los dulces", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla("Parking Gratuito"));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Cruce Gominolas", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Chocolate Street", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(300, "Impuesto del Azucar"));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Avenida de las Avellanas", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeJuez();
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Banco de monedas de chocolate", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Palacio del dulce de leche", 50, revalorizacion, 150, 200, 120)
		));
		tablero.añadeCasilla(new Casilla(mazo, "Puente Hamburguesa"));
		tablero.añadeCasilla(new Casilla(
		  new TituloPropiedad("Hotel de los batidos", 50, revalorizacion, 150, 200, 120)
		));
	}

	private void pasarTurno(){
		indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
	}

	private ArrayList<Jugador> ranking(){
		int p;
		ArrayList<Jugador> copia;
		copia=new ArrayList<>(jugadores);
		Collections.sort(copia);
		return copia;
	}

	public Boolean salirCarcelPagando(){
		return jugadores.get(indiceJugadorActual).salirCarcelPagando();
	}

	public Boolean salirCarcelTirando(){
		return jugadores.get(indiceJugadorActual).salirCarcelTirando();
	}

	public OperacionesJuego siguientePaso(){
		Jugador jugadorActual=jugadores.get(indiceJugadorActual);
		OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual,estado);
		contabilizarPasosPorSalida(jugadorActual);
		if(operacion==OperacionesJuego.PASAR_TURNO){
			this.pasarTurno();
			this.siguientePasoCompletado(operacion);
		}
		else if(operacion==OperacionesJuego.AVANZAR){
			this.avanzaJugador();
			this.siguientePasoCompletado(operacion);
		}
		contabilizarPasosPorSalida(jugadorActual);
			return operacion;
	}
	
	public void siguientePasoCompletado(OperacionesJuego operacion){
			estado=gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual),estado,operacion);
	}

	public boolean vender(int ip){
		return jugadores.get(indiceJugadorActual).vender(ip);
	}
	

	
}