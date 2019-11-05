#encoding:utf-8

MAIN = "casilla"

module Civitas
  case MAIN
  when "casilla"
    require_relative "Casilla"
    require_relative "TituloPropiedad"
    require_relative "Jugador"
    require_relative "MazoSorpresas"
    
    jugadores = [Jugador.new("Test de pruebas"), Jugador.new("Test Comprador")]
    
    Diario.instance.ocurre_evento(puts "\nCasilla de descanso")
    Casilla.descanso("Casilla Descanso").recibeJugador(0, jugadores)
    
    Diario.instance.ocurre_evento("\nCasilla de propiedad")
    titulo = TituloPropiedad.new("Plaza Lavapies", 77, 1.15, 0, 133, 0)
    titulo.comprar(jugadores[1])
    Casilla.calle(titulo).recibeJugador(0, jugadores)
    
    Diario.instance.ocurre_evento("\nCasilla de juez")
    Casilla.juez(3, "Señor juez").recibeJugador(0, jugadores)
    Diario.instance.ocurre_evento("\nJugador sale de la carcel")
    jugadores[0].salirCarcelPagando
    
    Diario.instance.ocurre_evento("\nCasilla de sorpresa")
    mazo = MazoSorpresas.new
    mazo.alMazo(Sorpresa.new3(TipoSorpresa::PAGARCOBRAR, -45.66, "Seguro del coche"))
    Casilla.sorpresa(mazo, "Sorpresa").recibeJugador(0, jugadores)
    
    while Diario.instance.eventos_pendientes
      puts Diario.instance.leer_evento
    end
    
  end
  
  
end