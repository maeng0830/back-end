package com.project.devgram.repository.impl;

import com.project.devgram.dto.DetailResponse;
import com.project.devgram.dto.SearchBoard.Response;
import com.project.devgram.repository.CustomBoardRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.project.devgram.entity.QBoard.board;
import static com.project.devgram.entity.QUsers.users;

@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Response> findBy(Pageable pageable, String sort) {
		List<Response> responseList = queryFactory.select(Projections.fields(Response.class,
				board.boardSeq.as("id"),
				board.title,
				board.content,
				board.createdAt,
				board.createdBy,
				users.userSeq.as("createdByUserSeq"),
				board.status,
				board.likeCount))
			.from(board)
			.leftJoin(users)
			.on(board.createdBy.eq(users.username))
			.where()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(createSort(sort))
			.fetch();

		Long count = queryFactory.select(board.count())
			.from(board)
			.fetchOne();

		return new PageImpl<>(responseList, pageable, count);
	}

	@Override
	public Page<Response> findByFollowerUserSeq(Pageable pageable, List<Long> followerList) {
		List<Response> responseList = queryFactory.select(Projections.fields(Response.class,
				board.boardSeq.as("id"),
				board.title,
				board.content,
				board.createdAt,
				board.createdBy,
				users.userSeq.as("createdByUserSeq"),
				board.status,
				board.likeCount))
			.from(board)
			.leftJoin(users)
			.on(board.createdBy.eq(users.username))
			.where(users.userSeq.in(followerList))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(board.boardSeq.desc())
			.fetch();

		Long count = queryFactory.select(board.count())
			.from(board)
			.leftJoin(users)
			.on(board.createdBy.eq(users.username))
			.where(users.userSeq.in(followerList))
			.fetchOne();

		return new PageImpl<>(responseList, pageable, count);
	}

	@Override
	public DetailResponse findDetailByBoardSeq(Long boardSeq) {
		return queryFactory.select(Projections.fields(DetailResponse.class,
				board.title,
				board.bestProduct.as("productsBest"),
				board.selfIntroduce.as("intro"),
				board.otherProduct.as("productsRecommend"),
				board.recommendReason.as("productsRecommendReason"),
				board.content.as("last"),
				users.userSeq.as("createdBySeq")
				))
			.from(board)
			.leftJoin(users)
			.on(board.createdBy.eq(users.username))
			.where(board.boardSeq.eq(boardSeq))
			.fetchOne();
	}

	private OrderSpecifier<?> createSort(String sort) {
		if (Objects.equals(sort, "popular")) {
			return board.likeCount.desc();
		} else {
			return board.boardSeq.desc();
		}
	}

}
