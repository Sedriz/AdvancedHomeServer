package de.sedriz.smartboot.enums;

public enum Quality {
    STANDARD_DEFINITION("SD"),
    HIGH_DEFINITION("HD"),
    FULL_HD("FHD"),
    TWO_K_DEFINITION("2k"),
    ULTRA_HIGH_DEFINITION("UHD"),
    EIGHT_K_DEFINITION("8k");

    private final String name;

    Quality(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
