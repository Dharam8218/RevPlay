package com.revature.RevPlay.service;

import com.revature.RevPlay.Enum.Visibility;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.model.Song;
import com.revature.RevPlay.repository.SongRepository;
import com.revature.RevPlay.transformer.SongTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrowseService {

    private final SongRepository songRepository;

    public List<SongResponse> browseAllSongs() {
        return songRepository.findByVisibilityOrderByIdDesc(Visibility.PUBLIC)
                .stream()
                .map(SongTransformer::songToSongResponse)
                .toList();
    }

    public Page<SongResponse> browseAllSongs(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return songRepository.findByVisibility(Visibility.PUBLIC, pageable)
                .map(SongTransformer::songToSongResponse);
    }


}
