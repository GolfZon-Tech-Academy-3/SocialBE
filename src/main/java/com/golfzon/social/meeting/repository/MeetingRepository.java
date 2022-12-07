package com.golfzon.social.meeting.repository;

import com.golfzon.social.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting,Long> {

}
