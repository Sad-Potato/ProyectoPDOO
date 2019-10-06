require 'singleton'
require_relative 'diario'


module Civitas
    class Dado
        include Singleton
        attr_reader :ultimoResultado
    
    def tirar
        @debug ? @random=1 : @random=(rand(6)+1)
        @ultimoResultado=@random
        return @random
    end

    def salgoDeLaCarcel
        self.tirar()==5 ? cond=true : cond=false
        return cond
    end

    def quienEmpieza(n)
        return rand(n)
    end

    def setDebug(d)
        @debug=d
        d ? st="Dado en modo debug" : st="Dado en modo no debug"
        Diario.instance.ocurre_evento(st)
    end

    private
        def initialize
            @random
            @ultimoResultado
            @debug=false
            @@SalidaCarcel=5
        end
    end
end