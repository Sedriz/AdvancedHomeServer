package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Playlist;
import de.sedriz.smartboot.database.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepo; //save new playlist if video playlist not found!

    public Playlist findById(int id) {
        return playlistRepo.findById(id).orElseThrow(NoResultException::new);
    }
}
