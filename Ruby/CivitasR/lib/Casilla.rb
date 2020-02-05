#encoding:utf-8

require_relative "TituloPropiedad"
require_relative "Enum"
require_relative "Diario"
require_relative "Jugador"

module Civitas

  class Casilla_Descanso
    
    def initialize(nombre)
      @nombre = nombre
    end
    
    def informe(actual, todos)
      Diario.instance.ocurre_evento(
        "Casilla -- El jugador " + todos[actual].toString() +
        " ha caido en la casilla " + toString()
      )
    end
    protected :informe

    def recibeJugador(iactual,todos)
      informe(iactual,todos)
    end


    def toString()
      s = @nombre
      return s
    end
    
    def jugadorCorrecto(actual, todos)
      return actual < todos.size()
    end
    
    attr_reader :nombre
  end
  
  
  
  class Casilla_Sorpresa < Casilla_Descanso

    def initialize(mazo, nombre)
      super(nombre)
      @mazo = mazo
    end
    
    def recibeJugador(iactual,todos) 
      if(jugadorCorrecto(iactual,todos))
        sorpresa=@mazo.siguiente() #1
        informe(iactual,todos)
        sorpresa.aplicarAJugador(iactual,todos)
      end
    end

    def toString
      return @nombre + " ( Sorpresa )"
    end
  
  end
  
  class Casilla_Impuesto < Casilla_Descanso
    
    def initialize(cantidad, nombre)
     super(nombre)
      @cantidad = cantidad
    end
    
    def recibeJugador(actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        actual[todos].pagaImpuesto(@importe)
      end
    end

    def toString
      return @nombre + " ( Impuesto, cantidad: " + @cantidad.to_str + " )"
    end
    
    attr_reader :cantidad
    
  end
  
  class Casilla_Juez < Casilla_Descanso
    @@carcel = 0
    
    def initialize(numCasillaCarcel, nombre)
      super(nombre)
      @@carcel = numCasillaCarcel
    end
    
    def recibeJugador_juez(actual, todos)
      if jugadorCorrecto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@@carcel)
      end
    end

    def toString
      return @nombre + " ( juez )"
    end
    
    
  end
  
  
  
  class Casilla_Calle < Casilla_Descanso

    def initialize(titulo)
      super(titulo.nombre)
      @titulo = titulo
    end
    
    def recibeJugador(iactual,todos)
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
    
    def toString
      return @nombre + " ( Propiedad: " + @titulo.toString + " )"
    end
    
    attr_reader :titulo
    
  end
end
