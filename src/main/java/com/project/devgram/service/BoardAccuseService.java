package com.project.devgram.service;

import com.project.devgram.dto.BoardAccuseDto;
import com.project.devgram.dto.SearchBoardAccuse.Request;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.User;
import com.project.devgram.repository.BoardAccuseRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardAccuseService {

	private final BoardAccuseRepository boardAccuseRepository;

	private final BoardService boardService;

	private final UserRepository userRepository;

	public List<BoardAccuseDto> searchBoardAccuse(Request request) {
		return boardAccuseRepository.findBy(request);
	}

	public BoardAccuseDto registerBoardAccuse(Long userSeq, Long boardSeq, String content) {
		findBoardAccuse(boardSeq, userSeq);

		Board board = boardService.getBoard(boardSeq);

		User user = userRepository.findById(userSeq).orElseThrow(() -> new UsernameNotFoundException(""));

		return BoardAccuseDto.from(boardAccuseRepository.save(BoardAccuseDto.toEntity(board, user, content)));
	}

	private void findBoardAccuse(Long boardSeq, Long userSeq) {
		boardAccuseRepository.findByBoard_BoardSeqAndUser_UserSeq(boardSeq, userSeq).ifPresent(boardAccuse -> {
			throw new EntityExistsException("이미 신고 하셨습니다.");
		});
	}


}
