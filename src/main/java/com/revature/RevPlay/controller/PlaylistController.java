package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.PlaylistRequest;
import com.revature.RevPlay.dto.response.PlaylistResponse;
import com.revature.RevPlay.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/revplay/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(
            @RequestBody PlaylistRequest request
    ) {
        return ResponseEntity.ok(
                playlistService.createPlaylist(request)
        );
    }
}
