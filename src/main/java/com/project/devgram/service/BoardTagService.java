package com.project.devgram.service;

import com.project.devgram.dto.BoardTagDto;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardTag;
import com.project.devgram.entity.Tag;
import com.project.devgram.repository.BoardTagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardTagService {

	private final BoardTagRepository boardTagRepository;

	public List<BoardTagDto> registerBoardTag(Board board, List<Tag> tagList) {
		List<BoardTagDto> boardTagDtoList = new ArrayList<>();
		for (Tag tag : tagList) {
			if (!boardTagRepository.existsByBoard_BoardSeqAndTag_TagSeq(board.getBoardSeq(), tag.getTagSeq())) {
				BoardTag boardTag = boardTagRepository.save(BoardTag.builder().tag(tag).board(board).build());
				boardTagDtoList.add(
					BoardTagDto.builder()
						.boardSeq(boardTag.getBoard().getBoardSeq())
						.tagSeq(boardTag.getTag().getTagSeq())
						.tagName(tag.getName())
						.build());
			}
		}
		return boardTagDtoList;
	}
}
