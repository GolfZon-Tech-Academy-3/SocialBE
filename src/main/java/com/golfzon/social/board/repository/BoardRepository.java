package com.golfzon.social.board.repository;

import com.golfzon.social.board.entity.Board;
import com.golfzon.social.meeting.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByMeetingOrderByBoardIdDesc(Meeting meeting);
}
