package Clases;

import java.util.Iterator;

/**
 *
 * @author Kathy
 */
public enum Transmision {
    MANUAL("Manual"),
    AUTOMÁTICA("Automática"),
    CVT("Continuamente Variable"),
    DSG("Doble Embrague"),
    AMT("Manual Automatizada"),
    MANUAL_SECUENCIAL("Manual Secuencial"),
    EVT("Electrónica Variable"),
    HIDRÁULICA("Hidráulica");

    private final String displayName;

    Transmision(String displayName) {
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
                return index < Transmision.values().length;
            }

            @Override
            public String next() {
                return Transmision.values()[index++].getDisplayName();
            }
        };
    }

    public static Iterator<Transmision> iteratorTransmision() {
        return new Iterator<Transmision>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < Transmision.values().length;
            }

            @Override
            public Transmision next() {
                return Transmision.values()[index++];
            }
        };
    }
}
