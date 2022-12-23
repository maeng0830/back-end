package com.project.devgram.dto;

import com.project.devgram.type.status.Status;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterBoard {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Request {

		@Size(max = 200, message = "제목은 200자를 넘을수 없습니다.")
		@NotBlank
		private String title;
		private String content;
		private List<String> tagNames;
		private List<Long> productSeqList;
	}


	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Getter
	public static class Response {

		private Status status;
		private String title;
		private String content;
		private Integer likeCount;
		private List<BoardTagDto> boardTagDtos;
		private List<BoardProductDto> boardProductDtos;
		public static Response from(BoardDto boardDto, List<BoardTagDto> boardTagDtos,List<BoardProductDto> boardProductDtos) {
			return Response.builder()
				.status(boardDto.getStatus())
				.title(boardDto.getTitle())
				.content(boardDto.getContent())
				.likeCount(boardDto.getLikeCount())
				.boardTagDtos(boardTagDtos)
				.boardProductDtos(boardProductDtos)
				.build();
		}
	}
}
