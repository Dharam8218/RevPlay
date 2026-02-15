package com.revature.RevPlay.transformer;

import com.revature.RevPlay.dto.response.PlaylistResponse;
import com.revature.RevPlay.model.Playlist;

public class PlaylistTransformer {

    public static PlaylistResponse playlistToPlaylistResponse(Playlist playlist) {

        return PlaylistResponse.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .isPublic(playlist.isPublic())
                .ownerUsername(
                        playlist.getUser() != null
                                ? playlist.getUser().getUsername()
                                : null
                )
                .totalSongs(
                        playlist.getSongs() != null
                                ? playlist.getSongs().size()
                                : 0
                )
                .build();
    }
}
