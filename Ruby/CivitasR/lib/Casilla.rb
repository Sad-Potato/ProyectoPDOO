module Civitas
  class Casilla
    @@carcel = 0
    
    def init
      @importe = 0
      @nombre = "Sample Text"
    end
    
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
      return new(TipoCasilla::CALLE, titulo.getNombre(), titulo, nil, nil)
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
        "Casilla -- El jugador " + todos[actual].getNombre() +
        " ha caido en la casilla " + toString()
      )
    end
    private :informe
    
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
        actual[todos].encarcelar
      end
    end
    private :recibeJugador_juez
    
    def toString()
      s = "[Nombre: " + @nombre + "; "
      s += "Tipo: " + @tipo + "]"
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
