package com.revature.RevPlay.repository;

import com.revature.RevPlay.Enum.Genre;
import com.revature.RevPlay.Enum.Visibility;
import com.revature.RevPlay.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {
    List<Song> findByArtistId(Long artistId);
    boolean existsByIdAndArtistId(Long songId, Long artistId);
    long countByAlbumId(Long albumId);
    List<Song> findByVisibilityOrderByIdDesc(Visibility visibility);
    Page<Song> findByVisibility(Visibility visibility, Pageable pageable);
    List<Song> findByArtistIdAndVisibility(Long artistId, Visibility visibility);

    @Query("""
        select s from Song s
        where s.visibility = :visibility
          and (lower(s.title) like lower(concat('%', :q, '%'))
               or lower(s.artist.artistName) like lower(concat('%', :q, '%'))
               or lower(s.album.albumName) like lower(concat('%', :q, '%')))
    """)
    Page<Song> searchPublicSongs(String q, Visibility visibility, Pageable pageable);

    Page<Song> findByGenreAndVisibility(
            Genre genre,
            Visibility visibility,
            Pageable pageable
    );

    Page<Song> findByArtistIdAndVisibility(
            Long artistId,
            Visibility visibility,
            Pageable pageable
    );

    Page<Song> findByAlbumIdAndVisibility(
            Long albumId,
            Visibility visibility,
            Pageable pageable
    );

    List<Song> findByAlbumIdAndVisibility(Long albumId, Visibility visibility);


}
