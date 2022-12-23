package com.project.devgram.dto;


import com.project.devgram.entity.Product;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {

	long product_Seq;
	String title;
	String content;
	Integer hits;
	double rating;
	Integer likeCount;
	double price;

	String status; // 상태

	long category_Seq; // 카테고리 id

	int reviewCount;
	double totalRating;

	public static List<ProductDto> of(List<Product> products) {
		if (products != null) {
			List<ProductDto> productList = new ArrayList<>();
			for (Product x : products) {
				productList.add(of(x));
			}
			return productList;
		}
		return null;
	}

	public static ProductDto of(Product product) {
		return ProductDto.builder()
			.product_Seq(product.getProductSeq())
			.category_Seq(product.getCategory_Seq())
			.title(product.getTitle())
			.content(product.getContent())
			.hits(product.getHits())
			.rating(product.getRating())
			.likeCount(product.getLikeCount())
			.price(product.getPrice())
			.status(product.getStatus())
			.build();

	}

}
