#encoding:utf-8

require_relative "Tablero"
require_relative "Diario"
require_relative "Dado"
require_relative "MazoSorpresas"
require_relative "Jugador"
require_relative "Enum"
require_relative "GestorEstados"

module Civitas
  class CivitasJuego
    
    def initialize(nombres)
      @jugadores = nombres.map { |nombre|  Jugador.new(nombre) }
      @gestorEstados = Gestor_estados.new
      @estado = @gestorEstados.estado_inicial
      @indiceJugadorActual = Dado.instance.quienEmpieza(nombres.size)
      @mazo = MazoSorpresas.new(true)
      inicializaTablero(@mazo)
      inicializaMazoSorpresas(@tablero)
    end

    def inicializaTablero(mazo)
      @tablero = Tablero.new(5)
      revalorizacion = 1.15
      # =>  La casilla del inicio se introduce automáticamente
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Calle Caramelo", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Plaza Galleta", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Sorpresa.new(mazo, "Esquina de las Pizzas"))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Estación de los gofres", 50, revalorizacion, 150, 200, 120)
      ))
      # =>  Aquí meterá tablero a la carcel
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Ruta glaseada", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Rio Cremoso", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Sorpresa.new(mazo, "Puente Hamburguesa"))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Zoo de los dulces", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Descanso.new("Parking Gratuito"))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Cruce Gominolas", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Chocolate Street", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Impuesto.new(300, "Impuesto del Azucar"))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Avenida de las Avellanas", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeJuez
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Banco de monedas de chocolate", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Palacio del dulce de leche", 50, revalorizacion, 150, 200, 120)
      ))
      @tablero.añadeCasilla(Casilla_Sorpresa.new(mazo, "Puente Hamburguesa"))
      @tablero.añadeCasilla(Casilla_Calle.new(
          TituloPropiedad.new("Hotel de los batidos", 50, revalorizacion, 150, 200, 120)
      ))
    end
    private :inicializaTablero

    def inicializaMazoSorpresas(tablero)
      @mazo.alMazo(Sorpresa_IrCarcel.new("Carcel",tablero))
      @mazo.alMazo(Sorpresa_SalirCarcel.new("Carta de salir de la carcel",@mazo))
      @mazo.alMazo(Sorpresa_MoverACasilla.new("Te mudas",tablero,10))
      @mazo.alMazo(Sorpresa_MoverACasilla.new("A la casilla de salida",tablero,1))
      @mazo.alMazo(Sorpresa_MoverACasilla.new("Muevete de casilla",tablero,20))
      @mazo.alMazo(Sorpresa_PorCasaHotel.new("Reparaciones",1))
      @mazo.alMazo(Sorpresa_PorCasaHotel.new("Tuberias oxidadas",1))
      @mazo.alMazo(Sorpresa_PagarCobrar.new("Pagas la comunidad",-100))
      @mazo.alMazo(Sorpresa_PagarCobrar.new("Concurso de belleza",100))
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
			@estado = @gestorEstados.siguiente_estado(@jugadores[@indiceJugadorActual], @estado, operacion)
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

		def salirCarcelPagando
			return @jugadores[@indiceJugadorActual].salirCarcelPagando
		end

		def salirCarcelTirando
			return @jugadores[@indiceJugadorActual].salirCarcelTirando
		end

		def finalDelJuego
			return @jugadores.any? { |jugador| jugador.enBancarrota() }
		end

    # => Ranking se hace público para que pueda ser mostrado si el juego se acaba
		def ranking
			return @jugadores.sort!.reverse!
		end
    
    def getJugadorActual
      return @jugadores[@indiceJugadorActual]
    end
    
    def getCasillaActual
      return @tablero.getCasilla(@jugadores[@indiceJugadorActual].numCasillaActual)
    end
    
    def avanzaJugador
      jugadorActual = @jugadores[@indiceJugadorActual] #1.1
      posicionNueva = @tablero.nuevaPosicion( jugadorActual.numCasillaActual,Dado.instance.tirar )  #1.2, 1.3 y 1.4
      casilla = @tablero.getCasilla(posicionNueva) #1.5
      contabilizarPasosPorSalida(jugadorActual) #1.6
      jugadorActual.moverACasilla(posicionNueva) #1.7
      casilla.recibeJugador(@indiceJugadorActual,@jugadores) #1.8
      contabilizarPasosPorSalida(jugadorActual) #1.9
    end
    private :avanzaJugador

    def siguientePaso
      # 1, 2
      contabilizarPasosPorSalida(@jugadorActual)
      operacion = @gestorEstados.operaciones_permitidas(@jugadores[@indiceJugadorActual],@estado);
      case operacion
      when OperacionesJuego::PASAR_TURNO
        pasarTurno # 3
        siguientePasoCompletado(operacion) # 4
      when OperacionesJuego::AVANZAR
        avanzaJugador # 5
        siguientePasoCompletado(operacion) # 6
      
      end
      contabilizarPasosPorSalida(@jugadorActual)
      return operacion
    end

    def comprar
      jugadorActual = @jugadores[@indiceJugadorActual] #1
      numCasillaActu = jugadorActual.numCasillaActual  #2
      casilla = @tablero.getCasilla(numCasillaActu) #3
      titulo = casilla.titulo #4
      res = jugadorActual.comprar(titulo) #5
      return res
    end

    def infoJugadorTexto
      jugador = @jugadores[ @indiceJugadorActual ]
      s = jugador.toString + "\n" +
        "Propiedades:  \n"
      jugador.nombrePropiedades.each { |p| s += p + '\n' }
      s += "\n" + @tablero.getCasilla( jugador.numCasillaActual ).toString
      return s
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

  end
end
