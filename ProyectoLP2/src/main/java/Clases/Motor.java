package Clases;

import java.util.Iterator;

public enum Motor {
    GASOLINA("Gasolina"),
    DIESEL("Diésel"),
    ELÉCTRICO("Eléctrico"),
    HÍBRIDO("Híbrido"),
    GAS("Gas");

    private final String displayName;

    Motor(String displayName) {
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
                return index < Motor.values().length;
            }

            @Override
            public String next() {
                return Motor.values()[index++].getDisplayName();
            }
        };
    }

    public static Iterator<Motor> iteratorMotor() {
        return new Iterator<Motor>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Motor.values().length;
            }

            @Override
            public Motor next() {
                return Motor.values()[index++];
            }
        };
    }
}
