#encoding:utf-8

require_relative 'Diario'
require_relative 'Enum'
require_relative 'MazoSorpresas'


module Civitas 
  class Sorpresa
    def initialize(tipo,valor,texto,tablero,mazo)
      @valor = valor
      @tipo = tipo
      @tablero = tablero
      @mazo = mazo
      @valor = valor
      @texto = texto
    end

    def self.new1(tipo,tablero)
      return new(tipo, -1, nil, tablero, nil)
    end

    def self.new2(tipo,tablero,valor,texto)
      return new(tipo, valor, texto, tablero, nil)
    end

    def self.new3(tipo,valor,texto)
      return new(tipo, valor, texto, nil, nil)
    end

    def self.new4(tipo,mazo)
      return new(tipo, -1, nil, nil, mazo)
    end

    def jugadorCorrecto(actual,todos)
      return actual<todos.length
    end

    def informe(actual,todos)
      Diario.instance.ocurre_evento("Sorpresa -- Se aplica la sorpresa " + toString + " al jugador " + todos[actual].toString())
    end

    def aplicarAJugador(actual,todos)
      case @tipo
        when TipoSorpresa::IRCARCEL
             aplicarAJugador_irCarcel(actual,todos)
        when TipoSorpresa::IRCASILLA
             aplicarAJugador_irACasilla(actual,todos)
        when TipoSorpresa::PAGARCOBRAR
             aplicarAJugador_pagarCobrar(actual,todos)
        when TipoSorpresa::PORCASAHOTEL
             aplicarAJugador_porCasaHotel(actual,todos)
        when TipoSorpresa::PORJUGADOR
             aplicarAJugador_porJugador(actual,todos)
        when TipoSorpresa::SALIRCARCEL
             aplicarAJugador_salirCarcel(actual,todos)
      end

    end

    def salirDelMazo
      if(@tipo==TipoSorpresa::SALIRCARCEL)
        @mazo.inhabilitarCartaEspecial(self)
      end
    end


    def usada
      if(@tipo==TipoSorpresa::SALIRCARCEL)
          @mazo.inhabilitarCartaEspecial(self)
      end
    end

    def toString
      return @nombre.to_s + ":[Tipo: " + @tipo.to_s + "]"
    end

    private

    def aplicarAJugador_irCarcel(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end

    def aplicarAJugador_irACasilla(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        p = todos[actual].numCasillaActual
        todos[actual].moverACasilla( @tablero.nuevaPosicion(p,@tablero.calcularTirada(p,@valor) ) )
        @tablero.getCasilla(@valor).recibeJugador(actual,todos)
      end
    end

    def aplicarAJugador_pagarCobrar(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].modificarSaldo(@valor)
      end
    end

    def aplicarAJugador_porCasaHotel(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].modificarSaldo(@valor*todos[actual].cantidadCasasHoteles)
      end
    end

    def aplicarAJugador_porJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        for i in 0..todos.size-1 do
          p = Sorpresa.new3(TipoSorpresa::PAGARCOBRAR,i==actual ? @valor*(todos.length-1) : @valor*-1,"aplicarAJugador_porJugador")
          p.aplicarAJugador(i,todos)
        end
      end
    end

    def aplicarAJugador_salirCarcel(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        if(!todos.any? { |jugador| jugador.tieneSalvoconducto })
          todos[actual].obtenerSalvoconducto(self)
          salirDelMazo
        end
      end
    end

  end
end