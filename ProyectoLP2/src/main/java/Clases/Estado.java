
package Clases;

import java.util.Iterator;

public enum Estado {
    NUEVO("Nuevo"),
    USADO("Usado");

    private final String displayName;

    Estado(String displayName) {
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
                return index < Estado.values().length;
            }

            @Override
            public String next() {
                return Estado.values()[index++].getDisplayName();
            }
        };
    }

    public static Iterator<Estado> iteratorEstado() {
        return new Iterator<Estado>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Estado.values().length;
            }

            @Override
            public Estado next() {
                return Estado.values()[index++];
            }
        };
    }
}
