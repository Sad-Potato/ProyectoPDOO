#encoding:utf-8

module Civitas
    class OperacionInmobiliaria

        def initialize(gestion,iprop)
            @gestion=gestion
            @numPropiedad=iprop
        end
        
        attr_reader :numPropiedad,:gestion
    end
end