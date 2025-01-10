package Clases;

import java.util.Iterator;

/**
 *
 * @author Kathy
 */
public enum Ubicacion {
    AZUAY("Azuay"),
    BOLIVAR("Bolivar"),
    CAÑAR("Cañar"),
    CARCHI("Carchi"),
    CHIMBORAZO("Chimborazo"),
    COTOPAXI("Cotopaxi"),
    EL_ORO("El Oro"),
    ESMERALDAS("Esmeraldas"),
    GALAPAGOS("Galápagos"),
    GUAYAS("Guayas"),
    IMBABURA("Imbabura"),
    LOJA("Loja"),
    LOS_RIOS("Los Ríos"),
    MANABI("Manabí"),
    MORONA_SANTIAGO("Morona Santiago"),
    NAPO("Napo"),
    ORELLANA("Orellana"),
    PASTAZA("Pastaza"),
    PICHINCHA("Pichincha"),
    SANTA_ELENA("Santa Elena"),
    SANTO_DOMINGO_DE_LOS_TSACHILAS("Santo Domingo de los Tsáchilas"),
    SUCUMBIOS("Sucumbíos"),
    TUNGURAHUA("Tungurahua"),
    ZAMORA_CHINCHIPE("Zamora Chinchipe");

    private final String displayName;

    Ubicacion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Iterator<String> iterator() {
        return new Iterator<String>() {
            private final Ubicacion[] ubicaciones = Ubicacion.values();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < ubicaciones.length;
            }

            @Override
            public String next() {
                return ubicaciones[index++].getDisplayName();
            }
        };
    }

    public static Iterator<Ubicacion> iteratorUbicacion() {
        return new Iterator<Ubicacion>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Ubicacion.values().length;
            }

            @Override
            public Ubicacion next() {
                return Ubicacion.values()[index++];
            }
        };
    }
}
