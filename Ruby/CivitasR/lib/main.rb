#encoding:utf-8

require_relative "Dado"
require_relative "Enum"
require_relative "MazoSorpresas"
require_relative "diario"
require_relative "Tablero"
require_relative "Jugador"

module Civitas

  jugadores = 4
  
  testPosibilidades = Array.new(jugadores, 0)
  
  for i in 1..100
    testPosibilidades[Dado.instance.quienempieza(jugadores)] += 1
  end
  
  puts "Test de quienempieza de Dado, " + jugadores.to_s + " jugadores\n"
  
  for i in 1..jugadores
    puts i.to_s + ", " + testPosibilidades[i-1].to_s
  end
  
  puts "Lanzamiento de dado: " + Dado.instance.tirar.to_s
  puts "Ultimo lanzamiento: " + Dado.instance.ultimoResultado.to_s  
  
  puts "\nTipos de enum:"
  puts Estados_juego::DESPUES_AVANZAR
  puts TipoCasilla::CALLE
  puts TipoSorpresa::PORJUGADOR
  puts Operaciones_juego::PASAR_TURNO
  
  mazo = MazoSorpresas.new(false)
  
  s = [Sorpresa.new("Vas a suspender"), Sorpresa.new("No vas a suspender")]
  for i in s
    mazo.alMazo(i)
  end
  
  puts mazo.siguiente.nombre
  
  mazo.inhabilitarCartaEspecial(s[1])
  mazo.habilitarCartaEspecial(s[1])
  
  puts "\nEventos del diario: "
  
  while Diario.instance.eventos_pendientes
    puts Diario.instance.leer_evento
  end
  
end