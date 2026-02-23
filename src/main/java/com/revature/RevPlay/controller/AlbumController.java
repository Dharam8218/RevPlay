package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.AlbumRequest;
import com.revature.RevPlay.dto.request.AlbumUpdateRequest;
import com.revature.RevPlay.dto.response.AlbumResponse;
import com.revature.RevPlay.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/revplay/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AlbumResponse> createAlbum(
            @RequestParam("data") String data,
            @RequestParam(value = "cover", required = false) MultipartFile cover
    ) throws Exception {

        AlbumRequest request = objectMapper.readValue(data, AlbumRequest.class);
        AlbumResponse response = albumService.createAlbum(request, cover);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{albumId}/songs/{songId}")
    public ResponseEntity<String> addSongToAlbum(
            @PathVariable Long albumId,
            @PathVariable Long songId
    ) {

        albumService.addSongToAlbum(albumId, songId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{albumId}/songs/{songId}")
    public ResponseEntity<String> removeSongFromAlbum(
            @PathVariable Long albumId,
            @PathVariable Long songId
    ) {
        albumService.removeSongFromAlbum(albumId, songId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AlbumResponse>> getMyAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @GetMapping("/get-all-albums")
    public ResponseEntity<List<AlbumResponse>> getAllAlbums(){
        return ResponseEntity.ok(albumService.getAllAlbum());
    }

    @PostMapping(value = "/{albumId}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumResponse> updateAlbum(
            @PathVariable Long albumId,
            @RequestParam("data") String data,
            @RequestParam(value = "cover", required = false) MultipartFile cover
    ) throws Exception {

        AlbumUpdateRequest request = new ObjectMapper().readValue(data, AlbumUpdateRequest.class);

        return ResponseEntity.ok(albumService.updateAlbum(albumId, request, cover));
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<String> deleteAlbum(
            @PathVariable Long albumId
    ) {
        albumService.deleteAlbum(albumId);
        return ResponseEntity.noContent().build();
    }

}
