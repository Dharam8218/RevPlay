package com.revature.RevPlay.repository;

import com.revature.RevPlay.model.Playlist;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Page<Playlist> findByIsPublicTrueAndNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );
}
