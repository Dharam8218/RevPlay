package com.revature.RevPlay.repository;

import com.revature.RevPlay.model.Playlist;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Page<Playlist> findByIsPublicTrueAndNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    List<Playlist> findByUserUsernameOrderByCreatedAtDesc(String username);
}
