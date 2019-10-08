require_relative 'diario'


module Civitas 
    class Sorpresa
      attr_accessor :nomb
        def self.new_1(tipo,tablero)
        end

        def self.new_2(tipo,tablero,valor,texto)
        end

        def self.new_3(tipo,valor,texto)
        end

        def self.new_4(tipo,mazo)
        end

        def init
            @valor=1

        end

        def jugadorCorrecto(actual,todos)
          return actual<todos.length
        end

        def informe(actual,todos)
          Diario.instance.ocurre_evento("")
        end

        def aplicarAJugador(actual,todos)
            
        end
        
    end
end