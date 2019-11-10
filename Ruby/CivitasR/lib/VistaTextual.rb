#encoding:utf-8
require_relative 'Enum'
require 'io/console'

module Civitas

  class VistaTextual
    
    attr_reader :gestion, :propiedad

    def mostrar_estado(estado)
      puts estado
    end

    
    def pausa
      print "Pulsa una tecla"
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end



    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    
    def comprar
      return Lista_Respuestas[ menu(
      "*****************************************************\n" +
      "*                                                   *\n" +
      "*         ¿Desea comprar la propiedad?              *\n" +
      "*                                                   *\n" +
      "*****************************************************\n" ,
        ["No","Si"]
      ) ]
    end
    
    def salirCarcel
      return Lista_SalidasCarcel[ menu(
        "*****************************************************\n" +
        "*                                                   *\n" +
        "*         ¿Cómo quieres salir de la carcel?         *\n" +
        "*                                                   *\n" +
        "*****************************************************\n",
          ["Pagar",
           "Tirar dados"]
      ) ]
    end

    def gestionar
      @gestion = Lista_GestionesInmobiliarias[ menu(
        "*****************************************************\n" +
        "*                                                   *\n" +
        "*          ¿Qué gestión quiere realizar?            *\n" +
        "*                                                   *\n" +
        "*****************************************************\n",
          ["Vender",
           "Hipotecar",
           "Cancelar hipoteca",
           "Construir casa",
           "Construir hotel",
           "Terminar"]
      ) ]
    
      if @gestion != GestionesInmobiliarias::TERMINAR
        @propiedad = menu(
        "*****************************************************\n" +
        "*                                                   *\n" +
        "*               ¿Sobre qué propiedad?               *\n" +
        "*                                                   *\n" +
        "*****************************************************\n",
        @juegoModel.getJugadorActual.nombrePropiedades
      )
      end
    end

    def mostrarSiguienteOperacion(operacion)
      puts "Siguiente operacion: " + operacion.to_s + "\n"
    end

    def mostrarEventos
      puts "\tDIARIO:"
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
      puts "\tFin del diario"
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         actualizarVista
    end

    def actualizarVista
      puts "\n-----------------------------------------"
      puts "Jugador actual: "
      j = @juegoModel.getJugadorActual
      puts j.toString
      l = j.nombrePropiedades
      print "Propiedades:"
      if l.size == 0
        puts " ninguna"
      else
        puts ""
        l.each { |prop| puts prop }
      end
      puts "\nCasilla en la que se ha caido:"
      puts @juegoModel.getCasillaActual.toString
    end

    
  end

end
