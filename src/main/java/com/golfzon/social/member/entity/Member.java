package com.golfzon.social.member.entity;

import com.golfzon.social.member.dto.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member{

    @Id //pk설정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_member")
    @SequenceGenerator(sequenceName = "seq_member", allocationSize = 1, name="seq_member")
    @Column
    private Long memberId; // 회원 번호

    @Column(nullable = false)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @Column(nullable = false)
    private int age; // 나이

    @Column(nullable = false)
    private String gender; // 성

    @Column(nullable = false)
    private String handy; // 핸디 점수

    @Column(nullable = false)
    private String role; // 권한

    @Column
    private String imgUrl; // 프로필 이미지

    //    @Column
//    private MultipartFile multipartFile;


    public Member(SignupRequestDto signupRequestDto) {
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        this.nickname = signupRequestDto.getNickname();
        this.age = signupRequestDto.getAge();
        this.gender = signupRequestDto.getGender();
        this.handy = signupRequestDto.getHandy();
        this.imgUrl = signupRequestDto.getImgUrl();
        this.role = "member";
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", handy='" + handy + '\'' +
                ", role='" + role + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
