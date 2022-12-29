package com.project.devgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateBoard {
	@Getter
	@Setter
	public static class Request {
		private Long boardSeq;
		private String title;
		private String precautions;
		private String selfIntroduce;
		private String recommendReason;
		private String bestProduct;
		private String otherProduct;
		private String content;
	}
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	public static class Response{
		private Long boardSeq;
		private String title;
		private String content;
		private Integer likeCount;
		public static Response of(BoardDto boardDto){
			return Response.builder()
				.boardSeq(boardDto.getBoardSeq())
				.title(boardDto.getTitle())
				.content(boardDto.getContent())
				.likeCount(boardDto.getLikeCount())
				.build();
		}
	}
}
