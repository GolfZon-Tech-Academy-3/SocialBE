package com.golfzon.social.meeting.controller;

import com.golfzon.social.meeting.dto.MeetingDto;
import com.golfzon.social.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    //모임개설하기
    @PostMapping(value = "/post")
    public ResponseEntity<String> postMeeting(MeetingDto.Request request) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("principal:{}",principal);
        //Member member = ((UserDetailsImpl)principal).getMember();

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.postMeeting(request)); //member
    }

    //모임 소개 정보조회
    @GetMapping(value = "/{meetingId}/details")
    public ResponseEntity<MeetingDto.Response> getMeeting(@PathVariable(name = "meetingId") Long meetingId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("principal:{}",principal);
        //Member member = ((UserDetailsImpl)principal).getMember();

        // 업체 신청 목록보기
        return ResponseEntity.ok()
                .body(meetingService.getMeeting(meetingId)); //member
    }


    //모임가입하기
    @PostMapping(value = "/{meetingId}/signup")
    public ResponseEntity<MeetingDto.Role> signupMeeting(@PathVariable(name = "meetingId") Long meetingId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("principal:{}",principal);
        //Member member = ((UserDetailsImpl)principal).getMember();

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.signupMeeting(meetingId)); //member
    }

    //모임 수정하기
    @PutMapping(value = "/{meetingId}/update")
    public ResponseEntity<String> updateMeeting(@PathVariable(name = "meetingId") Long meetingId, MeetingDto.Request request) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("principal:{}",principal);
        //Member member = ((UserDetailsImpl)principal).getMember();

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.updateMeeting(meetingId, request)); //member
    }

    //모임 삭제하기
    @DeleteMapping(value = "/{meetingId}/delete")
    public ResponseEntity<String> deleteMeeting(@PathVariable(name = "meetingId") Long meetingId) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("principal:{}",principal);
        //Member member = ((UserDetailsImpl)principal).getMember();

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.deleteMeeting(meetingId)); //member
    }

}
