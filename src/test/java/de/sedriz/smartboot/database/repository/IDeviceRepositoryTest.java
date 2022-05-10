package de.sedriz.smartboot.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql("/device.sql")
class IDeviceRepositoryTest {

    @Autowired
    private IDeviceRepository repo;

//    @Test
//    void findDevicesByDeviceType() {
//        assertThat(repo.findByTypeIsReachable(true));
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "true, 5",
//            "false, 6",
//    })
//    void findDevicesByButtonStateChange(boolean isDeviceStateChange, int size) {
//        assertThat(repo.findDevicesByButtonControlled(isDeviceStateChange).size()).isEqualTo(size);
//    }
}