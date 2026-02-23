package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.SongRequest;
import com.revature.RevPlay.dto.request.SongUpdateRequest;
import com.revature.RevPlay.dto.request.SongVisibilityRequest;
import com.revature.RevPlay.dto.response.SongDetailsResponse;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/revplay/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<SongResponse> uploadSong(
            @RequestParam("data") String data,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam(value = "cover", required = false) MultipartFile cover
    ) throws Exception {

        SongRequest dto = new ObjectMapper().readValue(data, SongRequest.class);
        SongResponse response = songService.uploadSong(dto, audio, cover);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SongResponse>> getMySongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @PutMapping("/{songId}")
    public ResponseEntity<SongResponse> updateSong(
            @PathVariable Long songId,
            @RequestBody SongUpdateRequest request
    ) {
        return ResponseEntity.ok(songService.updateSong(songId, request));
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<String> deleteSong(
            @PathVariable Long songId
    ) {
        songService.deleteSong(songId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{songId}/visibility")
    public ResponseEntity<SongResponse> updateVisibility(
            @PathVariable Long songId,
            @RequestBody SongVisibilityRequest request
    ) {
        return ResponseEntity.ok(songService.updateVisibility(songId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDetailsResponse> getSongDetails(@PathVariable Long id) {
        return ResponseEntity.ok(songService.getSongDetails(id));
    }
}

