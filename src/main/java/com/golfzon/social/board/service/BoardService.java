package com.golfzon.social.board.service;

import com.golfzon.social.board.dto.BoardDto;
import com.golfzon.social.board.entity.Board;
import com.golfzon.social.board.repository.BoardRepository;
import com.golfzon.social.meeting.entity.Meeting;
import com.golfzon.social.meeting.entity.MeetingMember;
import com.golfzon.social.meeting.repository.MeetingMemberRepository;
import com.golfzon.social.meeting.repository.MeetingRepository;
import com.golfzon.social.member.entity.Member;
import com.golfzon.social.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final MeetingRepository meetingRepository;
    private final MeetingMemberRepository meetingMemberRePository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시글 작성
    public String postBoard(Long meetingId, BoardDto.Request request, Member member) {
        log.info("request:{}", request);
        //모임 유무와 모임가입자인지 여부 확인
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        MeetingMember meetingMember = meetingMemberRePository.findByMemberAndMeeting(member, meeting);
        if (meetingMember == null || meetingMember.getRole().equals("waiting")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아직 모임에 가입되지 않은 회원입니다.");
        } else if (request.getNotice() && !meetingMember.getRole().equals("leader")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "리더 권한이 아닙니다.");
        }
        //DB에 게시글 저장
        boardRepository.save(new Board(request, meeting, member.getMemberId()));
        return "result : 게시글 등록 완료";
    }

    //게시글 목록 보기
    public BoardDto.Response getBoards(Long meetingId, Member member) {
        //모임 유무와 모임가입자인지 여부 확인
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 모임 아이디가 없습니다."));
        MeetingMember meetingMember = meetingMemberRePository.findByMemberAndMeeting(member, meeting);
        if (meetingMember == null || meetingMember.getRole().equals("waiting")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아직 모임에 가입되지 않은 회원입니다.");
        }
        //board 목록 가져오기
        List<Board> boards = boardRepository.findAllByMeetingOrderByBoardIdDesc(meeting);
        List<BoardDto.Info> infos = new ArrayList<>();
        for (Board board : boards) {
            String title = board.getTitle();
            if(board.getNotice()) title = "공지";
            //SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
            String createdDate = board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").withLocale(Locale.forLanguageTag("ko")));
            infos.add(new BoardDto.Info(board.getBoardId(), title, createdDate));
        }
        return new BoardDto.Response(infos);
    }

    //게시글 상세보기
    public BoardDto.DetailResponse getBoardDetail(Long boardId, Member member) {
        //게시글 유무 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 보드 아이디가 없습니다."));
        Member writer = memberRepository.findById(board.getMemberId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 작성자가 없습니다."));
        boolean isWriter = writer.getMemberId().equals(member.getMemberId());
        String createdDate = board.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").withLocale(Locale.forLanguageTag("ko")));
        return new BoardDto.DetailResponse(board.getTitle(), board.getContents(), createdDate, writer.getNickname(), isWriter);
    }

    //게시글 수정
    public String updateBoard(Long boardId, BoardDto.Request request, Member member) {
        //게시글 유무와 게시글 작성자인지 여부 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 보드 아이디가 없습니다."));
        Member writer = memberRepository.findById(board.getMemberId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 작성자가 없습니다."));
        if (!writer.getMemberId().equals(member.getMemberId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 작성자가 아닙니다.");
        }
        //db에 저장
        board.setTitle(request.getTitle());
        board.setContents(request.getContents());
        boardRepository.save(board);
        return "result : 게시글 수정 완료";
    }

    public String deleteBoard(Long boardId, Member member) {
        //게시글 유무와 게시글 작성자인지 여부 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당하는 보드 아이디가 없습니다."));
        Member writer = memberRepository.findById(board.getMemberId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시글 작성자가 없습니다."));
        if (!writer.getMemberId().equals(member.getMemberId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글 작성자가 아닙니다.");
        }
        //DB에서 삭제
        boardRepository.deleteById(boardId);
        return "result : 게시글 삭제 완료";
    }
}
