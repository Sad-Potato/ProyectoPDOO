#encoding:utf-8

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
      puts "JAJA"
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
      jugadorActual = @jugadores[@indiceJugadorActual]
      posicionActual = jugadorActual.getNumCasillaActual
      tirada = Dado.instance.tirar
      posicionNueva = @tablero.nuevaPosicion(posicionActual,tirada)
      casilla = @tablero.getCasilla(posicionNueva)
      contabilizarPasosPorSalida(jugadorActual)
      jugadorActual.moverACasilla(posicionNueva)
      casilla.recibeJugador(@indiceJugadorActual,@jugadores)
      contabilizarPasosPorSalida(jugadorActual)
    end

  end
end
