package com.project.devgram.service;

import com.project.devgram.dto.BoardDto;
import com.project.devgram.dto.BoardProductDto;
import com.project.devgram.dto.BoardTagDto;
import com.project.devgram.dto.RegisterBoard.Request;
import com.project.devgram.dto.RegisterBoard.Response;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardServiceContainer {

	private final BoardService boardService;
	private final BoardTagService boardTagService;
	private final TagService tagService;
	private final BoardProductService boardProductService;
	private final ImageUploader uploader;

	public Response registerBoard(String username, Request request, MultipartFile file) throws IOException {
		List<Tag> tagList = tagService.addTag(request.getTagNames());

		String imageUrl = uploader.upload(file, "BOARD");

		request.setImageUrl(imageUrl);

		Board board = boardService.registerBoard(request , username);

		List<BoardTagDto> boardTagDtos = boardTagService.registerBoardTag(board, tagList , username);

		List<BoardProductDto> boardProductDtos = boardProductService.registerBoardProduct(board, request.getProductSeqList() , username);

		return Response.from(BoardDto.fromEntity(board), boardTagDtos, boardProductDtos);
	}
}
