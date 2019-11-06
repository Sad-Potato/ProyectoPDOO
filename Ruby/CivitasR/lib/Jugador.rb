#encoding:utf-8

module Civitas
  require_relative 'Diario'
  require_relative 'Dado'

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
      @encarcelado = false
      @salvoconducto = nil
      @puedeComprar = true
      @propiedades = []
      @numCasillaActual = 0
      @saldo = @@SaldoInicial
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

    def cancelarHipoteca(ip)
      result=false
      if @encarcelado
        return result
      end
      if existeLaPropiedad(ip)
        propiedad=@propiedades.get(ip);
        cantidad=propiedad.getImporteCancelarHipoteca
        puedoGastar=puedoGastar(cantidad)
        if puedoGastar
          result=propiedad.cancelarHipoteca(self)
          if result
            Diario.ocurreEvento("El jugador "+@nombre+" cancela la hipoteca de la propiedad "+ip.to_s)
          end
        end
      end
      return result
    end
    private :cancelarHipoteca

    def puedoEdificarHotel(propiedad)
      return puedoGastar(propiedad.getPrecioEdificar) && (propiedad.numHoteles<@@HotelesMax) && (propiedad.numCasas>=@@CasasPorHotel)
    end
    
    def construirHotel(ip)
      result=false
      if @encarcelado
        return result
      end
      if existeLaPropiedad(ip)
        propiedad = @propiedades.at(ip)
        if puedoEdificarHotel(propiedad)
          result=propiedad.construirHotel(self)
          casasPorHotel=@@CasasPorHotel
          propiedad.derruirCasas(casasPorHotel,self)
        end
        Diario.instance.ocurre_evento("El jugador "+ toString + " construye hotel en la propiedad "+ ip.toString)
      end
      return result
    end
    
    def puedoEdificarCasa(propiedad)
      return puedoGastar(propiedad.precioEdificar) && (propiedad.numCasas < @@CasasMax)
    end
    
    def construirCasa(ip)
      result = false
      if @encarcelado
        return result
      end
      if existeLaPropiedad(ip)
        propiedad = @propiedades[ip]
        if puedoEdificarCasa(propiedad)
          result = propiedad.construirCasa(self)
          if result
            Diario.instance.ocurre_evento(
              "El jugador " + toString + " construye casa en la propiedad "+ propiedad.toString
            )
          end
        end
      end
      return result
    end

    def debeSerEncarcelado
      if(@encarcelado)
        resul=false
      else
        if(tieneSalvoconducto)
          perderSalvoconducto
          Diario.instance.ocurre_evento("Jugador -- El jugador " + toString + " se libra de ir a la carcel.")
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
        Diario.instance.ocurre_evento("Jugador -- El jugador " + toString + " ha sido encarcelado")
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

    def perderSalvoconducto
      @salvoconducto.usada
      @salvoconducto=nil
    end
    private :perderSalvoconducto

    def tieneSalvoconducto 
      @salvoconducto != nil
    end

    def puedeComprarCasilla
      @puedeComprar = !@encarcelado
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
      Diario.instance.ocurre_evento("Jugador -- Se ha modificado el saldo de " + toString + " en " + cantidad.to_s + " euros.")
      return true
    end

    def moverACasilla(numCasilla)
      if(@encarcelado)
        return false
      else
        @numCasillaActual=numCasilla
        @puedeComprar=false
        Diario.instance.ocurre_evento("Jugador -- Se ha movido al jugador a la casilla "+@numCasillaActual.to_s)
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
      end
      if(existeLaPropiedad(ip))
        resul=@propiedades.at(ip).vender(self)
        if resul
          @propiedades.delete_at(ip)
          Diario.ocurre_evento("Vendida la propiedad : "+ip.to_s)
          return true
        end
      end
      return false
    end

    def comprar(titulo)
      result = false
      if(@encarcelado)
        return result
      end
      if @puedeComprar
        precio = titulo.precioCompra
        if puedoGastar(precio)
          result = titulo.comprar(self)
          if result
            @propiedades << titulo
            Diario.instance.ocurre_evento("El jugador " + @nombre + " compra la propiedad " + titulo.toString);
          end
          @puedeComprar = false
        end
      end
      return result
    end

    def hipotecar(ip)
      result = false
      if @encarcelado
        return result
      end
      if existeLaPropiedad(ip)
        result = @propiedades[ip].hipotecar(self)
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
        Diario.instance.ocurre_evento("Jugador -- El jugador " + toString + " paga para salir de la carcel.")
        return true
      else
        return false
      end
    end

    def salirCarcelTirando
      if(Dado.salgoDeLaCarcel)
        @encarcelado=false
        Diario.instance.ocurre_evento("El jugador " + toString + " sale de la carcel tirando dados.")
        return true
      else
        return false
      end
    end

    def pasaPorSalida
      modificarSaldo(@@PasoPorSalida)
      Diario.ocurre_evento("El jugador ha pasado por salida.")
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
      @propiedades.size > ip
    end
    private :existeLaPropiedad


    def puedeEdificarCasa(propiedad)
      return propiedad.getNumCasas<@@CasasMax
    end
    private :puedeEdificarCasa

    def puedeEdificarHotel(propiedad)
      return (propiedad.numCasas==4 && propiedad.numHoteles < @@HotelesMax)
    end
    private :puedeEdificarHotel

    def toString
      return @nombre + " (Saldo: " + @saldo.to_s + "; Casilla: " + @numCasillaActual.to_s + 
             "; Propiedades: " + @propiedades.size().to_s + "; Encarcelado: " + @encarcelado.to_s + ")"
    end
    
    def cantidadCasasHoteles
      i = 0
      for p in @propiedades
        i += p.numCasas + p.numHoteles
      end
      return i
    end

    attr_reader :CasasPorHotel,:encarcelado,:puedeComprar,:numCasillaActual

    private 
      attr_reader  :HotelesMax,:CasasMax,:PasoPorSalida,:PrecioLibertad

    protected
      attr_reader :nombre,:saldo,:propiedades


  end
end