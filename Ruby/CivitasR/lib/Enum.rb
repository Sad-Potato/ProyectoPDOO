#encoding:utf-8

module Civitas
  module OperacionesJuego
    AVANZAR=:avanzar
    COMPRAR=:comprar
    GESTIONAR=:gestionar
    SALIR_CARCEL=:salir_carcel
    PASAR_TURNO=:pasar_turno
  end
  
  module TipoCasilla
    CALLE=:calle
    SORPRESA=:sorpresa
    JUEZ=:juez
    IMPUESTO=:impuesto
    DESCANSO=:descanso
  end
  
  module TipoSorpresa
    IRCARCEL=:ircarcel
    IRCASILLA=:ircasilla
    PAGARCOBRAR=:pagarcobrar
    PORCASAHOTEL=:porcasahotel
    PORJUGADOR=:porjugador
    SALIRCARCEL=:salircarcel
  end
  
  module EstadosJuego
    
		INICIO_TURNO = :inicio_turno  
		DESPUES_CARCEL =:despues_carcel
  	DESPUES_AVANZAR = :despues_avanzar
  	DESPUES_COMPRAR =:despues_comprar
  	DESPUES_GESTIONAR =:despues_gestionar
  end

	module GestionesInmobiliarias
		VENDER=:vender
		HIPOTECAR=:hipotecar
		CANCELAR_HIPOTECA=:cancelar_hipoteca
		CONSTRUIR_CASA=:construir_casa
		CONSTRUIR_HOTEL=:construir_hotel
		TERMINAR=:terminar
	end

	module SalidasCarcel
		PAGANDO=:pagando
		TIRANDO=:tirando
	end

	module Respuestas
		NO=:no
		SI=:si
	end

	lista_GestionesInmobiliarias=[
			GestionesInmobiliarias::VENDER,
			GestionesInmobiliarias::HIPOTECAR,
			GestionesInmobiliarias::CANCELAR_HIPOTECA,
			GestionesInmobiliarias::CONSTRUIR_CASA,
			GestionesInmobiliarias::CONSTRUIR_HOTEL,
			GestionesInmobiliarias::TERMINAR
	]	






   
end
