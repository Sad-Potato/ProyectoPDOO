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

    puts "------->"
    puts Dado.instance.ultimoResultado.to_s + " " +  Dado.instance.salgoDeLaCarcel.to_s
    
    puts Operaciones_juego::AVANZAR 
    puts TipoCasilla::CALLE

    c=MazoSorpresas.new
    c.alMazo("Woooo")
    c.alMazo("Tiracooo")
    puts c.siguiente
     














    end
end