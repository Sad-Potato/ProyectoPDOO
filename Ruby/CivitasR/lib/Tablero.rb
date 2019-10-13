#encoding:utf-8
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Casilla'
require_relative 'Enum'

module Civitas
  class Tablero
    attr_reader:casillas
    
    def initialize(carcel)
      @numCasillaCarcel= carcel >= 1 ? carcel : 1
      @casillas=[Casilla.descanso("Salida")]
      @porSalida=0
      @tieneJuez=false
    end
    
    def correcto(numCasilla = -1)
       return @tieneJuez && @casillas.length > @numCasillaCarcel && numCasilla < @casillas.length
    end
    
    def getCarcel
        return @numCasillaCarcel
    end

    def getPorSalida
      xp=@porSalida
      if(@porSalida>0)
        @porSalida=@porSalida-1
      end
      return xp
    end

    def añadeCasilla(casilla)
      if(@casillas.length==@numCasillaCarcel)
        @casillas.push(Casilla.descanso("Carcel"))
      end
      @casillas.push(casilla)
    end

    def añadeJuez
      if(!@tieneJuez)  
        @casillas.push(Casilla.juez("Juez"))
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
  end
end

