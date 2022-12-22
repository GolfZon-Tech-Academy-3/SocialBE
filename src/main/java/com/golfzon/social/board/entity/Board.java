package com.golfzon.social.board.entity;

import com.golfzon.social.board.dto.BoardDto;
import com.golfzon.social.meeting.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(sequenceName = "seq_board", allocationSize = 1, name="seq_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "board_id")
    private Long boardId; // 게시글 번호

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String contents; // 게시글 내용

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 등록한 memberId

    @Column(nullable = false)
    private Boolean notice; // 공지여부(true:공지글, false: 일반게시글)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Meeting meeting;

    @CreationTimestamp
    LocalDateTime createdAt;

    public Board(BoardDto.Request request, Meeting meeting, Long memberId) {
        this.title = request.getTitle();
        this.contents = request.getContents();
        this.memberId = memberId;
        this.notice = request.getNotice();
        this.meeting = meeting;
    }
}
