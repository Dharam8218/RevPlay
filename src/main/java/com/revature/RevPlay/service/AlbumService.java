package com.revature.RevPlay.service;

import com.revature.RevPlay.dto.request.AlbumRequest;
import com.revature.RevPlay.dto.request.AlbumUpdateRequest;
import com.revature.RevPlay.dto.response.AlbumResponse;
import com.revature.RevPlay.model.Album;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.repository.AlbumRepository;
import com.revature.RevPlay.repository.ArtistRepository;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.transformer.AlbumTransformer;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final CloudinaryService cloudinaryService;
    private final SongRepository songRepository;

    public AlbumResponse createAlbum(AlbumRequest request, MultipartFile coverArt) {

        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        String coverUrl = null;
        if (coverArt != null && !coverArt.isEmpty()) {
            coverUrl = cloudinaryService.uploadFile(coverArt, "album_covers");
        }
        Album saved = albumRepository.save(AlbumTransformer.albumRequestToAlbum(request, coverUrl, artist));
        return AlbumTransformer.albumToAlbumResponse(saved);
    }

    @Transactional
    public void addSongToAlbum(Long albumId, Long songId, Long artistId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        // SECURITY CHECK
        if (!album.getArtist().getId().equals(artistId) ||
                !song.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You can only manage your own songs/albums");
        }
        song.setAlbum(album);
        songRepository.save(song);
    }

    @Transactional
    public void removeSongFromAlbum(Long albumId, Long songId, Long artistId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (!album.getArtist().getId().equals(artistId) ||
                !song.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (!song.getAlbum().getId().equals(albumId)) {
            throw new RuntimeException("Song does not belong to this album");
        }

        song.setAlbum(null);

        songRepository.save(song);
    }

    public List<AlbumResponse> getAllAlbums(Long artistId) {
        return albumRepository.findByArtistId(artistId).stream()
                .map(AlbumTransformer::albumToAlbumResponse)
                .toList();
    }

    @Transactional
    public AlbumResponse updateAlbum(Long albumId, Long artistId, AlbumUpdateRequest request, MultipartFile cover) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        // ownership check
        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You can update only your own albums");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            album.setAlbumName(request.getName().trim());
        }

        if (request.getDescription() != null) {
            album.setDescription(request.getDescription());
        }

        if (cover != null && !cover.isEmpty()) {
            String coverUrl = cloudinaryService.uploadFile(cover, "album_covers");
            album.setCoverArtUrl(coverUrl);
        }

        Album saved = albumRepository.save(album);

        return AlbumTransformer.albumToAlbumResponse(saved);
    }

    @Transactional
    public void deleteAlbum(Long albumId, Long artistId) {

        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        if (!album.getArtist().getId().equals(artistId)) {
            throw new RuntimeException("You can delete only your own albums");
        }

        long songsCount = songRepository.countByAlbumId(albumId);
        if (songsCount > 0) {
            throw new RuntimeException("Cannot delete album. Remove songs first.");
        }

        albumRepository.delete(album);
    }


}

