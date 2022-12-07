package com.golfzon.social.meeting.controller;

import com.golfzon.social.meeting.dto.MeetingDto;
import com.golfzon.social.meeting.service.MeetingService;
import com.golfzon.social.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<String> postMeeting(MeetingDto.Request request,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.postMeeting(request, userDetails.getMember()));
    }

    //모임 소개 정보조회
    @GetMapping(value = "/{meetingId}/details")
    public ResponseEntity<MeetingDto.Response> getMeeting(@PathVariable(name = "meetingId") Long meetingId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .body(meetingService.getMeeting(meetingId, userDetails.getMember()));
    }


    //모임가입하기
    @PostMapping(value = "/{meetingId}/signup")
    public ResponseEntity<MeetingDto.Role> signupMeeting(@PathVariable(name = "meetingId") Long meetingId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.signupMeeting(meetingId, userDetails.getMember()));
    }

    //모임 수정하기
    @PutMapping(value = "/{meetingId}/update")
    public ResponseEntity<String> updateMeeting(@PathVariable(name = "meetingId") Long meetingId, MeetingDto.Request request,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.updateMeeting(meetingId, request, userDetails.getMember()));
    }

    //모임 삭제하기
    @DeleteMapping(value = "/{meetingId}/delete")
    public ResponseEntity<String> deleteMeeting(@PathVariable(name = "meetingId") Long meetingId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(meetingService.deleteMeeting(meetingId, userDetails.getMember()));
    }

}
