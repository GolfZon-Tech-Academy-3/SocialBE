package com.golfzon.social.album.repository;

import com.golfzon.social.album.entity.Album;
import com.golfzon.social.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByMeetingOrderByAlbumIdDesc(Meeting meeting);
}
