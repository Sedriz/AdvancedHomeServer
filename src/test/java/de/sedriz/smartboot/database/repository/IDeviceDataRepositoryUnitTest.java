package de.sedriz.smartboot.database.repository;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql("/device.sql")
@Sql("/deviceData.sql")
class IDeviceDataRepositoryUnitTest {

    @Autowired
    private IDeviceDataRepository repo;

//    @ParameterizedTest
//    @CsvSource({
//            "1, STATE, 0, 1, 1",
//            "2, STATE, 0, 1, 0",
//            "4, STATE, 0, 10, 1",
//            "1, SENSOR, 0, 1, 0",
//            "7, SENSOR, 0, 1, 1",
//            "1, SENSOR, 0, 10, 0",
//            "7, SENSOR, 0, 10, 3",
//            "1, STATE, 1, 3, 0",
//    })
//    void findDeviceDataByIdAndType(int id, String dataType, int page, int size, int elements) {
//        Pageable pageable = PageRequest.of(page, size);
//        assertThat(repo.findDeviceDataByIdAndType(id, dataType, pageable).get().count()).isEqualTo(elements);
//    }
}