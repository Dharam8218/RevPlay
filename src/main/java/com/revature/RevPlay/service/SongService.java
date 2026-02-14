package com.revature.RevPlay.service;

import com.revature.RevPlay.dto.request.SongRequest;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.repository.ArtistRepository;
import com.revature.RevPlay.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SongService {

    private final CloudinaryService cloudinaryService;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongResponse uploadSong(SongRequest request,
                                  MultipartFile audioFile,
                                  MultipartFile coverImage) {

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        // Upload files to Cloudinary
        String audioUrl = cloudinaryService.uploadFile(audioFile, "songs");
        String imageUrl = coverImage != null
                ? cloudinaryService.uploadFile(coverImage, "covers")
                : null;

        // Save Song in DB
        Song song = Song.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .duration(request.getDuration())
                .audioUrl(audioUrl)
                .coverImageUrl(imageUrl)
                .artist(artist)
                .build();

        Song saved = songRepository.save(song);

        return SongResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .genre(saved.getGenre())
                .duration(saved.getDuration())
                .audioUrl(saved.getAudioUrl())
                .coverArtUrl(saved.getCoverImageUrl())
                .artistName(saved.getArtist().getArtistName())
                .build();
    }
}

