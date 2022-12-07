package com.golfzon.social.round.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoundListResponseDto {

    private String roundDate; // 라운드 진행 날짜
//    private String recruitStart; // 라운드 모집 시작
//    private String recruitEnd; // 라운드 모집 마감
    private String recruitStatus; // 모집 여부
    private String location; // 장소
    private String gender; // 성별
}
