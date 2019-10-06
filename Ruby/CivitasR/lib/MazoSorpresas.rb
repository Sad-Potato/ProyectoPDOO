require_relative 'diario'


module Civitas
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
                if(@sorpresas.include?(s))
                    @sorpresas.delete(s)
                    @cartasEspeciales.push(s)
                    Diario.instance.ocurre_evento("Se ha eliminado la carta especial "+s.to_s)
                end
        end

        def habilitarCartaEspecial(s)
                if(@cartasEspeciales.include?(s))
                    @cartasEspeciales.delete(s)
                    @sorpresas.push(s)
                    Diario.instance.ocurre_evento("Se ha añadido la carta especial "+s.to_s)
                end
        end
        private :init
        

    end
end