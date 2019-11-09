package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.GestionesInmobiliarias;
import civitas.Jugador;
import civitas.Respuestas;
import java.util.ArrayList;

public class Controlador{
    private CivitasJuego juego;
    private VistaTextual vista;

    Controlador(CivitasJuego juego, VistaTextual vista){
        this.juego=juego;
        this.vista=vista;
    }

    void juega(){
        vista.setCivitasJuego(juego);
        while(!juego.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego ope=juego.siguientePaso();
            vista.mostrarSiguienteOperacion(ope);
            if(ope!=OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                switch(ope){
                    case COMPRAR:
                        vista.comprar();
                        if(vista.comprar() == Respuestas.SI){
                            juego.comprar();
                        }
                        juego.siguientePasoCompletado(ope);
                    break;
                    case GESTIONAR:
                        vista.gestionar();
                        int iGest=vista.getGestion();
                        int iProp=vista.getPropiedad();
                        OperacionInmobiliaria operacion=new OperacionInmobiliaria(GestionesInmobiliarias.values()[iGest], iProp);
                        switch(operacion.getGestion()){
                            case VENDER:
                                juego.vender(operacion.getNumPropiedad()); break;
                            case HIPOTECAR:
                                juego.hipotecar(operacion.getNumPropiedad()); break;
                            case CANCELAR_HIPOTECA:
                                juego.cancelarHipoteca(operacion.getNumPropiedad()); break;
                            case CONSTRUIR_CASA:
                                juego.construirCasa(operacion.getNumPropiedad()); break;
                            case CONSTRUIR_HOTEL:
                                juego.construirHotel(operacion.getNumPropiedad()); break;
                            case TERMINAR:
                                juego.siguientePasoCompletado(ope); break;
                        }
                    break;
                    case SALIR_CARCEL:
                        switch(vista.salirCarcel()){
                            case PAGANDO:
                                juego.salirCarcelPagando(); break;
                            case TIRANDO:
                                juego.salirCarcelTirando(); break;
                        }
                        juego.siguientePasoCompletado(ope);
                    break;
                   
                }
            }
        }
        ArrayList <Jugador> ranking=juego.ranking();
        for(int i=0;i<ranking.size();i++){
            System.out.println("En " + (i+1) + "ª posición: " + ranking.get(i) + "\n");
        }

    }
}