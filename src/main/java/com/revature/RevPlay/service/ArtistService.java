package com.revature.RevPlay.service;

import com.revature.RevPlay.Enum.RoleName;
import com.revature.RevPlay.Enum.TrendType;
import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.response.*;
import com.revature.RevPlay.exception.ArtistNotFoundException;
import com.revature.RevPlay.exception.UserNotFoundException;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.ArtistRepository;
import com.revature.RevPlay.repository.ListeningHistoryRepository;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.ArtistTransformer;
import com.revature.RevPlay.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ListeningHistoryRepository listeningHistoryRepository;

    public ArtistResponse updateArtistProfile(ArtistProfileRequest request) {

        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isArtist = user.getRoles()
                .stream()
                .anyMatch(role -> role.getName() == RoleName.ARTIST);

        if (!isArtist) {
            throw new RuntimeException("Only artists have profiles");
        }
        Artist artist = artistRepository.findByUserId(user.getId()).orElseThrow(
                () -> new RuntimeException("Artist not found")
        );

        return ArtistTransformer.artistToArtistResponse(artistRepository.save(ArtistTransformer.setArtistProfile(artist, user, request)));
    }

    public ArtistDashboardResponse getDashboard() {

        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));

        Long artistId = artist.getId();

        long totalSongs = songRepository.countByArtistId(artistId);
        long totalPlays = songRepository.getTotalPlaysByArtist(artistId);
        long totalFavorites = songRepository.countFavoritesForArtist(artistId);

        return ArtistDashboardResponse.builder()
                .totalSongs(totalSongs)
                .totalPlays(totalPlays)
                .totalFavorites(totalFavorites)
                .build();
    }

    public SongPlayCountResponse getSongPlayCount(Long songId) {

        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));

        return songRepository.getSongPlayStats(songId, artist.getId())
                .orElseThrow(() ->
                        new RuntimeException("Song not found or you are not authorized to view this analytics"));
    }

    public List<SongPlayCountResponse> getPopularSongs() {

        String username = SecurityUtils.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));

        // return songRepository.getSongsSortedByPopularity(artist.getId());
        return songRepository
                .getSongsSortedByPopularity(artist.getId(), PageRequest.of(0, 5))
                .getContent();
    }

    public List<FavoritedUserResponse> getFavoritedUsers(Long songId) {

        String username = SecurityUtils.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));

        List<FavoritedUserResponse> users =
                songRepository.getFavoritedUsersForSong(songId, artist.getId());

        if (users.isEmpty()) {
            // Optional: verify song exists but has no favorites
            boolean exists = songRepository.existsById(songId);
            if (!exists) {
                throw new RuntimeException("Song not found");
            }
        }

        return users;
    }

    public List<TrendResponse> getListeningTrends(TrendType type) {

        String username = SecurityUtils.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));

        Long artistId = artist.getId();

        return switch (type) {
            case DAILY -> listeningHistoryRepository.getDailyTrends(artistId);
            case WEEKLY -> listeningHistoryRepository.getWeeklyTrends(artistId);
            case MONTHLY -> listeningHistoryRepository.getMonthlyTrends(artistId);
        };
    }

    public Page<TopListenerResponse> getTopListeners(int page, int size) {

        String username = SecurityUtils.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Artist artist = artistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist profile not found"));


        Pageable pageable = PageRequest.of(page, size);

        return listeningHistoryRepository
                .findTopListenersByArtist(artist.getId(), pageable);
    }
}

