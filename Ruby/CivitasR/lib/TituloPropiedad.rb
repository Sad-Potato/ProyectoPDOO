#encoding:utf-8

module Civitas
	class TituloPropiedad
		@@factorInteresesHipoteca = 1.1

		def initialize(nombre, alquilerBase, factorRevalorizacion, hipotecaBase, precioCompra , precioEdificar)
			@nombre = nombre
			@alquilerBase = alquilerBase
			@factorRevalorizacion = factorRevalorizacion
			@hipotecaBase = hipotecaBase
			@precioCompra = precioCompra
			@precioEdificar = precioEdificar
			@propietario = nil
			@numCasas = 0
			@numHoteles = 0
			@hipotecado = false
		end

		def toString
			s = "Nombre: " + @nombre + "; "
			s += "Precio: " + @precioCompra.to_s + "; "
			s += "Casas: " + @numCasas.to_s + "; "
			s += "Hoteles: " + @numHoteles.to_s + "; "
			s += "Hipotecado: " + @hipotecado.to_s + "; "
			return s
		end

		def getPrecioAlquiler
			return @alquilerBase*(1+(@numCasas*0.5)+(@numHoteles*2.5))
		end

		def getImporteCancelarHipoteca
			return @@factorInteresesHipoteca * @hipotecaBase
		end

		def cancelarHipoteca(jugador)
			b = @hipotecado && esEsteElPropietario(jugador)
			if b
				jugador.paga(getImporteCancelarHipoteca)
				@hipotecado = false
			end
			return b
		end

		def hipotecar(jugador)
			b = !@hipotecado && esEsteElPropietario(jugador)
			if b
				jugador.recibe(@hipotecaBase)
				@hipotecado = true
			end
			return b
		end

		def tramitarAlquiler(jugador)
			b = @propietario != nil && !esEsteElPropietario(jugador)
			if b
				jugador.pagaAlquiler(getPrecioAlquiler)
				@propietario.recibe(getPrecioAlquiler)
			end
		end

		def propietarioEncarcelado
			return @propietario != nil && @propietario.isEncarcelado
		end

		def cantidadCasasHoteles
			return @numCasas + @numHoteles
		end

		def derruirCasas(n, jugador)
			b = esEsteElPropietario(jugador) && @numCasas >= n
			if b
				@numCasas -= n
			end
			return b
		end

		def getPrecioVenta
			return (@precioCompra+@precioEdificar*(@numCasas+@numHoteles))*@factorRevalorizacion
		end

		def construirCasa(jugador)
			b = esEsteElPropietario(jugador)
			if b
				jugador.paga(@precioEdificar)
				@numCasas += 1
			end
			return b
		end

		def construirHotel(jugador)
			b = esEsteElPropietario(jugador)
			if b
				jugador.paga(@precioEdificar*5)
				@numHoteles += 1
			end
			return b
		end

		def comprar(jugador)
			b = @propietario==nil
			if b
				@propietario = jugador
				jugador.paga(@precioCompra)
			end
			return b
		end

	end
end
