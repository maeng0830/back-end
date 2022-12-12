package com.project.devgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterBoardAccuse {
	@Getter
	@Setter
	public static class Request{
		private Long userSeq;
		private Long boardSeq;
		private String content;
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	public static class Response{
		private Long boardAccuseSeq;
		private String content;
		private Long boardSeq;
		private Long userSeq;
		public static Response of(BoardAccuseDto boardAccuseDto){

			return Response.builder()
				.boardAccuseSeq(boardAccuseDto.getBoardAccuseSeq())
				.content(boardAccuseDto.getContent())
				.boardSeq(boardAccuseDto.getBoardSeq())
				.userSeq(boardAccuseDto.getUserSeq())
				.build();
		}
	}
}
