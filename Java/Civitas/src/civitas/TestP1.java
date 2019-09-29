/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.ArrayList;
import java.util.Random;


public class TestP1 {
    public static void main(String args[]){
        /**
         * 1-
         */
        int a;
        ArrayList<Integer> b=new ArrayList<>();
        for(int i=0;i<4;i++){
            b.add(0);
        }
        for(int i=0;i<100;i++){
            a=Dado.getInstance().quienEmpieza(4);
            int c=b.get(a);
            c++;
            b.set(a,c);
        }
        for(int i=0;i<4;i++){
            System.out.println(b.get(i));
        }
        
        /**
         * 2-
         */
        
        for(int i=0;i<5;i++){
            System.out.println(Dado.getInstance().tirar());
        }
        Dado.getInstance().setDebug(true);
        for(int i=0;i<5;i++){
            System.out.println(Dado.getInstance().tirar());
        }
        
        /**
         * 3-
         */
        Dado.getInstance().setDebug(false);
        Dado.getInstance().tirar();
        System.out.println("\n"+Dado.getInstance().getUltimoResultado()+"---------"+Dado.getInstance().salgoDeLaCarcel());
        System.out.println("\n"+TipoCasilla.CALLE+TipoSorpresa.IRCARCEL+Operaciones_juego.AVANZAR+EstadosJuego.DESPUES_AVANZAR);
        /**
         * 4-
         */
        MazoSorpresas baraja=new MazoSorpresas();
        Sorpresa g=new Sorpresa("Tiracoo");
        Sorpresa h=new Sorpresa("Headshot");
        baraja.alMazo(g);
        baraja.alMazo(h);
        System.out.println(baraja.siguiente().getSorpresa());
        System.out.println(baraja.siguiente().getSorpresa());
        baraja.inhabilitarCartaEspecial(g);
        for(int i=0;i<5;i++){
            System.out.println(baraja.siguiente().getSorpresa());
        }
        baraja.inhabilitarCartaEspecial(h);
        baraja.habilitarCartaEspecial(g);
        for(int i=0;i<5;i++){
            System.out.println(baraja.siguiente().getSorpresa());
        }
        
        /**
         * 6-
         */
        Diario.getInstance().ocurreEvento("La vin compae");
        Diario.getInstance().eventosPendientes();
        for(int i=0;i<6;i++){
        System.out.println(Diario.getInstance().eventosPendientes()+Diario.getInstance().leerEvento());
        }
        
        /**
         * 7-
         */
        Tablero test=new Tablero(5);
        ArrayList<Casilla>Prueba=new ArrayList<>();
        for(int i=0;i<7;i++){
            Casilla test4=new Casilla("wooooo");
            Prueba.add(test4);
            test.añadeCasilla(Prueba.get(i));
        }
        test.añadeJuez();
        System.out.println(test.getCarcel()+test.getCasilla(4).getNombre());
        
        
        
        
        System.out.println(test.nuevaPosicion(0,9)+"------"+test.calcularTirada(3,0)+"---------"+Dado.getInstance().tirar());
        
        
        
    }
}
