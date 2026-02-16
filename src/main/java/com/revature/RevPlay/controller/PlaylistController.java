package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.PlaylistPrivacyUpdateRequest;
import com.revature.RevPlay.dto.request.PlaylistRequest;
import com.revature.RevPlay.dto.response.PlaylistResponse;
import com.revature.RevPlay.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{playlistId}/privacy")
    public ResponseEntity<PlaylistResponse> updatePrivacy(
            @PathVariable Long playlistId,
            @RequestBody PlaylistPrivacyUpdateRequest request

    ) {
        boolean isPublic = request.isPublic();
        return ResponseEntity.ok(
                playlistService.updatePrivacy(playlistId,  isPublic)
        );
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistResponse> addSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok(playlistService.addSongToPlaylist(playlistId, songId));
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<PlaylistResponse> removeSong(
            @PathVariable Long playlistId,
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok(playlistService.removeSongFromPlaylist(playlistId, songId));
    }

    @GetMapping
    public ResponseEntity<Page<PlaylistResponse>> getMyPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(playlistService.getMyPlaylists(page, size, sortBy, direction));
    }

}
