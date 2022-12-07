package com.golfzon.social.round.service;

import com.golfzon.social.member.entity.Member;
import com.golfzon.social.member.repository.MemberRepository;
import com.golfzon.social.round.dto.RoundRequestDto;
import com.golfzon.social.round.entity.Round;
import com.golfzon.social.round.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;
    private final MemberRepository memberRepository;
    private final RoundS3Service roundS3Service;

    public static String roundImages = "round_001.png";

    // 라운드 개설하기
    public void createRound(RoundRequestDto roundRequestDto, Member member) {

        Member member1 = memberRepository.findById(member.getMemberId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "라운드 개설할 수 없는 멤버입니다."));

        log.info("member1 : {}", member1.toString());

        Round round = new Round(roundRequestDto.getExplanation(), roundRequestDto.getRoundDate(),
                roundRequestDto.getRecruitStart(), roundRequestDto.getRecruitEnd(),
                roundRequestDto.getLocation(), roundRequestDto.getDetails(),
                roundRequestDto.getMinAge(), roundRequestDto.getMaxAge(), roundRequestDto.getPersonnel(),
                roundRequestDto.getGender(), roundRequestDto.getMultipartFile().toString());

        roundRepository.save(round);

    }

    // 라운드 수정하기
    public void updateRound(Long roundId, RoundRequestDto roundRequestDto, Member member) {

        Member member1 = memberRepository.findById(member.getMemberId()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "라운드 수정할 수 없는 멤버입니다."));

        Round round = roundRepository.findById(roundId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 수 없는 라운드 입니다."));

        log.info("member1 : {}", member1.toString());

        round.setExplanation(roundRequestDto.getExplanation());
        round.setRoundDate(roundRequestDto.getRoundDate());
        round.setRecruitStart(roundRequestDto.getRecruitStart());
        round.setRecruitEnd(roundRequestDto.getRecruitEnd());
        round.setLocation(roundRequestDto.getLocation());
        round.setDetails(roundRequestDto.getDetails());
        round.setMinAge(roundRequestDto.getMinAge());
        round.setMaxAge(roundRequestDto.getMaxAge());
        round.setPersonnel(roundRequestDto.getPersonnel());
        round.setGender(roundRequestDto.getGender());

        // 사진이 없다면
        if (roundRequestDto.getMultipartFile() == null) {
            round.setImageUrl("https://spacez3.s3.ap-northeast-2.amazonaws.com/" + roundImages);
        // 사진이 있다면
        } else if(roundRequestDto.getMultipartFile() != null) {
            String imageUrl = roundS3Service.update(roundId, roundRequestDto.getMultipartFile());
            round.setImageUrl(imageUrl);
        }

        roundRepository.save(round);
    }
}
