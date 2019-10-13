#encoding:utf-8

require_relative 'diario'
require_relative 'Enum'
require_relative 'MazoSorpresas'


module Civitas 
    class Sorpresa
      attr_accessor :nombre


        def initialize(tipo,valor,texto,tablero,mazo)
            @valor = valor
            @tipo = tipo
            @tablero = tablero
            @mazo = mazo
            @valor = valor
            @texto = texto
        end

        def self.new1(tipo,tablero)
          return new(tipo, nil, nil, tablero, nil)
        end

        def self.new2(tipo,tablero,valor,texto)
          return new(tipo, valor, texto, tablero, nil)
        end

        def self.new3(tipo,valor,texto)
          return new(tipo, valor, texto, nil, nil)
        end

        def self.new4(tipo,mazo)
          return new(tipo, nil, nil, nil, mazo)
        end

        def jugadorCorrecto(actual,todos)
          return actual<todos.length
        end

        def informe(actual,todos)
          Diario.instance.ocurre_evento("Sorpresa -- Se aplica una sorpresa al jugador " + todos[actual].getNombre())
        end

        def aplicarAJugador(actual,todos)
            
        end
        
        def aplicarAJugador_irCarcel(actual,todos)
            if(jugadorCorrecto(actual,todos))
              informe(actual,todos)
              todos[actual].encarcelar(@tablero.getCarcel)
            end
        end

        def aplicarAJugador_irACasilla(actual,todos)
            if(jugadorCorrecto(actual,todos))
              informe(actual,todos)
              p=todos[actual].getNumCasillaActual
              todos[actual].moverACasilla(@tablero.nuevaPosicion(p,@tablero.calcularTirada(p,@valor)))
              @tablero.getCasilla.recibeJugador(actual,todos)
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
            todos[actual].modificarSaldo(@valor*todos[actual].getCasasPorHotel)
          end
        end

        def aplicarAJugador_porJugador(actual,todos)
          if(jugadorCorrecto(actual,todos))
            informe(actual,todos)

          end
        end

        def salirDelMazo
            if(@tipo==TipoSorpresa::SALIRCARCEL)
              @mazo.inhabilitarCartaEspecial()
            end
        end

        def aplicarAJugador_salirCarcel(actual,todos)
          if(jugadorCorrecto(actual,todos))
            informe(actual,todos)
            t=false
            for p in todos do 
              if(p.tieneSalvoconducto)
                t=true
              end
            end
            if(!t)
              todos[actual].obtenerSalvoconducto
              salirDelMazo
            end
          end
        end

        def usada
          if(@tipo==TipoSorpresa::SALIRCARCEL)
              @mazo.inhabilitarCartaEspecial()
          end
        end

        def toString
          return @tipo.to_s
        end
        
    end
end