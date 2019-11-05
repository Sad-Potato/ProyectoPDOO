#encoding:utf-8
require_relative "Sorpresa"
require_relative "Diario"

module Civitas
  class MazoSorpresas
    def init
      @barajada=false
      @usadas=0
      @sorpresas=[]
      @cartasEspeciales=[]
      @ultimaSorpresa
    end


    def initialize(n=nil)
      if n != nil
        @debug= n
        Diario.instance.ocurre_evento("Diario -- Se inicializa el mazo con debug = " + n.to_s)
      else
        @debug = false
      end
      init
    end

    def alMazo(s)
      @barajada ? nil : @sorpresas.push(s)
    end

    def siguiente
      if(!@barajadas || @usadas==@sorpresas.length || !@debug)
        @sorpresas.shuffle
        @usadas=0
        @barajada=true
      end
      @usadas+=1
      copia=@sorpresas[0]
      @sorpresas.shift
      @sorpresas.push(copia)
      @ultimaSorpresa=copia
      return @ultimaSorpresa
    end

    def inhabilitarCartaEspecial(s)
      p = false
      if @sorpresas.include?(s)
          p = true
          @sorpresas.delete(s)
          @cartasEspeciales.push(s)
          Diario.instance.ocurre_evento("MazoSorpresas -- Se ha eliminado la carta especial "+ s.toString)
      end
      return p
    end


    def habilitarCartaEspecial(s)
      p = false
      if @cartasEspeciales.include?(s)
          p = true
          @cartasEspeciales.delete(s)
          @sorpresas.push(s)
          Diario.instance.ocurre_evento("MazoSorpresas -- Se ha añadido la carta especial "+ s.toString)
      end
      return p
    end
    private :init
  end
end