package civitas;

import java.util.ArrayList;


public class CivitasJuego{
        private int indiceJugadorActual;
        private ArrayList<Jugador> jugadores;
        private GestorEstados gestorEstados;
        private MazoSorpresas mazo;
        private Tablero tablero;
        private EstadosJuego estado;

        public CivitasJuego(String nombres){
            jugadores=new ArrayList<>();
            for(int i=0;i<nombres.size();i++){
                jugadores.add(new Jugador(nombres[i]));
            }
            gestorEstados=new GestorEstados();
            gestorEstados.estadoInicial();
            indiceJugadorActual=Dado.getInstance().quienEmpieza(nombres.size());
            mazo=new MazoSorpresas();
            inicializarTablero(mazo);
            inicializarMazoSorpresas(tablero);
        }

        public Boolean cancelarHipoteca(int ip){
                return jugadores[indiceJugadorActual].cancelarHipoteca(ip)
        }

        public Boolean comprar(){

        }

        public Boolean construirCasa(int ip){
            return jugadores[indiceJugadorActual].construirCasa(ip)
        }

        public Boolean construirHotel(int ip){
            return jugadores[indiceJugadorActual].construirHotel(ip)
        }

        private void contabilizarPasosPorSalida(Jugador jugadorActual){
                while(tablero.getPorSalida()>0){
                    jugadorActual.pasaPorSalida();
                }
        }

        public Boolean finalDelJuego(){
            result=false;
            for(int i=0;i<jugadores.size();i++){
                if(jugadores[i].enBancarrota()){
                    result=true;
                }
            }
            return result
        }

        public Casilla getCasillaActual(){
            return tablero.getCasilla(jugadores[indiceJugadorActual].getNumCasillaActual())
        }

        public Jugador getJugadorActual(){
            return jugadores[indiceJugadorActual]
        }

        public Boolean hipotecar(int ip){
                return jugadores[indiceJugadorActual].hipotecar(ip)
        }

        public String infoJugadorTexto(){
            return jugadores[indiceJugadorActual].toString()
        }

        private void inicializarMazoSorpresas(Tablero tablero){
                return 0
        }

        private void inicializarTablero(MazoSorpresas mazo){
                tablero=new Tablero(15)
        }

        private void pasarTurno(){
                indiceJugadorActual+=1;
                if(indiceJugadorActual==jugadores.size()){
                    indiceJugadorActual-=jugadores.size();
                }
        }

        private Jugador ranking(){


        }

        public Boolean salirCarcelPagando(){
            return jugadores[indiceJugadorActual].salirCarcelPagando()
        }

        public Boolean salirCarcelTirando(){
            return jugadores[indiceJugadorActual].salirCarcelTirando()
        }

        public OperacionesJuego siguientePaso(){


        }

        public void siguientePasoCompletado(OperacionesJuego operacion){
                estado=gestorEstados.siguienteEstado(jugadores[indiceJugadorActual],estado,operacion);
        }

        public Boolean vender(int ip){
               return jugadores[indiceJugadorActual].vender(ip)
        }







}