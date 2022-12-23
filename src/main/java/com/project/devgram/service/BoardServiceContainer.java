package com.project.devgram.service;

import com.project.devgram.dto.BoardDto;
import com.project.devgram.dto.BoardProductDto;
import com.project.devgram.dto.BoardTagDto;
import com.project.devgram.dto.RegisterBoard.Request;
import com.project.devgram.dto.RegisterBoard.Response;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceContainer {

	private final BoardService boardService;
	private final BoardTagService boardTagService;
	private final TagService tagService;
	private final  BoardProductService boardProductService;
	public Response registerBoard(Request request) {

		List<Tag> tagList = tagService.addTag(request.getTagNames());

		Board board = boardService.registerBoard(request);

		List<BoardTagDto> boardTagDtos= boardTagService.registerBoardTag(board, tagList);

		List<BoardProductDto> boardProductDtos = boardProductService.registerBoardProduct(board, request.getProductSeqList());

		return Response.from(BoardDto.fromEntity(board),boardTagDtos,boardProductDtos);
	}
}
