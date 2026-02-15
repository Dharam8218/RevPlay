package com.revature.RevPlay.transformer;

import com.revature.RevPlay.dto.request.SongRequest;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.Song;

public class SongTransformer {

    public static Song songRequestToSong(SongRequest songRequest, String audioUrl, String imageUrl, Artist artist){
        return Song.builder()
                .title(songRequest.getTitle())
                .genre(songRequest.getGenre())
                .duration(songRequest.getDuration())
                .audioUrl(audioUrl)
                .coverImageUrl(imageUrl)
                .artist(artist)
                .build();
    }

    public static SongResponse songToSongResponse(Song song){
        return SongResponse.builder()
                .id(song.getId())
                .title(song.getTitle())
                .genre(song.getGenre())
                .duration(song.getDuration())
                .audioUrl(song.getAudioUrl())
                .coverArtUrl(song.getCoverImageUrl())
                .artistName(song.getArtist().getArtistName())
                .artistId(song.getArtist().getId())
                .albumId(song.getAlbum().getId())
                .albumName(song.getAlbum().getAlbumName())
                .visibility(song.getVisibility())
                .build();
    }
}
