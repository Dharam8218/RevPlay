package com.revature.RevPlay.dto.request;

import com.revature.RevPlay.Enum.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ArtistRequest {
    private String username;
    private String email;
    private String password;
    private String artistName;
    private String bio;
    private String genre;
    private Gender gender;
    private String displayName;
    private String profilePicture;
}
