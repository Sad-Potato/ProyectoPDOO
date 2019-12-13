package juegoTexto;

import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.Respuestas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla_Descanso;
import civitas.Jugador;
import civitas.TituloPropiedad;

public class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
  VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("*****************************************************\n" +
                       "*                                                   *\n" +
                       "*         ¿Como desea salir de la carcel?           *\n" +
                       "*                                                   *\n" +
                       "*****************************************************",
                      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }


  Respuestas comprar() {
    int opcion = menu("*****************************************************\n" +
                       "*                                                   *\n" +
                       "*         ¿Desea comprar la propiedad?              *\n" +
                       "*                                                   *\n" +
                       "*****************************************************",
                      new ArrayList<> (Arrays.asList("No","Si")));
    return (Respuestas.values()[opcion]);
  }

  void gestionar () {
    iGestion = menu("*****************************************************\n" +
                    "*                                                   *\n" +
                    "*          ¿Que gestion desea realizar?             *\n" +
                    "*                                                   *\n" +
                    "*****************************************************"
                    ,new ArrayList<> 
    (Arrays.asList("Vender","Hipotecar","Cancelar la hipoteca"
                  ,"Construir una casa","Construir un hotel","Terminar")));
    if(iGestion<5){
        ArrayList <String> propiedades=new ArrayList<>();
        for(int i=0;i<juegoModel.getJugadorActual().getPropiedades().size();i++){
            propiedades.add(juegoModel.getJugadorActual().getPropiedades().get(i).toString());
        }
        iPropiedad= menu("*****************************************************\n" +
                         "*                                                   *\n" +
                         "*              ¿Sobre que propiedad?                *\n" +
                         "*                                                   *\n" +
                         "*****************************************************"
                         ,propiedades); //Fix this.
    }
    
    }
  
  public int getGestion(){
    return iGestion;
  }
  
  public int getPropiedad(){
    return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
    System.out.println("Siguiente operacion: " + operacion);
  }


  void mostrarEventos() {
    while(Diario.getInstance().eventosPendientes()){
      System.out.println("\n" + Diario.getInstance().leerEvento() + "\n");
    }
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();

    }
  
  void actualizarVista(){
    System.out.println(juegoModel.getCasillaActual().toString());
    System.out.println(juegoModel.getJugadorActual().toString());
    System.out.println(juegoModel.getJugadorActual().toStringPropiedades());
  } 

}
