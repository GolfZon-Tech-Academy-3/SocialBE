package com.golfzon.social.round.controller;

import com.golfzon.social.member.dto.SignupRequestDto;
import com.golfzon.social.round.dto.RoundListResponseDto;
import com.golfzon.social.round.dto.RoundRequestDto;
import com.golfzon.social.round.dto.RoundResponseDto;
import com.golfzon.social.round.service.RoundService;
import com.golfzon.social.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Api(tags = "라운드 컨트롤러")
@RequestMapping(value = "/round")
@RestController
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    //라운드 개설하기
    @ApiOperation(value = "라운드 개설", notes = "라운드 개설입니다.")
    @PostMapping("/post")
    public ResponseEntity<String> roundPost(RoundRequestDto roundRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        roundService.createRound(roundRequestDto,userDetails.getMember());

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body("result : 라운드 개설 완료");
    }

    //라운드 수정하기
    @ApiOperation(value = "라운드 수정", notes = "라운드 수정입니다.")
    @PutMapping("/update/{roundId}")
    public ResponseEntity<String> roundUpdate(@PathVariable(name = "roundId") Long roundId,
                                              RoundRequestDto roundRequestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {

        roundService.updateRound(roundId,roundRequestDto,userDetails.getMember());

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body("result : 라운드 수정 완료");
    }

    // 라운드 상세 조회
    @ApiOperation(value = "라운드 상세조회", notes = "라운드 상세조회입니다.")
    @GetMapping("/details/{roundId}")
    public ResponseEntity<RoundResponseDto> roundDetails(@PathVariable(name = "roundId") Long roundId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        RoundResponseDto responseDto = roundService.roundDetails(roundId,userDetails.getMember());

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(responseDto);
    }

    // 라운드 목록 조회
    @ApiOperation(value = "라운드 목록 조회기능", notes = "라운드 목록 조회기능 입니다.")
    @GetMapping("/list")
    public ResponseEntity<List<RoundListResponseDto>> roundList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<RoundListResponseDto> responseDtos = roundService.roundList(userDetails.getMember());

        return ResponseEntity.ok()
                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                .body(responseDtos);
    }
}
