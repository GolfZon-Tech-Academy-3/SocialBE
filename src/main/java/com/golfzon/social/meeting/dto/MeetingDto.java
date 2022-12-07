package com.golfzon.social.meeting.dto;

import com.golfzon.social.meeting.entity.Meeting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class MeetingDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private MultipartFile imageUrl;
        private String title;
        private String explanation;
        private String maxAge;
        private String minAge;
        private String gender;
        private Boolean permission;
        private Boolean secret;
        private String location;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info{
        private String title;
        private String imageUrl;
        private String explanation;
        private String maxAge;
        private String minAge;
        private String gender;
        private String location;

        public Info(Meeting meeting) {
            this.title = meeting.getTitle();
            this.imageUrl = meeting.getImageUrl();
            this.explanation = meeting.getExplanation();
            this.maxAge = meeting.getMaxAge();
            this.minAge = meeting.getMinAge();
            this.gender = meeting.getGender();
            this.location = meeting.getLocation();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private MeetingDto.Info info;
        private String role;
    }

    @Getter
    @AllArgsConstructor
    public static class Role{
        private String role;
    }


}
