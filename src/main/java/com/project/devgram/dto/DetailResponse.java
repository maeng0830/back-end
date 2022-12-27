package com.project.devgram.dto;

import com.project.devgram.dto.SearchBoard.ProductElement;
import java.util.List;
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
public class DetailResponse {

	private String intro;
	private String title;
	private String productsBest;
	private String productsRecommend;
	private String productsRecommendReason;
	private String last;
	private Long tagCount;
	private Long createdBySeq;
	private String imageUrl;
	private List<ProductElement> productUsed;
}
