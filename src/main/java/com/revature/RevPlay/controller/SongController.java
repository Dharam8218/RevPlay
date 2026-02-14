package com.revature.RevPlay.controller;

import com.revature.RevPlay.dto.request.SongRequest;
import com.revature.RevPlay.dto.response.SongResponse;
import com.revature.RevPlay.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/revplay/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<SongResponse> uploadSong(
            @RequestParam("data") String data,
            @RequestParam("audio") MultipartFile audio,
            @RequestParam(value = "cover", required = false) MultipartFile cover
    ) throws Exception {

        SongRequest dto = new ObjectMapper().readValue(data, SongRequest.class);
        SongResponse response = songService.uploadSong(dto, audio, cover);

        return ResponseEntity.ok(response);
    }
}

