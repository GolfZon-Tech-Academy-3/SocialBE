package com.golfzon.social.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    private String username;
    private String password;
    private String nickname;
    private int age;
    private String gender;
    private String handy; // ęł¨í íě
    private String role;
    private String imgUrl;
    private MultipartFile multipartFile;

}
