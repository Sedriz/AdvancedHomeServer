package de.sedriz.smartboot.database.repository;

import de.sedriz.smartboot.database.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Optional<Video> findByNameAndHidden(String name, boolean hidden);
    Optional<Video> findByIdAndHidden(int id, boolean hidden);
    Optional<Video> findByName(String name);
    Page<Video> findAllByHidden(boolean hidden, Pageable pageable);

    Page<Video> findAllByHiddenAndNameContaining(boolean hidden, String name, Pageable pageable);

    Page<Video> findAllByNameContaining(String name, Pageable pageable);

    Page<Video> findAllByPlaylistId(int id, Pageable pageable);

    Page<Video> findAllByPlaylistIdAndHidden(int id, boolean hidden, Pageable pageable);

    boolean existsByIdAndHidden(int id, boolean hidden);
}
