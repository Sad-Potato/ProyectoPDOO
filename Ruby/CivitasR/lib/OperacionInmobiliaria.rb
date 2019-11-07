#encoding:utf-8

module Civitas
    class OperacionInmobiliaria
        @numPropiedad
        @gestion

        def OperacionInmobiliaria(gest,ip)
            @gestion=gest
            @numPropiedad=ip
        end
        
        attr_reader :numPropiedad,:gestion
    end
end