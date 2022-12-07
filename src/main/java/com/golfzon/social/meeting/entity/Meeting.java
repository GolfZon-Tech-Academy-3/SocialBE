package com.golfzon.social.meeting.entity;

import com.golfzon.social.album.entity.Album;
import com.golfzon.social.meeting.dto.MeetingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(sequenceName = "seq_meeting", allocationSize = 1, name="seq_meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "meeting_id")
    private Long meetingId; // 모임 번호

    @Column(nullable = false)
    private String title; // 모임명

    @Column(nullable = false)
    private String explanation; // 모임소개

    @Column(name = "max_age")
    private String maxAge; // 최대연령 조건

    @Column(name = "min_age")
    private String minAge; // 최소연령 조건

    @Column(nullable = false)
    private String gender; // 성별 조건

    @Column(name = "image_url", nullable = false)
    private String imageUrl; // 모임이미지

    @Column
    @ColumnDefault(value = "false")
    private Boolean permission; // 가입승낙필요(false: 필요없음)

    @Column
    @ColumnDefault(value = "false")
    private Boolean secret; // 공개형,비공개형(false: 공개형)

    @Column
    private String location; // 활동지역

    @OneToMany(mappedBy = "meeting",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "meeting",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    public Meeting(MeetingDto.Request request, String image) {
        this.title = request.getTitle();
        this.explanation = request.getExplanation();
        this.maxAge = request.getMaxAge();
        this.minAge = request.getMinAge();
        this.gender = request.getGender();
        this.imageUrl = image;
        this.permission = request.getPermission();
        this.secret = request.getSecret();
        this.location = request.getLocation();
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "meetingId=" + meetingId +
                ", title='" + title + '\'' +
                ", explanation='" + explanation + '\'' +
                ", maxAge='" + maxAge + '\'' +
                ", minAge='" + minAge + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", permission='" + permission + '\'' +
                ", secret='" + secret + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
