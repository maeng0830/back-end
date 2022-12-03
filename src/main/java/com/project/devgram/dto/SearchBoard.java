package com.project.devgram.dto;

import com.project.devgram.type.status.Status;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchBoard {

	@Getter
	@Setter
	public static class Request {

		private Long boardSeq;
		private String title;
		private String content;
		private Status status;
	}


	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private Long boardSeq;
		private String title;
		private String content;
		private Status status;
		private Integer likeCount;

		public static Response of(BoardDto dto) {
			return Response.builder()
				.boardSeq(dto.getBoardSeq())
				.title(dto.getTitle())
				.content(dto.getContent())
				.status(dto.getStatus())
				.likeCount(dto.getLikeCount())
				.build();
		}

		public static List<Response> listOf(List<BoardDto> boardDtoList) {
			return boardDtoList.stream()
				.map(Response::of)
				.collect(Collectors.toList());
		}
	}
}
