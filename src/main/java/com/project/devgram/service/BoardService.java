package com.project.devgram.service;

import com.project.devgram.dto.SearchBoard.Request;
import com.project.devgram.dto.UpdateBoard;
import com.project.devgram.entity.Board;
import com.project.devgram.dto.BoardDto;
import com.project.devgram.repository.BoardRepository;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardDto registerBoard(String title, String content) {
		return BoardDto.fromEntity(boardRepository
			.save(Board
				.builder()
				.content(content)
				.title(title)
				.build()));
	}

	public List<BoardDto> searchBoards(Request request) {
		return boardRepository.findBy(request);
	}

	@Transactional
	public BoardDto updateBoard(UpdateBoard.Request request) {
		Board board = getBoard(request.getBoardSeq());

		board.updateTitle(request.getTitle());
		board.updateContent(request.getContent());

		return BoardDto.fromEntity(board);
	}

	@Transactional
	public void deleteBoard(Long boardSeq) {
		Board board = getBoard(boardSeq);
		board.deleteBoard();
	}

	private Board getBoard(Long boardSeq) {
		//TODO: exception 기준 대로 만들어 throw 하는 exception 변경 예정.
		return boardRepository.findById(boardSeq).orElseThrow(() ->
			new NoSuchElementException("boardSeq에 해당하는 board가 없습니다."));
	}


}
