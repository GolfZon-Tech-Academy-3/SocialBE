package com.golfzon.social.album.controller;

import com.golfzon.social.album.service.AlbumService;
import com.golfzon.social.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class AlbumController {

    private final AlbumService albumService;

    //앨범등록하기
    @PostMapping(value = "/{meetingId}/post-album")
    public ResponseEntity<String> postAlbum(@PathVariable(name = "meetingId") Long meetingId, @RequestPart(value = "imageUrls") List<MultipartFile> files,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(albumService.postAlbum(meetingId, files, userDetails.getMember()));
    }

    //앨범 사진조회
    @GetMapping(value = "/{meetingId}/albums")
    public ResponseEntity<List<String>> getMeeting(@PathVariable(name = "meetingId") Long meetingId) {

        return ResponseEntity.ok()
                .body(albumService.getAlbums(meetingId));
    }
}
