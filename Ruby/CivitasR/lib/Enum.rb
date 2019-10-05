#encoding : UTF-8

module Civitas
  module Operaciones_juego
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
  
  module Estados_juego
    
		INICIO_TURNO = :inicio_turno  
		DESPUES_CARCEL =:despues_carcel
  	DESPUES_AVANZAR = :despues_avanzar
  	DESPUES_COMPRAR =:despues_comprar
  	DESPUES_GESTIONAR =:despues_gestionar
  end
   
end
