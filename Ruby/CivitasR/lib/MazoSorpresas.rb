#encoding:utf-8

module Civitas


  require_relative 'diario'
  require_relative 'Sorpresa'
    class MazoSorpresas
        def init
            @barajada=false
            @usadas=0
            @sorpresas=[]
            @cartasEspeciales=[]
            @ultimaSorpresa
        end


        def initialize(n=false)
            @debug=n
            n ? Diario.instance.ocurre_evento("Se ha activado el modo debug del mazo.") : Diario.instance.ocurre_evento("Se ha desactivado el modo debug del mazo.")
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
          p=false
              for g in @sorpresas do
                if s.nomb==g.nomb
                    p=true
                    @sorpresas.delete(g)
                  end
              end
              if(p)
                @cartasEspeciales.push(s)
              end
                Diario.instance.ocurre_evento("Se ha eliminado la carta especial "+s.to_s)
          
                
        end

        def habilitarCartaEspecial(s)
              p=false
                for g in @cartasEspeciales do
                  if(g.nomb==s.nomb)
                    p=true
                    @cartasEspeciales.delete(g)
                  end
                end
                if(p)
                @sorpresas.push(s)
                end
                    Diario.instance.ocurre_evento("Se ha añadido la carta especial "+s.to_s)
        end
        private :init
        

    end
end