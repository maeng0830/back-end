package com.project.devgram.dto;

import com.project.devgram.entity.BoardProduct;
import com.project.devgram.entity.Category;
import com.project.devgram.entity.Product;
import com.project.devgram.type.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardProductDto {

	private Long boardSeq;
	private Long productSeq;
	//category
	private String categoryColor;
	private String categoryName;
	//product
	private String content;
	private Integer hits;
	private Integer likeCount;
	private Double price;
	private Double rating;
	private String status;
	private String title;

	public static BoardProductDto from(BoardProduct boardProduct, Category category, Product product) {
		return BoardProductDto.builder()
			.boardSeq(boardProduct.getBoard().getBoardSeq())
			.productSeq(boardProduct.getProduct().getProductSeq())
			.categoryColor(category.getColor())
			.categoryName(category.getName())
			.content(product.getContent())
			.hits(product.getHits())
			.likeCount(product.getLikeCount())
			.price(product.getPrice())
			.rating(product.getRating())
			.status(product.getStatus())
			.title(product.getTitle())
			.build();
	}
}
