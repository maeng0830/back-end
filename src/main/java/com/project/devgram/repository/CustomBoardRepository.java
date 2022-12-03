package com.project.devgram.repository;

import com.project.devgram.dto.BoardDto;
import com.project.devgram.dto.SearchBoard;
import java.util.List;

public interface CustomBoardRepository {

	List<BoardDto> findBy(SearchBoard.Request request);
}
