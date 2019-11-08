#encoding:utf-8
require_relative 'Enum'
require 'io/console'

module Civitas

  class Vista_textual
    
    attr_reader :iGestion, :iPropiedad

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
      puts "*****************************************************"
      puts "*                                                   *"
      puts "*         ¿Desea comprar la propiedad?              *"
      puts "*                                                   *"
      puts "*****************************************************"
      
      return lista_Respuestas[ menu(
              "\n¿Comprar?",
              ["No","Si"]
      ) ]
    end
    
    def salirCarcel
      return lista_SalidasCarcel[ menu(
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
      @iGestion = lista_GestionesInmobiliarias[ menu(
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
    
      if @iGestion != GestionesInmobiliarias::TERMINAR
        @iPropiedad = @juegoModel.getJugadorActual.propiedades[ menu(
        "*****************************************************\n" +
        "*                                                   *\n" +
        "*               ¿Sobre qué propiedad?               *\n" +
        "*                                                   *\n" +
        "*****************************************************\n",
        @juegoModel.getJugadorActual.propiedades.map { |prop| prop.toString }
      ) ]
      end
    end

    def mostrarSiguienteOperacion(operacion)
      puts "Siguiente operacion: " + operacion.toString + "\n"
    end

    def mostrarEventos
      while Diario.instance.eventos_pendientes
        puts Diario.instance.leer_evento
      end
    end

    def setCivitasJuego(civitas)
         @juegoModel=civitas
         actualizarVista
    end

    def actualizarVista
      puts @juegoModel.infoJugadorTexto
    end

    
  end

end
