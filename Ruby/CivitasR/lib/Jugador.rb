module Civitas
    require_relative 'diario'
    require_relative 'Dado'
    class Jugador

        include Comparable

        @@CasasMax=4
        @@CasasPorHotel=4
        @@HotelesMax=4
        @@PasoPorSalida=1000.0
        @@PrecioLibertad=200.0
        @@SaldoInicial=7500.0


        def initialize(name)
            @nombre=name
            @encarcelado=nil
            @salvoconducto=nil
            @puedeComprar=nil
            @propiedades=nil
        end

        def self.copia(p)
            #Puedo preguntarle a jugador (p) por su nombre?
        end

        def debeSerEncarcelado
            if(encarcelado)
                resul=false
            else
                if(tieneSalvoconducto)
                    perderSalvoconducto
                    diario.ocurre_evento("El jugador se libra de ir a la carcel.")
                    resul=false
                else
                    resul=true
                end
            end
            return resul 
        end

        def encarcelar(numCasillaCarcel)
            if(debeSerEncarcelado)
                moverACasilla(numCasillaCarcel)
                @encarcelado=true
                diario.ocurre_evento("El jugador ha sido encarcelado")
            end
            return @encarcelado
        end

        def obtenerSalvoconducto(s)
            if(!@encarcelado)
                @salvoconducto=s
                resul=true
            else
                resul=false
            end
            return resul
        end 

        def perderSalvoConducto
            usada
            @salvoconducto=nil
        end

        def tieneSalvoconducto 
            @salvoconducto==nil ? return true : return false
        end

        def puedeComprarCasilla
            @encarcelado ? @puedeComprar=false : @puedeComprar=true
            return @puedeComprar
        end

        def paga(cantidad)
            return modificarSaldo(-1*cantidad)
        end
        
        def pagaImpuesto(cantidad)
            @encarcelado ? return false : return paga(cantidad)
        end

        def pagaAlquiler(cantidad)
            @encarcelado ? return false : return paga(cantidad)
        end

        def recibe(cantidad)
            @encarcelado ? return false : return modificarSaldo(cantidad)
        end

        def modificarSaldo(cantidad)
            @saldo+=cantidad
            diario.ocurre_evento("Se ha modificado el saldo en " + cantidad.to_s + " euros.")
            return true
        end

        def moverACasilla(numCasilla)
            if(@encarcelado)
                return false
            else
                @numCasillaActual=numCasilla
                @puedeComprar=false
                diario.ocurre_evento("Se ha movido al jugador a la casilla "+@numCasillaActual.to_s)
                return true
            end
        end

        def puedoGastar(precio)
            @encarcelado ? return false : return @saldo>=precio
        end

        def vender(ip)
            if(@encarcelado)
                return false
            else
                if(existeLaPropiedad)

                end
            end
        end

        def tieneAlgoQueGestionar
            return getPropiedades.empty?
        end

        def puedeSalirCarcelPagando
            return @saldo>=@@PrecioLibertad
        end

        def salirCarcelPagando
            if(@encarcelado && puedeSalirCarcelPagando)
                paga(@@PrecioLibertad)
                @encarcelado=false
                diario.ocurre_evento("El jugador deja de estar encarcelado.")
                return true
            else
                return false
            end
        end

        def salirCarcelTirando
            if(Dado.salgoDeLaCarcel)
                @encarcelado=false
                diario.ocurre_evento("El jugador deja de estar encarcelado.")
                return true
            else
                return false
            end
        end

        def pasaPorSalida
            modificarSaldo(@@PasoPorSalida)
            diario.ocurre_evento("El jugador ha pasado por salida.")
            return true
        end 

        def <=> (otro)
            self.@saldo <=> otro.getSaldo
        end



        #   Metodos que no estan en el guion pero si
        #   en el diagrama estructural.

        def self.getCasasMax
            @@CasasMax
        end

        def self.getCasasPorHotel
            @@CasasPorHotel
        end

        def self.getHotelesMax
            @@HotelesMax
        end

        def enBancarrota
            @saldo<0
        return 
        
        def existeLaPropiedad(ip)
            
        end



        private_class_method :getCasasMax :getHotelesMax

    end
    
end