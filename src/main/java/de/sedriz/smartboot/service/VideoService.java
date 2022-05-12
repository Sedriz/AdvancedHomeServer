package de.sedriz.smartboot.service;

import de.sedriz.smartboot.database.entity.Video;
import de.sedriz.smartboot.database.repository.VideoRepository;
import de.sedriz.smartboot.enums.Quality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepo;
    private final PlaylistService playlistService; //save new playlist if video playlist not found!

    public Video getVideoById(int id, boolean showHidden) {
        Optional<Video> video;
        if (true && showHidden) { //Check if admin
            video = videoRepo.findById(id);
        }
        else {
            video = videoRepo.findByIdAndHidden(id, false);
        }
        return video.orElseThrow(NoResultException::new);
    }

    public Page<Video> getAllVideos(boolean showHidden, short page, short size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Video> videos;

        if (true && showHidden) { //Check if admin
            videos = videoRepo.findAll(pageable);
        }
        else {
            videos = videoRepo.findAllByHidden(false, pageable);
        }
        return videos;
    }

    public Page<Video> getAllHiddenVideos(short page, short size, String sort) { //only admin
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return videoRepo.findAllByHidden(true, pageable);
    }

    public Page<Video> searchVideosByName(String name, boolean showHidden, short page, short size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Video> videos;
        if (true && showHidden) { //Check if admin
            videos = videoRepo.findAllByNameContaining(name, pageable);
        }
        else {
            videos = videoRepo.findAllByHiddenAndNameContaining( false, name, pageable);
        }
        return videos;
    }

    @Transactional
    public Video createNewVideo(String name, String location, Quality quality, boolean isHidden) throws NoPermissionException {
        if (false && isHidden) { //Check if admin
            throw new NoPermissionException("You don't have the permission to save hidden videos");
        }
        final Video video = Video.builder()
                .name(name)
                .location(location)
                .hidden(isHidden)
                .quality((short) quality.ordinal())
                .length(10.2F) //replace with length
                .build();
        return videoRepo.save(video);
    }

    @Transactional
    public Video updateVideo(int id, String name, Quality quality, boolean isHidden) throws NoPermissionException {
        Optional<Video> videoOpt;
        if (true) { //Check if admin
            videoOpt = videoRepo.findById(id);
        }
        else {
            videoOpt = videoRepo.findByIdAndHidden(id, false);

            if (isHidden) {
                throw new NoPermissionException("User are not allowed to save hidden videos!");
            }
        }
        Video video = videoOpt.orElseThrow(NoResultException::new);
        video.setName(name);
        video.setQuality((short) quality.ordinal());
        video.setHidden(isHidden);
        return video;
    }

    @Transactional
    public void deleteVideo(int id) throws NoPermissionException {
        boolean isHidden = videoRepo.existsByIdAndHidden(id, true);
        if (false && isHidden) { //Check if admin
            throw new NoPermissionException("You don't have the permission to save hidden videos");
        }
        videoRepo.deleteById(id);
    }

    public Page<Video> getAllVideosInPlaylist(int id, boolean showHidden, short page, short size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Video> videos;
        if (true && showHidden) { //Check if admin
            videos = videoRepo.findAllByPlaylistId(id, pageable);
        }
        else {
            videos = videoRepo.findAllByPlaylistIdAndHidden(id, false, pageable);
        }
        return videos;
    }

    public Page<Video> getAllHiddenVideosInPlaylist(int id, short page, short size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Video> videos; //todo

        if (true) { //Check if admin
            videos = videoRepo.findAllByPlaylistId(id, pageable);
        }
        else {
            videos = videoRepo.findAllByPlaylistIdAndHidden(id, false, pageable);
        }
        return videos;
    }
}
