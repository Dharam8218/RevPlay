package com.revature.RevPlay.service;

import com.revature.RevPlay.dto.request.PlaylistRequest;
import com.revature.RevPlay.dto.response.PlaylistResponse;
import com.revature.RevPlay.exception.PlaylistNotFoundException;
import com.revature.RevPlay.exception.SongNotFoundException;
import com.revature.RevPlay.exception.UserNotFoundException;
import com.revature.RevPlay.model.Playlist;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.PlaylistRepository;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.PlaylistTransformer;
import com.revature.RevPlay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public PlaylistResponse createPlaylist(PlaylistRequest playlistRequest) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (playlistRequest.getName() == null || playlistRequest.getName().trim().isEmpty()) {
            throw new RuntimeException("Playlist name is required");
        }

        Playlist playlist = PlaylistTransformer.playlistRequestToPlaylist(playlistRequest,user);

        Playlist saved = playlistRepository.save(playlist);

        return PlaylistTransformer.playlistToPlaylistResponse(saved);
    }

    @Transactional
    public PlaylistResponse updatePrivacy(Long playlistId, boolean isPublic) {
        String username = SecurityUtils.getCurrentUsername();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found"));

        if (playlist.getUser() == null || !playlist.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to update this playlist");
        }
        playlist.setPublic(isPublic);

        return PlaylistTransformer.playlistToPlaylistResponse(playlist);
    }

    public PlaylistResponse addSongToPlaylist(Long playlistId, Long songId) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );

        Playlist playlist = playlistRepository.findByIdAndUser_Id(playlistId, user.getId())
                .orElseThrow(() -> new RuntimeException("Playlist not found or not owned by user"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));

        playlist.getSongs().add(song);
        Playlist saved = playlistRepository.save(playlist);

        return PlaylistTransformer.playlistToPlaylistResponse(saved);
    }

    public PlaylistResponse removeSongFromPlaylist(Long playlistId, Long songId) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Playlist playlist = playlistRepository.findByIdAndUser_Id(playlistId, user.getId())
                .orElseThrow(() -> new RuntimeException("Playlist not found or not owned by user"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));

        playlist.getSongs().remove(song);
        Playlist saved = playlistRepository.save(playlist);

        return PlaylistTransformer.playlistToPlaylistResponse(saved);
    }

    public Page<PlaylistResponse> getMyPlaylists(int page, int size, String sortBy, String direction) {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return playlistRepository.findByUserId(user.getId(), pageable)
                .map(PlaylistTransformer::playlistToPlaylistResponse);
    }

}

