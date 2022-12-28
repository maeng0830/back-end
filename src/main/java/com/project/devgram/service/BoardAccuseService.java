package com.project.devgram.service;

import com.project.devgram.dto.BoardAccuseDto;
import com.project.devgram.dto.SearchBoardAccuse.Request;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardAccuseErrorCode;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.BoardAccuseRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

	public BoardAccuseDto registerBoardAccuse(String username, Long boardSeq, String content) {
		Users user = userRepository.findByUsername(username).orElseThrow(() -> new DevGramException(UserErrorCode.USER_NOT_EXIST));

		findBoardAccuse(boardSeq, user.getUserSeq());

		Board board = boardService.getBoard(boardSeq);

		return BoardAccuseDto.from(boardAccuseRepository.save(BoardAccuseDto.toEntity(board, user, content)));
	}

	private void findBoardAccuse(Long boardSeq, Long userSeq) {
		boardAccuseRepository.findByBoard_BoardSeqAndUser_UserSeq(boardSeq, userSeq).ifPresent(boardAccuse -> {
			throw new DevGramException(BoardAccuseErrorCode.ALREADY_ACCUSED_BOARD);
		});
	}


}