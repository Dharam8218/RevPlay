package com.revature.RevPlay.controller;

import com.revature.RevPlay.Enum.TrendType;
import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.response.*;
import com.revature.RevPlay.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/revplay/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PutMapping(value = "/profile",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ArtistResponse> updateArtistProfile(

            @RequestParam("artist") String data,

            @RequestParam(value = "profilePicture", required = false)
            MultipartFile profilePicture,

            @RequestParam(value = "bannerImage", required = false)
            MultipartFile bannerImage
    ) {
        ArtistProfileRequest request = new ObjectMapper().readValue(data, ArtistProfileRequest.class);
        return ResponseEntity.ok(
                artistService.updateArtistProfile(request, profilePicture, bannerImage)
        );
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ArtistResponse>> getAllArtist(){
        return ResponseEntity.ok(artistService.getAllArtist());
    }


    @GetMapping("/profile")
    public ResponseEntity<ArtistResponse> getArtistProfile(){
        return ResponseEntity.ok(artistService.getArtistProfile());
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
