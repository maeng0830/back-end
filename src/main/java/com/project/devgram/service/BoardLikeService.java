package com.project.devgram.service;

import com.project.devgram.dto.BoardLikeDto;
import com.project.devgram.dto.SearchBoardLike.Request;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardLike;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardLikeErrorCode;
import com.project.devgram.exception.errorcode.UserErrorCode;
import com.project.devgram.repository.BoardLikeRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

	private final BoardLikeRepository boardLikeRepository;
	private final UserRepository userRepository;
	private final BoardService boardService;

	public List<BoardLikeDto> searchBoardLike(Request request) {
		return boardLikeRepository.findBy(request);
	}

	@Transactional
	public BoardLikeDto registerBoardLike(Long boardSeq, String username) {
		Users user = userRepository.findByUsername(username).orElseThrow(() -> new DevGramException(UserErrorCode.USER_NOT_EXIST));

		boardLikeRepository.findByBoard_BoardSeqAndUser_UserSeq(boardSeq, user.getUserSeq()).ifPresent(boardLike -> {
			throw new DevGramException(BoardLikeErrorCode.ALREADY_LIKED_BOARD);
		});

		Board board = boardService.getBoard(boardSeq);

		board.increaseLikeCount();

		return BoardLikeDto.from(boardLikeRepository.save(BoardLikeDto.toEntity(board, user, username)));

	}

	@Transactional
	public void deleteBoardLike(Long boardLikeSeq) {
		BoardLike boardLike = boardLikeRepository.findById(boardLikeSeq).orElseThrow(() -> new DevGramException(BoardLikeErrorCode.LIKE_NOT_EXIST));

		boardLike.getBoard().decreaseLikeCount();

		boardLikeRepository.delete(boardLike);
	}
}