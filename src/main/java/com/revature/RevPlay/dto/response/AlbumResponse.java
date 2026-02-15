package com.revature.RevPlay.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AlbumResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private String coverArtUrl;
    private String artistName;
}
