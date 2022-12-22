package com.golfzon.social.meeting.repository;

import com.golfzon.social.meeting.entity.Meeting;
import com.golfzon.social.meeting.entity.MeetingMember;
import com.golfzon.social.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember,Long> {
    MeetingMember findByMemberAndMeeting(Member member, Meeting meeting);
}
