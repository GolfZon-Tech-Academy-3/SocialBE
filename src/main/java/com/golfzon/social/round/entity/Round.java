package com.golfzon.social.round.entity;

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
public class Round {

    @Id //pk설정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_round")
    @SequenceGenerator(sequenceName = "seq_round", allocationSize = 1, name="seq_round")
    @Column
    private Long roundId; // 라운드 번호

    @Column(nullable = false)
    private String explanation; // 라운드 소개

    @Column(name = "round_date",nullable = false)
    private String roundDate; // 라운드 진행 날짜

    @Column(name = "recruit_start",nullable = false)
    private String recruitStart; // 라운드 모집 시작

    @Column(name = "recruit_end",nullable = false)
    private String recruitEnd; // 라운드 모집 시작

    @Column(nullable = false)
    private String location; // 장소

    @Column(nullable = false)
    private String details; // 상세주소

    @Column(name = "min_age", nullable = false)
    private String minAge; // 최소 연령 조건

    @Column(name = "max_age", nullable = false)
    private String maxAge; // 최대 연령 조건

    @Column(nullable = false)
    private String personnel; // 인원 수

    @Column(nullable = false)
    private String gender; // 성별

    @Column(nullable = false)
    private String imageUrl; // 라운드 대표 이미지

}
