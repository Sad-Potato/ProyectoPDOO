package juegoTexto;

import civitas.CivitasJuego;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;

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
            if(ope!=OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                switch(ope){
                    case COMPRAR:
                        vista.comprar(); break;
                    case GESTIONAR:
                        vista.gestionar();
                        int iGest=vista.getGestion();
                        int iProp=vista.getPropiedad();
                        OperacionInmobiliaria operacion=new OperacionInmobiliaria(iGest, iProp);
                        
                        break;
                   
                }
            }
        }
    }
}