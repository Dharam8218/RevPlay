package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.response.SearchResponse;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.service.BrowseService;
import com.revature.RevPlay.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revplay/library")
@RequiredArgsConstructor
public class LibraryController {

    private final BrowseService browseService;
    private final SearchService searchService;

    /*@GetMapping("/songs")
    public ResponseEntity<List<SongResponse>> browseSongs() {
        return ResponseEntity.ok(browseService.browseAllSongs());
    }*/

    @GetMapping("/songs")
    public ResponseEntity<Page<SongResponse>> browseSongs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(browseService.browseAllSongs(page, size, sortBy, direction));
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(searchService.search(q, page, size));
    }
}
