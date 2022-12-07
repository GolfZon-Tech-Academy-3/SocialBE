package com.golfzon.social.member.service;

import com.golfzon.social.member.dto.SignupRequestDto;
import com.golfzon.social.member.entity.Member;
import com.golfzon.social.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberS3Service memberS3Service;

    public static String profileImages = "img_001.png";

    //회원가입
    public void signup(SignupRequestDto signupRequestDto) {
        log.info("signup()...");
        log.info("{}", signupRequestDto);

        // 아이디 중복확인
        checkUsername(signupRequestDto.getUsername());

        // 아무 사진을 지정하지 선택하지 않았다면
        if (signupRequestDto.getMultipartFile() == null) {
            signupRequestDto.setImgUrl("https://spacez3.s3.ap-northeast-2.amazonaws.com/" + profileImages);
        // 사진이 있다면
        } else if(signupRequestDto.getMultipartFile() != null){
            signupRequestDto.setImgUrl("https://spacez3.s3.ap-northeast-2.amazonaws.com/" + signupRequestDto.getMultipartFile());
        }
        // 비밀번호 암호화
        signupRequestDto.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        memberRepository.save(new Member(signupRequestDto));
    }

    //아이디 중복확인
    public void checkUsername(String username) {

        Optional<Member> foundByUsername = memberRepository.findByUsername(username);

        if (foundByUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }

        if (username.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디를 입력해주세요.");
        }

    }

    //닉네임 중복확인
    public int checkNickname(SignupRequestDto signupRequestDto) {
        log.info("checkNickname()...");
        log.info("{}", signupRequestDto);
        int flag = 0;
        Optional<Member> member = memberRepository.findByNickname(signupRequestDto.getNickname());
        log.info("{}", member);
        if (member.isEmpty()) {
            flag = 1;
        }
        log.info("flag:{}", flag);
        return flag;
    }

    //회원정보 수정
    public void updateMember(SignupRequestDto signupRequestDto, Member member) {
        log.info("updateMember()...");
        log.info("signupRequestDto:{}", signupRequestDto);
        log.info("member:{}", member);

        // 프로필이미지 S3에 저장
        if (signupRequestDto.getMultipartFile() != null) {
            String imageUrl = memberS3Service.update(member.getMemberId(), signupRequestDto.getMultipartFile());
            member.setImgUrl(imageUrl);
        }
        member.setNickname(signupRequestDto.getNickname());
        // Update member info.
        memberRepository.save(member);
    }

//    //마스터 목록 조회
//    public List<SignupRequestDto> masterList() {
//        List<Member> members = memberRepository.findAllByAuthority("master");
//        List<SignupRequestDto> masters = new ArrayList<>();
//        for (Member member:members) {
//            masters.add(new SignupRequestDto(member));
//        }
//        return masters;
//    }

    //마스터로 승급될 회원 조회
//    public SignupRequestDto memberList(String searchWord) {
//        log.info("searchWord:{}",searchWord);
//        Member member = memberRepository.findMemberBySearchWord(searchWord);
//        log.info("member:{}",member);
//        return new SignupRequestDto(member);
//    }

//    public void approve(Long memberId) {
//
//        Member member = memberRepository.findByMemberId(memberId);
//
//        member.setAuthority("master"); // 권한 변경
//
//        memberRepository.save(member); // 저장.
//
//    }

    // 업체관리자 승인 거부
//    public void disapprove(Long memberId) {
//        Member member = memberRepository.findByMemberId(memberId);
//
//        member.setAuthority("member"); // 권한 변경
//
//        memberRepository.save(member); // 저장.
//    }
}
