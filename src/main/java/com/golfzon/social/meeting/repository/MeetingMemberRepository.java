package com.golfzon.social.meeting.repository;

import com.golfzon.social.meeting.entity.MeetingMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember,Long> {

    //MeetingMember findByMember(Member member);
}
