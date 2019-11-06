package civitas;

enum TipoCasilla {
	    CALLE, 
	    SORPRESA, 
	    JUEZ, 
	    IMPUESTO, 
	    DESCANSO
}

enum TipoSorpresa {
    	IRCARCEL,       
    	IRCASILLA,       
    	PAGARCOBRAR,       
    	PORCASAHOTEL,
    	PORJUGADOR, 
    	SALIRCARCEL
}

enum EstadosJuego {
	INICIO_TURNO,
	DESPUES_CARCEL,
	DESPUES_AVANZAR,
	DESPUES_COMPRAR,
	DESPUES_GESTIONAR
}

enum OperacionesJuego{
	AVANZAR,
 	COMPRAR,
    	GESTIONAR,
    	SALIR_CARCEL,
    	PASAR_TURNO
}

enum GestionesInmobiliarias{
	VENDER,
	HIPOTECAR,
	CANCELAR_HIPOTECA,
	CONSTRUIR_CASA,
	CONSTRUIR_HOTEL,
	TERMINAR
}

enum SalidasCarcel{
	PAGANDO,
	TIRANDO
}

enum Respuestas{
	NO,
	SI
}
