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

        public CivitasJuego(ArrayList <String> nombres){
            jugadores=new ArrayList<>();
            for(int i=0;i<nombres.size();i++){
                jugadores.add(new Jugador(nombres.get(i)));
            }
            gestorEstados=new GestorEstados();
            gestorEstados.estadoInicial();
            indiceJugadorActual=Dado.getInstance().quienEmpieza(nombres.size());
            mazo=new MazoSorpresas();
            inicializarTablero(mazo);
            inicializarMazoSorpresas(tablero);
        }

        public Boolean cancelarHipoteca(int ip){
                return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
        }

        public Boolean comprar(){
            throw new UnsupportedOperationException("No implementado");
        }

        public Boolean construirCasa(int ip){
            return jugadores.get(indiceJugadorActual).construirCasa(ip);
        }

        public Boolean construirHotel(int ip){
            return jugadores.get(indiceJugadorActual).construirHotel(ip);
        }

        private void contabilizarPasosPorSalida(Jugador jugadorActual){
                while(tablero.getPorSalida()>0){
                    jugadorActual.pasaPorSalida();
                }
        }

        public Boolean finalDelJuego(){
            Boolean result=false;
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

        public Boolean hipotecar(int ip){
                return jugadores.get(indiceJugadorActual).hipotecar(ip);
        }

        public String infoJugadorTexto(){
            return jugadores.get(indiceJugadorActual).toString();
        }

        private void inicializarMazoSorpresas(Tablero tablero){
                throw new UnsupportedOperationException("No implementado");
        }

        private void inicializarTablero(MazoSorpresas mazo){
                tablero=new Tablero(15);
        }

        private void pasarTurno(){
                indiceJugadorActual+=1;
                if(indiceJugadorActual==jugadores.size()){
                    indiceJugadorActual-=jugadores.size();
                }
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
                throw new UnsupportedOperationException("No implementado");

        }

        public void siguientePasoCompletado(OperacionesJuego operacion){
                estado=gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual),estado,operacion);
        }

        public Boolean vender(int ip){
               return jugadores.get(indiceJugadorActual).vender(ip);
        }







}