package com.golfzon.social.album.entity;

import com.golfzon.social.meeting.dto.MeetingDto;
import com.golfzon.social.meeting.entity.Meeting;
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
@SequenceGenerator(sequenceName = "seq_album", allocationSize = 1, name="seq_album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "album_id")
    private Long albumId; // 앨범 번호

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 이미지

    @Column(name = "member_id", nullable = false)
    private Long memberId; // 등록한 memberId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", nullable = false)
    private Meeting meeting;

    public Album(Meeting meeting, String image, long memberId) {
        this.meeting = meeting;
        this.imageUrl = image;
        this.memberId = memberId;
    }
}
