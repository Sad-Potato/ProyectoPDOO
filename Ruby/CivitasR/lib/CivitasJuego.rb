#encoding:utf-8

require_relative "Tablero"
require_relative "Diario"
require_relative "Dado"
require_relative "MazoSorpresas"
require_relative "Jugador"
require_relative "Enum"

module Civitas
  class CivitasJuego
    
    def initialize(nombres)
      @jugadores = nombres.map { |nombre|  Jugador.new(nombre) }
      @gestorEstados = GestorEstados.new()
      @estado = @gestorEstador.estado_inicial
      @indiceJugadorActual = Dado.instance.quienempieza(nombres.size)
      @mazo = MazoSorpresas.new()
      inicializaTablero(@mazo)
      inicializaMazoSorpresas(@tablero)
    end

    def actualizarInfo
      puts @jugadores[@indiceJugadorActual].toString
      puts "Propiedades del jugador: "
      @jugadores[@indiceJugadorActual].getPropiedades.each do |prop|
        puts prop.toString
      end
      puts "Casilla Actual: "
      puts @tablero.getCasilla(@jugadores[@indiceJugadorActual].numCasillaActual).toString
      if @jugadores.any? { |jugador| jugador.enBancarrota() }
        puts "Ranking: "
        for i in 1..@jugadores.size
          puts i.to_s+". "+@jugadores[i].getNombre+" - "+@jugadores[i].getSaldo.to_s+"€ "
        end
      end
    end

    def inicializaTablero(mazo)
      @tablero = Tablero.new(15)
    end
    
    private :inicializarTablero

    def inicializaMazoSorpresas(tablero)
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::IRCARCEL,tablero))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::SALIRCARCEL,@mazo))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::IRCASILLA,tablero,10,"Te muevo de casilla porque puedo."))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::IRCASILLA,tablero,1,"Por gracioso"))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::IRCASILLA,tablero,20,"Evitar la carcel"))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::PORCASAHOTEL,1,"??????"))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::PORCASAHOTEL,1,"?????"))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::PAGARCOBRAR,-100,"A pagar por gracioso"))
      @mazo.alMazo(Sorpresa.new(TipoSorpresa::PAGARCOBRAR,100,"Toma crack"))
    
      
    end
    
    private :inicializaMazoSorpresas

    def contabilizarPasosPorSalida(jugadorActual)
      while @tablero.getPorSalida != 0
        jugadorActual.pasaPorSalida
      end
    end
    
    private :contabilizarPasosPorSalida

		def pasarTurno
			@indiceJugadorActual = (@indiceJugadorActual + 1) % @jugadores.size
		end
    
    private :pasarTurno

		def siguientePasoCompletado(operacion)
			@estado = GestorEstados.siguienteEstado(@jugadores[@indiceJugadorActual], @estado, operacion)
		end

		def construirCasa(ip)
			return @jugadores[@indiceJugadorActual].construirCasa(ip)
		end

		def construirHotel(ip)
			return @jugadores[@indiceJugadorActual].construirHotel(ip)
		end

		def vender(ip)
			return @jugadores[@indiceJugadorActual].vender(ip)
		end

		def hipotecar(ip)
			return @jugadores[@indiceJugadorActual].hipotecar(ip)
		end

		def cancelarHipoteca(ip)
			return @jugadores[@indiceJugadorActual].cancelarHipoteca(ip)
		end

		def salirCarcelPagando(ip)
			return @jugadores[@indiceJugadorActual].salirCarcelPagando(ip)
		end

		def salirCarcelTirando(ip)
			return @jugadores[@indiceJugadorActual].salirCarcelTirando(ip)
		end

		def finalDelJugeo
			return @jugadores.any? { |jugador| jugador.enBancarrota() }
		end

		def ranking
			return @jugadores.sort
		end
    private :ranking
    
    def avanzaJugador
      jugadorActual = @jugadores[@indiceJugadorActual] #1.1
      posicionNueva = @tablero.nuevaPosicion(jugadorActual.getNumCasillaActual,Dado.instance.tirar)  #1.2, 1.3 y 1.4
      casilla = @tablero.getCasilla(posicionNueva) #1.5
      contabilizarPasosPorSalida(jugadorActual) #1.6
      jugadorActual.moverACasilla(posicionNueva) #1.7
      casilla.recibeJugador(@indiceJugadorActual,@jugadores) #1.8
      contabilizarPasosPorSalida(jugadorActual) #1.9
    end

    def siguientePaso
      # 1, 2
      operacion = @gestorEstados.operacionesPermitidas(@jugadores[@indiceJugadorActual],@estado);
      case operacion
        when OperacionesJuego::PASAR_TURNO
          pasarTurno # 3
        when OperacionesJuego::AVANZAR
          avanzaJugador # 5
      end
      siguientePasoCompletado(operacion) # 4, 6
      return operacion
    end
  end
end
