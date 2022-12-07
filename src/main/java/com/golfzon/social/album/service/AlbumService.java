package com.golfzon.social.album.service;

import com.golfzon.social.album.entity.Album;
import com.golfzon.social.album.repository.AlbumRepository;
import com.golfzon.social.meeting.entity.Meeting;
import com.golfzon.social.meeting.repository.MeetingRepository;
import com.golfzon.social.meeting.service.MeetingS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final MeetingS3Service meetingS3Service;
    private final AlbumRepository albumRepository;
    private final MeetingRepository meetingRepository;

    // 앨범 등록하기
    public String postAlbum(Long meetingId, List<MultipartFile> files) { //member
        log.info("files.size:{}",files.size());
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        //S3에 이미지 저장 후 DB에 저장
        for (MultipartFile file:files) {
            String image = meetingS3Service.upload(file);
            albumRepository.save(new Album(meeting, image, 1L)); //memberId
        }
        //알림 푸쉬

        return "result : 앨범등록 완료";
    }

//앨범 조회
    public List<String> getAlbums(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        List<Album> albums = albumRepository.findAllByMeetingOrderByAlbumIdDesc(meeting);
        List<String> albumImages = new ArrayList<>();
        for (Album album:albums) {
            albumImages.add(album.getImageUrl());
        }
        return albumImages;
    }
}
