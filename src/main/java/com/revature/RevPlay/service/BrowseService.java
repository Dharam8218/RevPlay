package com.revature.RevPlay.service;

import com.revature.RevPlay.Enum.Genre;
import com.revature.RevPlay.Enum.Visibility;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.spec.SongSpecifications;
import com.revature.RevPlay.transformer.SongTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrowseService {

    private final SongRepository songRepository;

    public List<SongResponse> browseAllSongs() {
        return songRepository.findByVisibilityOrderByIdDesc(Visibility.PUBLIC)
                .stream()
                .map(SongTransformer::songToSongResponse)
                .toList();
    }

    public Page<SongResponse> browseAllSongs(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return songRepository.findByVisibility(Visibility.PUBLIC, pageable)
                .map(SongTransformer::songToSongResponse);
    }


    public Page<SongResponse> browseByGenre(
            Genre genre,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return songRepository
                .findByGenreAndVisibility(genre, Visibility.PUBLIC, pageable)
                .map(SongTransformer::songToSongResponse);
    }

    public Page<SongResponse> browseByArtist(
            Long artistId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return songRepository
                .findByArtistIdAndVisibility(artistId, Visibility.PUBLIC, pageable)
                .map(SongTransformer::songToSongResponse);
    }

    public Page<SongResponse> browseByAlbum(
            Long albumId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return songRepository
                .findByAlbumIdAndVisibility(albumId, Visibility.PUBLIC, pageable)
                .map(SongTransformer::songToSongResponse);
    }

    public Page<SongResponse> filterSongs(
            Genre genre,
            Long artistId,
            Long albumId,
            Integer year,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Song> spec = Specification.where(SongSpecifications.isPublic());

        if (genre != null)
            spec = spec.and(SongSpecifications.hasGenre(genre));

        if (artistId != null)
            spec = spec.and(SongSpecifications.hasArtistId(artistId));

        if (albumId != null)
            spec = spec.and(SongSpecifications.hasAlbumId(albumId));

        if (year != null)
            spec = spec.and(SongSpecifications.hasReleaseYear(year));

        return songRepository.findAll(spec, pageable)
                .map(SongTransformer::songToSongResponse);
    }


}
