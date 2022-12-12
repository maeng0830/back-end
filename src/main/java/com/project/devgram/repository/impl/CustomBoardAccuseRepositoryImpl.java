package com.project.devgram.repository.impl;

import com.project.devgram.dto.BoardAccuseDto;
import com.project.devgram.dto.SearchBoardAccuse.Request;
import com.project.devgram.repository.CustomBoardAccuseRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import static com.project.devgram.entity.QBoardAccuse.boardAccuse;

@RequiredArgsConstructor
public class CustomBoardAccuseRepositoryImpl implements CustomBoardAccuseRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardAccuseDto> findBy(Request request) {
		return queryFactory.select(Projections.fields(BoardAccuseDto.class,
			boardAccuse.boardAccuseSeq,
			boardAccuse.content,
			boardAccuse.user.username,
			boardAccuse.board.title,
			boardAccuse.board.boardSeq,
			boardAccuse.user.userSeq
		)).from(boardAccuse).where(isBoardAccuseSeqSame(request.getBoardAccuseSeq()),
			isBoardSeqSame(request.getBoardSeq()),
			isUserSeqSame(request.getUserSeq()),
			containsContent(request.getContent()),
			isReporterNameSame(request.getReporterName())).fetch();
	}

	private BooleanExpression isBoardAccuseSeqSame(Long boardAccuseSeq) {
		if (boardAccuseSeq != null) {
			return boardAccuse.boardAccuseSeq.eq(boardAccuseSeq);
		}
		return null;
	}

	private BooleanExpression isBoardSeqSame(Long boardSeq) {
		if (boardSeq != null) {
			return boardAccuse.board.boardSeq.eq(boardSeq);
		}
		return null;
	}

	private BooleanExpression isUserSeqSame(Long userSeq) {
		if (userSeq != null) {
			return boardAccuse.user.userSeq.eq(userSeq);
		}
		return null;
	}

	private BooleanExpression containsContent(String content) {
		if (StringUtils.hasText(content)) {
			//TODO: %'expression'% ===> fullScan을 사용하기때문에 개선 필요.
			return boardAccuse.content.contains(content);
		}
		return null;
	}

	private BooleanExpression isReporterNameSame(String reporterName) {
		if (StringUtils.hasText(reporterName)) {
			return boardAccuse.user.username.eq(reporterName);
		}
		return null;
	}

}
