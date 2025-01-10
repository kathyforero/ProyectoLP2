package Clases;

import java.util.Iterator;

public enum Tipo {
    SEDAN("Sedán"),
    COUPE("Coupé"),
    CONVERTIBLE("Convertible"),
    HATCHBACK("Hatch-Back"),
    SUV("SUV"),
    PICK_UP("Pick-Up"),
    HIBRIDO("Híbrido"),
    LIMOSINA("Limosina"),
    ELECTRICO("Eléctrico");

    private final String displayName;

    Tipo(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Tipo.values().length;
            }

            @Override
            public String next() {
                return Tipo.values()[index++].getDisplayName();
            }
        };
    }

    public static Iterator<Tipo> iteratorTipo() {
        return new Iterator<Tipo>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Tipo.values().length;
            }

            @Override
            public Tipo next() {
                return Tipo.values()[index++];
            }
        };
    }

}
