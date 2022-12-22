package com.golfzon.social.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        private Long boardId;
        private String title;
        private String createdDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String title;
        private String contents;
        private Boolean notice;


        @Override
        public String toString() {
            return "Info{" +
                    "title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", notice=" + notice +
                    '}';
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private List<Info> boards;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailResponse {
        private String title;
        private String contents;
        private String createdDate;
        private String writer;
        private Boolean isWriter; //작성자일 경우, true
    }
}
