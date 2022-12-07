package com.golfzon.social.member.controller;

import com.golfzon.social.member.dto.SignupRequestDto;
import com.golfzon.social.member.entity.Member;
import com.golfzon.social.member.service.MemberService;
import com.golfzon.social.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@Api(tags = "회원 컨트롤러")
@RequestMapping(value = "/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @ApiOperation(value = "회원가입", notes = "회원가입 처리입니다.")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(SignupRequestDto signupRequestDto) {
        memberService.signup(signupRequestDto);
        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body("회원가입 완료");
    }

    // 아이디 중복체크
    @ApiOperation(value = "아이디 중복체크", notes = "아이디 중복체크 처리입니다.")
    @PostMapping("/username-check/{username}")
    public ResponseEntity<String> checkUsername(@PathVariable(name = "username") String username) {

        memberService.checkUsername(username);

        return ResponseEntity.ok()
                .body("result : 아이디 중복체크 완료");
    }

    //닉네임 중복체크
    @ApiOperation(value = "닉네임 중복체크", notes = "닉네임 중복체크 처리입니다.")
    @PostMapping("/nickname-check")
    public ResponseEntity<Integer> checkNickname(@RequestBody SignupRequestDto signupRequestDto) {
        int result = memberService.checkNickname(signupRequestDto);
        return ResponseEntity.ok()
                .body(result);
    }

    //프로필 수정
    @ApiOperation(value = "회원정보 수정", notes = "회원정보 수정 처리입니다.")
    @PutMapping("/profile")
    public ResponseEntity<String> updateMember(SignupRequestDto signupRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("principal:{}",principal);
        Member member = ((UserDetailsImpl)principal).getMember();
        log.info("member?:{}",member);

        memberService.updateMember(signupRequestDto, member);
        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body("회원수정 완료");
    }

}
