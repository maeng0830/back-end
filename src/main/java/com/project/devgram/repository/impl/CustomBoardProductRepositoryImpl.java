package com.project.devgram.repository.impl;

import com.project.devgram.dto.SearchBoard.ProductElement;
import com.project.devgram.repository.CustomBoardProductRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.project.devgram.entity.QBoardProduct.boardProduct;
@RequiredArgsConstructor
public class CustomBoardProductRepositoryImpl implements CustomBoardProductRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ProductElement> findByBoardSeq(Long boardSeq) {
		return queryFactory.select(Projections.fields(ProductElement.class,
			boardProduct.product.productSeq.as("id"),
			boardProduct.product.title,
			boardProduct.product.hits
			)).from(boardProduct)
			.where(boardProduct.board.boardSeq.eq(boardSeq)).fetch();
	}
}
