#encoding:utf-8

require_relative "TituloPropiedad"
require_relative "Enum"
require_relative "Diario"
require_relative "Jugador"

module Civitas
  class Casilla
    @@carcel = 0
    
    
    def init
      @importe = 0
      @nombre = "Sample Text"
    end
    private :init
    
    def initialize(tipo, nombre, titulo, cantidad, mazo)
      @nombre = nombre
      @titulo = titulo
      @importe = cantidad
      @mazo = mazo
      @tipo = tipo
    end
    
    def self.descanso(nombre)
      return new(TipoCasilla::DESCANSO, nombre, nil, nil, nil)
    end
    
    def self.calle(titulo)
      return new(TipoCasilla::CALLE, "PROPIEDAD", titulo, nil, nil)
    end
    
    def self.impuesto(cantidad, nombre)
      return new(TipoCasilla::IMPUESTO, nombre, nil, cantidad, nil)
    end
    
    def self.juez(numCasillaCarcel, nombre)
      @@carcel = numCasillaCarcel
      return new(TipoCasilla::JUEZ, nombre, nil, nil, nil)
    end
    
    def self.sorpresa(mazo, nombre)
      return new(TipoCasilla::SORPRESA, nombre, nil, nil, mazo)
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento(
        "Casilla -- El jugador " + todos[actual].toString() +
        " ha caido en la casilla " + toString()
      )
    end
    private :informe

    def recibeJugador(iactual,todos)
      case @tipo
        when TipoCasilla::CALLE
          recibeJugador_calle(iactual,todos)
        when TipoCasilla::IMPUESTO
          recibeJugador_impuesto(iactual,todos)
        when TipoCasilla::JUEZ
          recibeJugador_juez(iactual,todos)
        when TipoCasilla::SORPRESA
          recibeJugador_sorpresa(iactual,todos)
        else
          informe(iactual,todos)
        end
    end

    def recibeJugador_sorpresa(iactual,todos) 
      if(jugadorCorrecto(iactual,todos))
        sorpresa=@mazo.siguiente() #1
        informe(iactual,todos)
        sorpresa.aplicarAJugador(iactual,todos)
      end
    end
    private :recibeJugador_sorpresa
    
    def recibeJugador_calle(iactual,todos)
      if(jugadorCorrecto(iactual,todos))
        informe(iactual,todos)
        jugador=todos[iactual]
        if(!@titulo.tienePropietario)
          jugador.puedeComprarCasilla
        else
          @titulo.tramitarAlquiler(jugador)
        end
      end
    end
    private :recibeJugador_calle

    def recibeJugador_impuesto(actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        actual[todos].pagaImpuesto(@importe)
      end
    end
    private :recibeJugador_impuesto
    
    def recibeJugador_juez(actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@@carcel)
      end
    end
    private :recibeJugador_juez
    
    def toString()
      s = (@tipo == TipoCasilla::CALLE ? @titulo.nombre : @nombre) + " (" +
          "Tipo: " + @tipo.to_s + (@importe == nil ? "" : ("Valor: " + @importe.to_s) ) + ")"
      return s
    end
    
    def jugadorCorrecto(actual, todos)
      return actual < todos.size()
    end
    
    def getNombre
      return @nombre
    end
    
    def getTituloPropiedad
      return @titulo
    end
    
  end
end
