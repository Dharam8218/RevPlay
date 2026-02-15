package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revplay/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{songId}")
    public ResponseEntity<String> add(@PathVariable Long songId) {
        favoriteService.addToFavorites(songId);
        return ResponseEntity.ok("Added to favorites");
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<String> remove(@PathVariable Long songId) {
        favoriteService.removeFromFavorites(songId);
        return ResponseEntity.ok("Removed from favorites");
    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> myFavorites() {
        return ResponseEntity.ok(favoriteService.getMyFavorites());
    }
}

