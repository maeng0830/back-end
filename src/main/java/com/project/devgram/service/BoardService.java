package com.project.devgram.service;

import com.project.devgram.dto.RegisterBoard;
import com.project.devgram.dto.SearchBoard.Response;
import com.project.devgram.dto.UpdateBoard;
import com.project.devgram.dto.DetailResponse;
import com.project.devgram.entity.Board;
import com.project.devgram.dto.BoardDto;
import com.project.devgram.entity.Follow;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardErrorCode;
import com.project.devgram.repository.BoardProductRepository;
import com.project.devgram.repository.BoardRepository;
import com.project.devgram.repository.BoardTagRepository;
import com.project.devgram.repository.CommentRepository;
import com.project.devgram.repository.FollowRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;
	private final BoardTagRepository boardTagRepository;
	private final BoardProductRepository boardProductRepository;

	private final FollowRepository followRepository;

	public Board registerBoard(RegisterBoard.Request request) {
		return boardRepository.save(Board
			.builder()
			.content(request.getContent())
			.title(request.getTitle())
			.bestProduct(request.getBestProduct())
			.otherProduct(request.getOtherProduct())
			.recommendReason(request.getRecommendReason())
			.selfIntroduce(request.getSelfIntroduce())
			.precautions(request.getPrecautions())
			.build());
	}

	@Transactional(readOnly = true)
	public Page<Response> searchBoards(Pageable pageable, String sort) {
		Page<Response> responsePage = boardRepository.findBy(pageable, sort);
		for (Response res : responsePage.getContent()) {
			res.setCommentsCount(commentRepository.countByBoard_BoardSeq(res.getId()));
			res.setTags(boardTagRepository.getTagNameByBoardSeq(res.getId()));
			res.setProducts(boardProductRepository.findByBoardSeq(res.getId()));
		}
		return responsePage;
	}

	@Transactional(readOnly = true)
	public Page<Response> searchFollowingBoards(Pageable pageable) {
		//TODO: 로그인 완료시 해당 부분 변경
		long userSeq = 1L;
		List<Follow> followList = followRepository.findByFollower_UserSeqOrderByFollower(userSeq);
		List<Long> followerList = followList.stream().map(Follow -> Follow.getFollowing().getUserSeq()).collect(Collectors.toList());

		Page<Response> responsePage = boardRepository.findByFollowerUserSeq(pageable, followerList);
		for (Response res : responsePage.getContent()) {
			res.setCommentsCount(commentRepository.countByBoard_BoardSeq(res.getId()));
			res.setTags(boardTagRepository.getTagNameByBoardSeq(res.getId()));
			res.setProducts(boardProductRepository.findByBoardSeq(res.getId()));
		}

		return responsePage;
	}

	@Transactional
	public BoardDto updateBoard(UpdateBoard.Request request) {
		Board board = getBoard(request.getBoardSeq());

		board.updateTitle(request.getTitle());
		board.updateContent(request.getContent());

		return BoardDto.fromEntity(board);
	}

	@Transactional
	public void deleteBoard(Long boardSeq) {
		Board board = getBoard(boardSeq);
		board.deleteBoard();
	}

	public Board getBoard(Long boardSeq) {
		return boardRepository.findById(boardSeq).orElseThrow(() ->
			new DevGramException(BoardErrorCode.CANNOT_FIND_BOARD_BY_BOARDSEQ));
	}

	@Transactional(readOnly = true)
	public DetailResponse searchBoardDetail(Long boardSeq) {
		DetailResponse detailResponse = boardRepository.findDetailByBoardSeq(boardSeq);
		detailResponse.setProductUsed(boardProductRepository.findByBoardSeq(boardSeq));
		detailResponse.setTagCount(commentRepository.countByBoard_BoardSeq(boardSeq));
		return detailResponse;
	}
}