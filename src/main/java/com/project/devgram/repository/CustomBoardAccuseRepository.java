package com.project.devgram.repository;

import com.project.devgram.dto.BoardAccuseDto;
import com.project.devgram.dto.SearchBoardAccuse;
import java.util.List;

public interface CustomBoardAccuseRepository {
	List<BoardAccuseDto> findBy(SearchBoardAccuse.Request request);
}
