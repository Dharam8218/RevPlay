package com.revature.RevPlay.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArtistResponse {

    private Long id;
    private String artistName;
    private String genre;
    private String bio;
    private String bannerImage;
    private String profilePicture;
    private String instagram;
    private String spotify;
    private String twitter;
}


