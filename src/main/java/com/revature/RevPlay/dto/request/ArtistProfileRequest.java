package com.revature.RevPlay.dto.request;

import lombok.Data;

@Data
public class ArtistProfileRequest {
    private String artistName;
    private String bio;
    private String genre;

    private String profilePicture;
    private String bannerImage;

    private String instagram;
    private String twitter;
    private String youtube;
    private String spotify;
    private String website;
}