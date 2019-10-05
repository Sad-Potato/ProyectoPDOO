# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'Casilla'
require_relative 'Enum'

module Civitas
  class Tablero
    attr_reader:casillas
    
    def initialize(carcel)
      @numCasillaCarcel=carcel>=1?carcel:0
      @casillas=[Casilla.new("Salida")]
      @porSalida=0
      @tieneJuez=false
    end
    
    def correcto
      return @tieneJuez && @casillas.length>@numCasillaCarcel
    end
    
    def correcto1(numCasilla)
       return self.correcto && numCasilla<@casillas.length
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

    def añadeCasilla(casp)
      if(@casillas.length==@numCasillaCarcel)
        @casillas.push(Casilla.new("Carcel"))
      end
      @casillas.push(casp)
    end

    def añadeJuez
      if(!@tieneJuez)  
        @casillas.push(Casilla.new("Juez"))
        @tieneJuez=true
      end
    end

    def getCasilla(numCasilla)
      return correcto1(numCasilla)? @casillas[numCasilla]:nil
    end

    def nuevaPosicion(actual,tirada)
      resultado=actual+tirada
        if(correcto1(actual))
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
      (destino-origen)>0 ? resul=destino-origen : resul+=@casillas.length
      return resul
    end
  
    p1=Tablero.new(2)
    puts p1.casillas[0].nombre + p1.correcto.to_s
    puts p1.getPorSalida().to_s
    p1.añadeCasilla(Casilla.new("Hotel jaja"))
    p1.añadeJuez
  end
end

