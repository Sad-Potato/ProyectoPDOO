require 'singleton'
module Civitas

  class Dado
    include Singleton
    attr_reader :ultimoResultado

    def initialize
      @ultimoResultado = -1
      @debug = false
      @SalidaCarcel = 5
    end

    def tirar
      @ultimoResultado = @debug? 1:rand(1..6)
      return @ultimoResultado
    end

    def salgoDeLaCarcel
      return @SalidaCarcel >= rand(1..6)
    end

    def quienempieza(n)
      return rand(n)
    end

    def setDebug(d)
      @debug = d
      Diario.instance.ocurre_evento("Dado -- modo debug: " + d)
    end


  end
end
