package com.project.devgram.repository.impl;

import com.project.devgram.dto.BoardDto;
import com.project.devgram.dto.SearchBoard.Request;
import com.project.devgram.repository.CustomBoardRepository;
import com.project.devgram.type.status.Status;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static com.project.devgram.entity.QBoard.board;

@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardDto> findBy(Request request) {
		return queryFactory.select(Projections.fields(BoardDto.class,
				board.boardSeq,
				board.title,
				board.content,
				board.status,
				board.likeCount)).from(board)
			.where(eqBoardSeq(request.getBoardSeq()), containTitle(request.getTitle()), containContents(request.getContent()),
				eqStatus(request.getStatus()))
			.fetch();
	}

	private BooleanExpression eqBoardSeq(Long boardSeq) {
		if (boardSeq != null) {
			return board.boardSeq.eq(boardSeq);
		}
		return null;
	}

	private BooleanExpression containTitle(String title) {
		if (StringUtils.hasText(title)) {
			return board.title.contains(title);
		}
		return null;
	}

	private BooleanExpression containContents(String contents) {
		if (StringUtils.hasText(contents)) {
			return board.content.contains(contents);
		}
		return null;
	}

	private BooleanExpression eqStatus(Status status) {
		return board.status.eq(Objects.requireNonNullElse(status, Status.NORMAL));
	}

}
