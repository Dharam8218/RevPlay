package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.PlaylistPrivacyUpdateRequest;
import com.revature.RevPlay.dto.request.PlaylistRequest;
import com.revature.RevPlay.dto.request.PlaylistUpdateRequest;
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

    @PutMapping("/{playlistId}")
    public ResponseEntity<PlaylistResponse> updatePlaylist(
            @PathVariable Long playlistId,
            @RequestBody PlaylistUpdateRequest request
    ) {
        return ResponseEntity.ok(playlistService.updatePlaylist(playlistId, request));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deleteMyPlaylist(playlistId);
        return ResponseEntity.ok("Playlist deleted successfully");
    }

    @GetMapping("/public")
    public ResponseEntity<Page<PlaylistResponse>> getPublicPlaylists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(playlistService.getPublicPlaylists(page, size, sortBy, direction));
    }

    @PostMapping("/{playlistId}/follow")
    public ResponseEntity<String> follow(@PathVariable Long playlistId) {
        return ResponseEntity.ok(playlistService.followPlaylist(playlistId));
    }

    @DeleteMapping("/{playlistId}/unfollow")
    public ResponseEntity<String> unfollow(@PathVariable Long playlistId) {
        return ResponseEntity.ok(playlistService.unfollowPlaylist(playlistId));
    }

}
