#encoding:utf-8

module Civitas
    require_relative 'diario'
    require_relative 'Dado'

    # Cosas pendientes:
    #   -Generalizar el uso de la clase diario
    #   -Constructores
    #   -Visibilidad de los metodos

    class Jugador

        include Comparable

        @@CasasMax = 4
        @@CasasPorHotel = 4
        @@HotelesMax = 4
        @@PasoPorSalida = 1000.0
        @@PrecioLibertad = 200.0
        @@SaldoInicial = 7500.0


        def initialize(name)
            @nombre=name
            @encarcelado=false
            @salvoconducto=nil
            @puedeComprar=false
            @propiedades=nil
            @numCasillaActual=0
        end

        def self.copia(tjugador)
            jugador = new(tjugador.nombre)
            jugador.encarcelado = tjugador.encarcelado
            jugador.salvoconducto = tjugador.salvoconducto
            jugador.puedeComprar = tjugador.puedeComprar
            jugador.propiedades = tjugador.propiedades
            jugador.numCasillaActual = tjugador.numCasillaActual
            return jugador
        end

        def debeSerEncarcelado
            if(@encarcelado)
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
        
        protected :debeSerEncarcelado

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
            @salvoconducto.usada
            @salvoconducto=nil
        end
        
        private :perderSalvoConducto

        def tieneSalvoconducto 
            @salvoconducto == nil
        end

        def puedeComprarCasilla
            @encarcelado ? @puedeComprar=false : @puedeComprar=true
            return @puedeComprar
        end

        def paga(cantidad)
            return modificarSaldo(-1*cantidad)
        end
        
        def pagaImpuesto(cantidad)
            @encarcelado ? false : paga(cantidad)
        end

        def pagaAlquiler(cantidad)
            @encarcelado ? false : paga(cantidad)
        end

        def recibe(cantidad)
            @encarcelado ? false : modificarSaldo(cantidad)
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
            @encarcelado ? false : @saldo>=precio
        end
        
        private :puedoGastar

        def vender(ip)
            if(@encarcelado)
                return false
            else
                if(existeLaPropiedad)

                end
            end
        end
        
        def comprar(titulo)
          result = false
          if(@encarcelado)
            return result
          end
          if @puedeComprar
            precio = titulo.getPrecioCompra
            if puedoGastar(precio)
              result = titulo.comprar(self)
              if result
                @propiedades << titulo
                Diario.instance.ocurre_evento("El jugador " + @nombre + " compra la propiedad " + titulo.toString);
              end
              @puedeComprar = false
            end
            return result
          end
        end

        def hipotecar(ip)
          result = false
          if @encarcelado
            return result
          end
          if existeLaPropiedad(ip)
            propiedad = @propiedades[ip]
            result = propiedad.hipotecar(self)
          end
          if result
            Diario.instance.ocurre_evento("El jugador " + @jugador.getNombre + " compra la propiedad " + titulo.toString)  
          end
        end
          
        def tieneAlgoQueGestionar
            return getPropiedades.empty?
        end

        def puedeSalirCarcelPagando
            return @saldo>=@@PrecioLibertad
        end
        
        private :puedeSalirCarcelPagando

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
          @saldo <=> otro.saldo
        end



        #   Metodos que no estan en el guion pero si
        #   en el diagrama estructural.

        

        def enBancarrota
            @saldo<0
        end
        
        def existeLaPropiedad(ip)
            @propiedades.length<ip
        end
        
        private :existeLaPropiedad


        def puedeEdificarCasa(propiedad)
            return propiedad.getNumCasas<@@CasasMax
        end
        
        private :puedeEdificarCasa

        def puedeEdificarHotel(propiedad)
            return propiedad.getNumCasas==4
        end
        
        private :puedeEdificarHotel

        def toString
            return "Jugador/ Nombre: " + @nombre + " Saldo: " + @saldo + " Casilla: " + @numCasillaActual
        end
         
        attr_reader :CasasPorHotel,:encarcelado,:puedeComprar,:numCasillaActual
        
        private 
          attr_reader  :HotelesMax,:CasasMax,:numCasillaActual,:PasoPorSalida,:PrecioLibertad
        
        protected
          attr_reader :nombre,:saldo,:propiedades
        

    end
end