package com.revature.RevPlay.service;

import com.revature.RevPlay.dto.response.ListeningHistoryResponse;
import com.revature.RevPlay.exception.SongNotFoundException;
import com.revature.RevPlay.exception.UserNotFoundException;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.ListeningHistoryRepository;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.ListeningHistoryTransformer;
import com.revature.RevPlay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListeningHistoryService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ListeningHistoryRepository listeningHistoryRepository;

    @Transactional
    public void recordPlay(Long songId) {
        String username = SecurityUtils.getCurrentUsername(); // from JWT
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));

        listeningHistoryRepository.save(
                ListeningHistoryTransformer.toListeningHistory(user, song)
        );
    }

    public List<ListeningHistoryResponse> getRecentlyPlayed() {

        String username = SecurityUtils.getCurrentUsername(); // from JWT
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return listeningHistoryRepository.findTop50ByUserIdOrderByPlayedAtDesc(user.getId())
                .stream().map(ListeningHistoryTransformer::listeningHistoryToListeningHistoryResponse).toList();
    }

    public Page<ListeningHistoryResponse> getHistory(int page, int size) {

        String username = SecurityUtils.getCurrentUsername(); // from JWT
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size);
        return listeningHistoryRepository.findByUserIdOrderByPlayedAtDesc(user.getId(), pageable)
                .map(ListeningHistoryTransformer::listeningHistoryToListeningHistoryResponse);
    }

    @Transactional
    public String clearHistory() {
        String username = SecurityUtils.getCurrentUsername(); // from JWT
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        listeningHistoryRepository.deleteByUserId(user.getId());
        return "Listening history cleared";
    }

}
