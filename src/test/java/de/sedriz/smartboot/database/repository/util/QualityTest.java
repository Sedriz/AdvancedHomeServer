package de.sedriz.smartboot.database.repository.util;

import org.junit.jupiter.api.Test;

class QualityTest {

    @Test
    void testToString() {
        assertThat(Quality.STANDARD_DEFINITION.toString()).isEqualTo("SD");
        assertThat(Quality.ULTRA_HIGH_DEFINITION.toString()).isEqualTo("UHD");
    }

    @Test
    void testValues() {
        assertThat(Quality.values().length).isEqualTo(6);
        assertThat(Quality.values()[0].toString()).isEqualTo("SD");
    }
}