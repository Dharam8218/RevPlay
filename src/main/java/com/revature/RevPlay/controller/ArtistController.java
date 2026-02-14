package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.response.ArtistResponse;
import com.revature.RevPlay.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revplay")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PutMapping("/artist-profile/{userId}")
    public ResponseEntity<ArtistResponse> updateProfile(
            @PathVariable Long userId,
            @RequestBody ArtistProfileRequest request) {

        return ResponseEntity.ok(artistService.updateArtistProfile(userId, request));
    }
}
