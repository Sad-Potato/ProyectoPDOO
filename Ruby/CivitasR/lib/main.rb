#encoding:utf-8

require_relative "Casilla"
require_relative "TituloPropiedad"
require_relative "Jugador"
require_relative "MazoSorpresas"
require_relative "Tablero"

#MAIN = "casilla"
#MAIN = "dado"
MAIN = "sorpresa"
#MAIN = "tablero"

module Civitas
  case MAIN
  when "casilla"
    
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
    
  when "dado"
    resultado = [0,0,0,0,0,0]
    for i in 1..100
      resultado[Dado.instance.tirar()-1] += 1
    end
    
    puts "Se ha lanzado el dado 100 veces. Los resultados son:"
    for i in 0..5
      puts (i+1).to_s + ":   " + resultado[i].to_s
    end
    
    puts "Se va a intentar salir de la carcel 10 veces"
    for i in 1..10
      puts Dado.instance.salgoDeLaCarcel().to_s
    end
    
  when "sorpresa"
    mazo = MazoSorpresas.new(true)
    tablero = Tablero.new(3)
    for i in 1..10
      tablero.añadeCasilla(Casilla.descanso("Descanso " + i.to_s))
    end
    tablero.añadeJuez
    
    
    
    mazo.alMazo(
      Sorpresa.new4(TipoSorpresa::SALIRCARCEL, mazo)
    )
    
    mazo.alMazo(
      Sorpresa.new1(TipoSorpresa::IRCARCEL, tablero)
    )
    
    mazo.alMazo(
      Sorpresa.new2(TipoSorpresa::IRCASILLA, tablero, 7, "Haces autostop")
    )
    
    mazo.alMazo(
      Sorpresa.new3(TipoSorpresa::PAGARCOBRAR, 5000, "Ganas la lotería")
    )
    
    mazo.alMazo(
      Sorpresa.new4(TipoSorpresa::SALIRCARCEL, mazo)
    )
    
    mazo.alMazo(
      Sorpresa.new3(TipoSorpresa::PORCASAHOTEL, -150, "Reparaciones generales")
    )
    
    mazo.alMazo(
      Sorpresa.new3(TipoSorpresa::PORJUGADOR, 50, "¡Felicidades, es tu cumpleaños!")
    )
    
    jugadores = []
    for i in 1..4
      jugadores << Jugador.new("Jugador " + i.to_s)
    end
    jugadores[0].comprar(TituloPropiedad.new("TEST", 50, 1.1, 500, 300, 60))
    jugadores[0].construirCasa(0);
    jugadores[0].construirCasa(0);
    
    
    for i in 1..6
      mazo.siguiente.aplicarAJugador(0, jugadores)
    end
    
    while Diario.instance.eventos_pendientes
      puts Diario.instance.leer_evento
    end
    
   when "tablero"
     tablero = Tablero.new(5)
     for i in 1..10
      tablero.añadeCasilla(Casilla.descanso("Descanso " + i.to_s))
    end
    tablero.añadeJuez
    
  end
end