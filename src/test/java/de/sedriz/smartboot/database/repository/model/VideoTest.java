package de.sedriz.smartboot.database.repository.model;

import de.sedriz.smartboot.database.entity.Video;
import de.sedriz.smartboot.enums.Quality;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class VideoTest {

    @Test
    void test_that_builder_works_with_all_arguments() {
        final Video video = Video.builder()
                .id(1).name("test").length(10.5f).quality((short) Quality.STANDARD_DEFINITION.ordinal())
                .hidden(false).uploadDate(new Date()).location("location").build();
        assertThat(video.getId()).isEqualTo(1);
        assertThat(video.getQuality()).isEqualTo((short) Quality.STANDARD_DEFINITION.ordinal());
        assertThat(video.getLocation()).isEqualTo("location");
    }

    @Test
    void test_that_builder_works_with_required_arguments() {
        final Video video = Video.builder()
                .name("test").location("location").build();
        assertThat(video.getName()).isEqualTo("test");
        assertThat(video.getLocation()).isEqualTo("location");
    }
}