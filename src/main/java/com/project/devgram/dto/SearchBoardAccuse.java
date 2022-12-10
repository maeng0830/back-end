package com.project.devgram.dto;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchBoardAccuse {

	@Getter
	public static class Request {

		private Long boardAccuseSeq;
		private Long boardSeq;
		private Long userSeq;
		private String content;
		private String reporterName;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {

		private Long boardAccuseSeq;
		private String content;
		private String reporterName;
		private String boardTitle;


		public static SearchBoardAccuse.Response of(BoardAccuseDto boardAccuseDto) {
			return Response.builder()
				.boardAccuseSeq(boardAccuseDto.getBoardAccuseSeq())
				.content(boardAccuseDto.getContent())
				.reporterName(boardAccuseDto.getUsername())
				.boardTitle(boardAccuseDto.getTitle())
			.build();
		}

		public static List<Response> listOf(List<BoardAccuseDto> boardAccuseDtoList) {
			return boardAccuseDtoList.stream()
				.map(Response::of)
				.collect(Collectors.toList());
		}
	}


}
