package com.project.devgram.dto;

import com.project.devgram.entity.ProductLike;
import java.util.ArrayList;
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
public class ProductLikeDto {
	private String username;
	private Long productSeq;
	public static List<ProductLikeDto> of(List<ProductLike> productLikes) {
		if (productLikes != null) {
			List<ProductLikeDto> productLikeList = new ArrayList<>();
			for (ProductLike x : productLikes) {
				productLikeList.add(of(x));
			}
			return productLikeList;
		}
		return null;
	}
	public static ProductLikeDto of(ProductLike productLike){
		return ProductLikeDto.builder()
			.productSeq(productLike.getProductSeq())
			.username(productLike.getUsername())
			.build();
	}
}
