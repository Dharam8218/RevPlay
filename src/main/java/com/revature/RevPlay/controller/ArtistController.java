package com.revature.RevPlay.controller;

import com.revature.RevPlay.Enum.TrendType;
import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.response.*;
import com.revature.RevPlay.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revplay/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PutMapping("/profile")
    public ResponseEntity<ArtistResponse> updateProfile(
            @RequestBody ArtistProfileRequest request) {

        return ResponseEntity.ok(artistService.updateArtistProfile(request));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ArtistDashboardResponse> dashboard() {
        return ResponseEntity.ok(artistService.getDashboard());
    }

    @GetMapping("/songs/{songId}/plays")
    public ResponseEntity<SongPlayCountResponse> getSongPlayCount(@PathVariable Long songId) {
        return ResponseEntity.ok(artistService.getSongPlayCount(songId));
    }

    @GetMapping("/songs/popular")
    public ResponseEntity<List<SongPlayCountResponse>> getPopularSongs() {
        return ResponseEntity.ok(artistService.getPopularSongs());
    }

    @GetMapping("/songs/{songId}/favorited-users")
    public ResponseEntity<List<FavoritedUserResponse>> getFavoritedUsers(
            @PathVariable Long songId
    ) {
        return ResponseEntity.ok(
                artistService.getFavoritedUsers(songId)
        );
    }

    @GetMapping("/analytics/trends")
    public ResponseEntity<List<TrendResponse>> getTrends(
            @RequestParam TrendType type
    ) {
        return ResponseEntity.ok(
                artistService.getListeningTrends(type)
        );
    }

    @GetMapping("/top-listeners")
    public ResponseEntity<Page<TopListenerResponse>> getTopListeners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(artistService.getTopListeners(page, size));
    }
}
