package com.golfzon.social.meeting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(sequenceName = "seq_meeting_member", allocationSize = 1, name="seq_meeting_member")
public class MeetingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "meeting_member_id")
    private Long meetingMemberId; // 모임회원 번호

//    @ManyToOne
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member;

    @ManyToOne
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    @Column(nullable = false)
    private String role; // 모임 내 권한

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean permission; // 가입승낙여부(true: 승낙됨)

    @Override
    public String toString() {
        return "MeetingMember{" +
                "meetingMemberId=" + meetingMemberId +
                //", member=" + member +
                ", meeting=" + meeting +
                ", role='" + role + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
