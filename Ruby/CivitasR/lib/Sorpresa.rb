#encoding:utf-8

require_relative 'Diario'
require_relative 'Enum'
require_relative 'MazoSorpresas'


module Civitas
  
#_________________________Clases Genericas_________________________
  class Sorpresa
    
    def initialize(texto)
      @texto = texto
    end
    
    def jugadorCorrecto(actual,todos)
      return actual<todos.length
    end
    
    def informe(actual,todos)
      Diario.instance.ocurre_evento("Sorpresa -- Se aplica la sorpresa " + toString + " al jugador " + todos[actual].toString())
    end
    
    def toString
      return @texto + " ( " + this.class.name
    end
    
  end
  
  class Sorpresa_Especial < Sorpresa
    
    def initialize(texto,mazo)
      super(texto)
      @mazo = mazo
    end
    
    def salirDelMazo
      @mazo.inhabilitarCartaEspecial(self)
    end

    def usada
      @mazo.habilitarCartaEspecial(self)
    end
    
  end

  #_________________________Sorpresas No Especiales_________________________
  
  class Sorpresa_IrCarcel < Sorpresa
    def initialize(texto,tablero)
      super(texto)
      @tablero = tablero
    end
    
    def aplicarAJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].encarcelar(@tablero.numCasillaCarcel)
      end
    end
    
    def toString
      return super + " )"
    end
    
  end
  
  class Sorpresa_MoverACasilla < Sorpresa
    
    def initialize(texto,tablero,valor)
      super(texto)
      @tablero = tablero
      @valor = valor
    end
    
    def aplicarAJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        p = todos[actual].numCasillaActual
        todos[actual].moverACasilla( @tablero.nuevaPosicion(p,@tablero.calcularTirada(p,@valor) ) )
        @tablero.getCasilla(@valor).recibeJugador(actual,todos)
      end
    end
    
    def toString
      return super + ", casilla: " + @valor.to_s + " )"
    end
    
  end
  
  
  class Sorpresa_PorCasaHotel < Sorpresa
    
    def initialize(texto, valor)
      super(texto)
      @valor = valor
    end
    
    def aplicarAJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].modificarSaldo(@valor*todos[actual].cantidadCasasHoteles)
      end
    end
    
    def toString
      return super + ", valor: " + @valor.to_s + " )"
    end
    
  end
  
  class Sorpresa_PagarCobrar < Sorpresa
    
    def initialize(texto, valor)
      super(texto)
      @valor = valor
    end
    
    def aplicarAJugador_pagarCobrar(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual].modificarSaldo(@valor)
      end
    end
    
    def toString
      return super + ", valor: " + @valor.to_s + " )"
    end
    
  end
  
  class Sorpresa_PorJugador < Sorpresa
    
    def initialize(texto, valor)
      super(texto)
      @valor = valor
    end
    
    def aplicarAJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        for i in 0..todos.size-1 do
          p = Sorpresa_PagarCobrar("aplicarAJugador_porJugador", i==actual ? @valor*(todos.length-1) : @valor*-1)
          p.aplicarAJugador(i,todos)
        end
      end
    end
    
    def toString
      return super + ", valor: " + @valor.to_s + " )"
    end
    
  end
  
  
  class Sorpresa_ConversionEspeculador < Sorpresa
    
    def initialize(texto)
      super(texto)
    end
    
    def aplicarAJugador(actual,todos)
      if(jugadorCorrecto(actual,todos))
        informe(actual,todos)
        todos[actual] = Jugador_Especulador.nuevoEspeculador(todos[actual], 200)
      end
    end
    
    def toString
      return super + ", valor: " + @valor.to_s + " )"
    end
    
  end

  #_________________________Sorpresas Especiales_________________________
  
  class Sorpresa_SalirCarcel < Sorpresa_Especial
    
    def initialize(texto,mazo)
      super(texto,mazo)
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
    
    def toString
      return super + " )"
    end
    
  end
end
  
