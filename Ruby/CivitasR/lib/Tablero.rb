#encoding:utf-8
require_relative 'Casilla'
require_relative 'Enum'
require_relative "MazoSorpresas"

module Civitas
  class Tablero
    
    
    def initialize(carcel)
      @numCasillaCarcel= carcel >= 1 ? carcel : 1
      @casillas=[Casilla_Descanso.new("Salida")]
      @porSalida=0
      @tieneJuez=false
    end
    
    def correcto(numCasilla = -1)
       return @tieneJuez && @casillas.length > @numCasillaCarcel && numCasilla < @casillas.length
    end
    
    private :correcto

    def getPorSalida
      xp=@porSalida
      if(@porSalida>0)
        @porSalida=@porSalida-1
      end
      return xp
    end

    def añadeCasilla(casilla)
      if(@casillas.length==@numCasillaCarcel)
        @casillas.push(Casilla_Descanso.new("Carcel"))
      end
      @casillas.push(casilla)
      if(@casillas.length==@numCasillaCarcel)
        @casillas.push(Casilla_Descanso.new("Carcel"))
      end
    end

    def añadeJuez
      if(!@tieneJuez)  
        añadeCasilla(Casilla_Juez.new(@numCasillaCarcel,"Juez"))
        @tieneJuez=true
      end
    end

    def getCasilla(numCasilla)
      return correcto(numCasilla) ? @casillas[numCasilla] : nil
    end

    def nuevaPosicion(actual,tirada)
      resultado=actual+tirada
      if(correcto(actual))
        if((actual+tirada)>=@casillas.length)
          resultado%=@casillas.length
          @porSalida+=1
        end
      else
        resultado=-1
      end
      return resultado
    end

    def calcularTirada(origen,destino)
      (destino-origen)>0 ? resul=destino-origen : resul=@casillas.length+(destino-origen)
      return resul
    end
    
    attr_reader :casillas,:numCasillaCarcel
    
  end
end

