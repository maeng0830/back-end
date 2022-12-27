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
		private String precautions;
		private String selfIntroduce;
		private String recommendReason;
		private String bestProduct;
		private String otherProduct;
		private String content;
		private String imageUrl;
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
		private String precautions;
		private String selfIntroduce;
		private String recommendReason;
		private String bestProduct;
		private String otherProduct;
		private String content;
		private String imageUrl;
		private Integer likeCount;
		private List<BoardTagDto> boardTagDtos;
		private List<BoardProductDto> boardProductDtos;
		public static Response from(BoardDto boardDto, List<BoardTagDto> boardTagDtos,List<BoardProductDto> boardProductDtos) {
			return Response.builder()
				.status(boardDto.getStatus())
				.title(boardDto.getTitle())
				.precautions(boardDto.getPrecautions())
				.selfIntroduce(boardDto.getSelfIntroduce())
				.recommendReason(boardDto.getRecommendReason())
				.bestProduct(boardDto.getBestProduct())
				.otherProduct(boardDto.getOtherProduct())
				.content(boardDto.getContent())
				.likeCount(boardDto.getLikeCount())
				.imageUrl(boardDto.getImageUrl())
				.boardTagDtos(boardTagDtos)
				.boardProductDtos(boardProductDtos)
				.build();
		}
	}
}
