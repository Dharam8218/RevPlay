package com.revature.RevPlay.transformer;

import com.revature.RevPlay.dto.request.AlbumRequest;
import com.revature.RevPlay.dto.response.AlbumResponse;
import com.revature.RevPlay.model.Album;
import com.revature.RevPlay.model.Artist;

import java.time.LocalDate;

public class AlbumTransformer {

    public static Album albumRequestToAlbum(AlbumRequest albumRequest, String coverUrl, Artist artist){
      return Album.builder()
              .albumName(albumRequest.getName())
              .description(albumRequest.getDescription())
              .releaseDate(LocalDate.parse(albumRequest.getReleaseDate()))
              .coverArtUrl(coverUrl)
              .artist(artist)
              .build();
    }

    public static AlbumResponse albumToAlbumResponse(Album album){
        return AlbumResponse.builder()
                .id(album.getId())
                .name(album.getAlbumName())
                .description(album.getDescription())
                .releaseDate(album.getReleaseDate())
                .coverArtUrl(album.getCoverArtUrl())
                .artistName(album.getArtist().getArtistName())
                .build();
    }
}
