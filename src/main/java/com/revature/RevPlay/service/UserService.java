package com.revature.RevPlay.service;

import com.revature.RevPlay.Enum.RoleName;
import com.revature.RevPlay.dto.request.ArtistRequest;
import com.revature.RevPlay.dto.request.UserRequest;
import com.revature.RevPlay.dto.response.UserResponse;
import com.revature.RevPlay.model.Artist;
import com.revature.RevPlay.model.Role;
import com.revature.RevPlay.model.User;
import com.revature.RevPlay.repository.ArtistRepository;
import com.revature.RevPlay.repository.RoleRepository;
import com.revature.RevPlay.repository.UserRepository;
import com.revature.RevPlay.transformer.ArtistTransformer;
import com.revature.RevPlay.transformer.UserTransformer;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ArtistRepository artistRepository;


    public UserResponse registerUser(UserRequest request) {
        User user = UserTransformer.userRequestToUser(request);
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.getRoles().add(userRole);
        return UserTransformer.userToUserResponse(userRepository.save(user));
    }

    public UserResponse registerArtist(ArtistRequest request) {
        User user = UserTransformer.userRequestToUser(request);
        Role userRole = roleRepository.findByName(RoleName.ARTIST)
                .orElseThrow(() -> new RuntimeException("Role ARTIST not found"));
        user.getRoles().add(userRole);
        User savedUser = userRepository.save(user);

        UserResponse savedUserResponse = UserTransformer.userToUserResponse(savedUser);

        artistRepository.save(ArtistTransformer.artistRequestToArtist(request,savedUser));
        return savedUserResponse;
    }
}

