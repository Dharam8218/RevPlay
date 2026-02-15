package com.revature.RevPlay.service;

import com.revature.RevPlay.dto.request.PlaylistRequest;
import com.revature.RevPlay.dto.response.PlaylistResponse;
import com.revature.RevPlay.exception.UserNotFoundException;
import com.revature.RevPlay.model.Playlist;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.PlaylistRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.PlaylistTransformer;
import com.revature.RevPlay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

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
}

