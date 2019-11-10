
require_relative "VistaTextual"
require_relative "CivitasJuego"
require_relative "OperacionInmobiliaria"

module Civitas
  class Controlador
    def initialize(juego, vista)
      @juego = juego
      @vista = vista
    end
    
    def juega
      @vista.setCivitasJuego(@juego)
      while !@juego.finalDelJuego
        @vista.actualizarVista
        @vista.pausa
        p = @juego.siguientePaso
        @vista.mostrarSiguienteOperacion( p )
        if p != OperacionesJuego::PASAR_TURNO
          @vista.mostrarEventos
        end
        if !@juego.finalDelJuego
          case p
            
            
          when OperacionesJuego::COMPRAR
            if @vista.comprar == Respuestas::SI
              @juego.comprar
            end
            @juego.siguientePasoCompletado( p )
          
            
          when OperacionesJuego::GESTIONAR
            @vista.gestionar
            g = OperacionInmobiliaria.new(@vista.gestion, @vista.propiedad)
            
            case g.gestion
            when GestionesInmobiliarias::VENDER
              @juego.vender(g.numPropiedad)
            when GestionesInmobiliarias::HIPOTECAR
              @juego.hipotecar(g.numPropiedad)
            when GestionesInmobiliarias::CANCELAR_HIPOTECA
              @juego.cancelarHipoteca(g.numPropiedad)
            when GestionesInmobiliarias::CONSTRUIR_CASA
              @juego.construirCasa(g.numPropiedad)
            when GestionesInmobiliarias::CONSTRUIR_HOTEL
              @juego.construirHotel(g.numPropiedad)
            when GestionesInmobiliarias::TERMINAR
              @juego.siguientePasoCompletado( p )
            end
            
            
          when OperacionesJuego::SALIR_CARCEL
            case @vista.salirCarcel
            when SalidasCarcel::PAGANDO
              @juego.salirCarcelPagando
            when SalidasCarcel::TIRANDO
              @juego.salirCarcelTirando
            end
            @juego.siguientePasoCompletado( p )
            
            
            
          end
        end
      end
      
      i = 0 
      puts "FIN DEL JUEGO"
      puts "RANKING FINAL: "
      @juego.ranking.each { |jugador| i += 1; puts i.to_s + "º: " + jugador.toString}
    end
  end
end