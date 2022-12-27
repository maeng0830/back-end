package com.project.devgram.dto;

import com.project.devgram.entity.Board;
import com.project.devgram.type.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private Long boardSeq;
	private String title;
	private String precautions;
	private String selfIntroduce;
	private String recommendReason;
	private String bestProduct;
	private String otherProduct;
	private String content;
	private Status status;
	private String imageUrl;
	private Integer likeCount;

	public static BoardDto fromEntity(Board board){
		return BoardDto.builder()
			.boardSeq(board.getBoardSeq())
			.title(board.getTitle())
			.precautions(board.getPrecautions())
			.selfIntroduce(board.getSelfIntroduce())
			.recommendReason(board.getRecommendReason())
			.bestProduct(board.getBestProduct())
			.otherProduct(board.getOtherProduct())
			.content(board.getContent())
			.status(board.getStatus())
			.likeCount(board.getLikeCount())
			.imageUrl(board.getImageUrl())
			.build();
	}
}
