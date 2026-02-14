package com.revature.RevPlay.service;

import com.revature.RevPlay.Enum.RoleName;
import com.revature.RevPlay.dto.request.ArtistProfileRequest;
import com.revature.RevPlay.dto.response.ArtistResponse;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.ArtistRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.ArtistTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    public ArtistResponse updateArtistProfile(Long userId, ArtistProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isArtist = user.getRoles()
                .stream()
                .anyMatch(role -> role.getName() == RoleName.ARTIST);

        if (!isArtist) {
            throw new RuntimeException("Only artists have profiles");
        }
        Artist artist = artistRepository.findByUserId(userId).orElseThrow(
                ()-> new RuntimeException("Artist not found")
        );

        return  ArtistTransformer.artistToArtistResponse(artistRepository.save(ArtistTransformer.setArtistProfile(artist, user, request)));
    }
}

