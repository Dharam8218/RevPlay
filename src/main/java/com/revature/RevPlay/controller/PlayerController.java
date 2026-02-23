package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.response.ListeningHistoryResponse;
import com.revature.RevPlay.service.ListeningHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/revplay/player")
public class PlayerController {

    private final ListeningHistoryService listeningHistoryService;

    @PostMapping("/songs/{songId}/played")
    public ResponseEntity<Void> played(@PathVariable Long songId) {
        listeningHistoryService.recordPlay(songId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/songs/recently-played")
    public ResponseEntity<List<ListeningHistoryResponse>> recentlyPlayed() {
        return ResponseEntity.ok(listeningHistoryService.getRecentlyPlayed());
    }

    @GetMapping("/songs/listening-history")
    public ResponseEntity<Page<ListeningHistoryResponse>> history(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(listeningHistoryService.getHistory(page, size));
    }

    @DeleteMapping("/songs/listening-history")
    public ResponseEntity<String> clear() {
        listeningHistoryService.clearHistory();
        return ResponseEntity.noContent().build();
    }


}
