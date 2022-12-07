package com.golfzon.social.meeting.service;

import com.golfzon.social.meeting.dto.MeetingDto;
import com.golfzon.social.meeting.entity.Meeting;
import com.golfzon.social.meeting.entity.MeetingMember;
import com.golfzon.social.meeting.repository.MeetingMemberRepository;
import com.golfzon.social.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MeetingService {

    private final MeetingS3Service meetingS3Service;
    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRePository;
    //private final MemberRepository memberRepository;

    //모임 개설하기
    public String postMeeting(MeetingDto.Request request) { //, member
        log.info("request:{}",request);
        //모임 이미지 S3에 저장
        String image = meetingS3Service.upload(request.getImageUrl());
        //DB에 저장
        Meeting meeting = meetingRepository.save(new Meeting(request, image));
        //Meeting member(leader로 저장, 마스터 저장)
//        meetingMemberRePository.save(new MeetingMember(meeting, "leader", true));        //member
//        List<Member> members = memberRepository.findAllByRole("master");
//        for (Member member:members) {
//            meetingMemberRePository.save(new MeetingMember(meeting, "master", true));
//        }
        //알림 푸쉬

        return "result : 모임 개설 완료";
    }

    //모임 상세보기
    public MeetingDto.Response getMeeting(Long meetingId) { //member
        //모임 아이디에 해당하는 모임 찾기
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        //로그인한 유저의 모임 역할 찾기
        //MeetingMember meetingMember = meetingMemberRePository.findByMember(member).orElse(new MeetingMember("none"));

        return new MeetingDto.Response(new MeetingDto.Info(meeting), "member"); //meetingMember.getRole()
    }

    //모임 가입하기
    public MeetingDto.Role signupMeeting(Long meetingId) {  //member
        String role = "member";
        //모임 아이디에 해당하는 모임 찾기
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        //로그인한 유저의 모임 역할 찾기
        //MeetingMember meetingMember = meetingMemberRePository.findByMember(member);
        MeetingMember meetingMember = null;
        if(meetingMember != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 모임에 가입되어 있습니다.");
        } else {
            if(meeting.getSecret()) {
                role = "waiting";
                meetingMemberRePository.save(new MeetingMember(meeting, role, false));
                // 가입신청 알림 푸쉬
            } else {
                meetingMemberRePository.save(new MeetingMember(meeting, role, true));
                // 가입완료 알림 푸쉬
            }
        }
        return new MeetingDto.Role(role);
    }

    //모임 수정하기
    public String updateMeeting(Long meetingId, MeetingDto.Request request) {
        log.info("request:{}",request);
        //모임 아이디에 해당하는 모임 찾기
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        //리더가 아니면 수정 불가
//        MeetingMember meetingMember = meetingMemberRePository.findByMember(member).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "모임에 가입되어 있지 않습니다."));
//        if(meetingMember.getRole().equals("leader")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리더 권한이 아닙니다.");
//        }
        //모임 이미지 S3에 저장
        String image = meeting.getImageUrl();
        if(request.getImageUrl() != null){
            image = meetingS3Service.update(meeting, request.getImageUrl());
        }
        //DB에 저장
        meeting.setTitle(request.getTitle());
        meeting.setExplanation(request.getExplanation());
        meeting.setMaxAge(request.getMaxAge());
        meeting.setMinAge(request.getMinAge());
        meeting.setGender(request.getGender());
        meeting.setLocation(request.getLocation());
        meeting.setPermission(request.getPermission());
        meeting.setImageUrl(image);
        meetingRepository.save(meeting);

        return "result : 모임 수정 완료";
    }

    public String deleteMeeting(Long meetingId) {
        log.info("meetingId:{}",meetingId);
        //리더가 아니면 삭제 불가
//        MeetingMember meetingMember = meetingMemberRePository.findByMember(member).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "모임에 가입되어 있지 않습니다."));
//        if(meetingMember.getRole().equals("leader")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리더 권한이 아닙니다.");
//        }
        meetingRepository.deleteById(meetingId);
        return "result : 모임 삭제 완료";
    }
}
