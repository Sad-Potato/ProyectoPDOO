# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  
  class Jugador_Especulador < Jugador
    
    @@FactorEspeculador = 2
    @@CasasMax = @@FactorEspeculador * Jugador.CasasMax
    @@HotelesMax = @@FactorEspeculador * Jugador.HotelesMax
    
    def initialize
      @fianza = 0
    end
    
    def self.nuevoEspeculador(jugador,fianza)
      Jugador_Especulador j = Jugador.copia(jugador)
      j.fianza = fianza
      for p in jugador.propiedades
        p.actualizaPropietarioPorConversion(self)
      end
      return j
    end
    
    def pagaImpuesto(cantidad)
      @encarcelado ? false : paga(cantidad/2)
    end
    
    def encarcelar(numCasillaCarcel)
      if(debeSerEncarcelado)
        if(paga(@fianza))
          Diario.instance.ocurre_evento(toString + " ha pagado una fianza para no entrar en la carcel")
        else
          moverACasilla(numCasillaCarcel)
          @encarcelado=true
          Diario.instance.ocurre_evento(toString + " ha sido encarcelado")
        end
      end
      return @encarcelado
    end
    
    def toString
      return @nombre + " ( Jugador Especulador, Saldo: " + @saldo.to_s + ", Casilla: " + @numCasillaActual.to_s + 
             ", Propiedades: " + @propiedades.size().to_s + ", Encarcelado: " + @encarcelado.to_s + " )"
    end
  end
  
  
end
