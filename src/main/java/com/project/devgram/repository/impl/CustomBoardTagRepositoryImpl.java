package com.project.devgram.repository.impl;

import com.project.devgram.repository.CustomBoardTagRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.project.devgram.entity.QBoardTag.boardTag;

@RequiredArgsConstructor
public class CustomBoardTagRepositoryImpl implements CustomBoardTagRepository {

	private final JPAQueryFactory queryFactory;

	public List<String> getTagNameByBoardSeq(Long boardSeq) {
		return queryFactory.select(boardTag.tag.name)
			.from(boardTag)
			.where(boardTag.board.boardSeq.eq(boardSeq))
			.fetch();
	}

}
