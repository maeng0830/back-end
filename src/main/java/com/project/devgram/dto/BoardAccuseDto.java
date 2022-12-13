package com.project.devgram.dto;

import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardAccuse;
import com.project.devgram.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAccuseDto {
	private Long boardAccuseSeq;
	private String content;
	private String username;
	private String title;
	private Long boardSeq;
	private Long userSeq;

	public static BoardAccuse toEntity(Board board, Users user, String content){
		return BoardAccuse.builder()
			.board(board)
			.user(user)
			.content(content)
			.build();
	}

	public static BoardAccuseDto from(BoardAccuse boardAccuse) {
		return BoardAccuseDto.builder()
			.boardAccuseSeq(boardAccuse.getBoardAccuseSeq())
			.boardSeq(boardAccuse.getBoard().getBoardSeq())
			.userSeq(boardAccuse.getUser().getUserSeq())
			.content(boardAccuse.getContent())
			.build();
	}
}
