package com.revature.RevPlay.repository;

import com.revature.RevPlay.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
