package com.revature.RevPlay.transformer;

import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.request.ArtistRequest;
import com.revature.RevPlay.dto.response.ArtistResponse;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.User;

public class ArtistTransformer {

    public static Artist artistRequestToArtist(ArtistRequest artistRequest, User user) {
        return Artist.builder()
                .artistName(artistRequest.getArtistName())
                .genre(artistRequest.getGenre())
                .user(user)
                .build();
    }

    public static Artist setArtistProfile(Artist artist, User user, ArtistProfileRequest request) {

        return artist.builder()
                .id(artist.getId())
                .user(user)
                .artistName(request.getArtistName())
                .bio(request.getBio())
                .genre(request.getGenre())
                .profilePicture(request.getProfilePicture())
                .bannerImage(request.getBannerImage())
                .instagram(request.getInstagram())
                .twitter(request.getTwitter())
                .youtube(request.getYoutube())
                .spotify(request.getSpotify())
                .website(request.getWebsite())
                .build();
    }

    public static ArtistResponse artistToArtistResponse(Artist artist) {
        return ArtistResponse.builder()
                .id(artist.getId())
                .artistName(artist.getArtistName())
                .genre(artist.getGenre())
                .bio(artist.getBio())
                .bannerImage(artist.getBannerImage())
                .profilePicture(artist.getProfilePicture())
                .instagram(artist.getInstagram())
                .spotify(artist.getSpotify())
                .twitter(artist.getTwitter())
                .build();
    }

}
