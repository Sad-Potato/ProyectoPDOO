require_relative 'Casilla'
require_relative 'Dado'
require_relative 'diario'
require_relative 'Enum'
require_relative 'MazoSorpresas'
require_relative 'Sorpresa'
require_relative 'Tablero'


module Civitas
    class Test
      def tep
      end
        a=[0,0,0,0]
        for i in 0..100
            a[Dado.instance.quienEmpieza(4)] += 1 
        end
    puts a[0..3] 

    Dado.instance.setDebug(false)
    for i in 0..5 
        puts Dado.instance.tirar
    end
   
    w=Sorpresa.new("Jjajaja")  
    puts w.nomb
      
    
    puts "------->"
    puts Dado.instance.ultimoResultado.to_s + " " +  Dado.instance.salgoDeLaCarcel.to_s
    
    puts Operaciones_juego::AVANZAR 
    puts TipoCasilla::CALLE
    
  
      
      
      

    c=MazoSorpresas.new
    c.alMazo(Sorpresa.new("Wooo"))
    c.alMazo(Sorpresa.new("Tiracooo"))
    c.alMazo(Sorpresa.new("Jejeje"))

    puts c.siguiente.nomb
    c.inhabilitarCartaEspecial(Sorpresa.new("Tiracooo"))
    puts "--------"
    for i in 0..3 do
      puts c.siguiente.nomb
    end
    c.habilitarCartaEspecial(Sorpresa.new("Tiracooo"))
     for i in 0..3 do
      puts c.siguiente.nomb
    end
    
      Dado.instance.setDebug(true)
      puts Diario.instance.eventos_pendientes
     
    test2=Tablero.new(5)
    test2.añadeCasilla(Casilla.new("Tuputamadreeee"))
    test2.añadeCasilla(Casilla.new("segunda"))
    test2.añadeCasilla(Casilla.new("tercera"))
    test2.añadeCasilla(Casilla.new("cuarta"))
    test2.añadeCasilla(Casilla.new("quinta"))
    test2.añadeJuez
    puts test2.nuevaPosicion(0,7).to_s + "------" + test2.calcularTirada(0,0).to_s
    puts test2.inspect











    end
end