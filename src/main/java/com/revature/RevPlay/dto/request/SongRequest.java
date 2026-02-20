package com.revature.RevPlay.dto.request;

import com.revature.RevPlay.Enum.Genre;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SongRequest {
    private String title;
    private Genre genre;
    private int duration; // seconds
    private String audioUrl;   // file URL for now
    private String coverArtUrl;  // optional
    private Long albumId; // optional
    private LocalDate releaseDate;
}