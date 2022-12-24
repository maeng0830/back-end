package com.project.devgram.service;

import com.project.devgram.dto.ReviewAccuseDto;
import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.ReviewAccuse;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.ReviewAccuseErrorCode;
import com.project.devgram.repository.ReviewAccuseRepository;
import com.project.devgram.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewAccuseService {

	private final ReviewAccuseRepository reviewAccuseRepository;
	private final ReviewRepository reviewRepository;
	public static final Sort sortByCreatedAtDesc = Sort.by(Direction.DESC, "reportAt");

	public ReviewAccuseDto reviewAccuse(ReviewAccuseDto parameter) { // 댓글 신고

		Review review = reviewRepository.findByReviewSeq(parameter.getReviewSeq()).orElse(null);
		if (review == null) {
			throw new DevGramException(ReviewAccuseErrorCode.REVIEW_NOT_EXIST); // 리뷰 존재 X
		}

		if (review.getStatus().equals(Review.STATUS_APPROVE)) { // 정상 상태면
			review.setStatus(Review.STATUS_DECLARATION); // 신고상태로 변경
			reviewRepository.save(review); // 리뷰상태 업데이트
		} else if (review.getStatus().equals(Review.STATUS_DECLARATION)) { // 이미 신고했으면
			throw new DevGramException(ReviewAccuseErrorCode.ALREADY_REVIEW);
		} else if (review.getStatus().equals(Review.STATUS_DELETE)) { // 만약 삭제 상태면
			throw new DevGramException(ReviewAccuseErrorCode.ALREADY_REVIEW_DELETE);
		}

		ReviewAccuse reviewAccuse = ReviewAccuse.builder()
			.review(review)
			.content(parameter.getContent())
			.reportAt(LocalDateTime.now())
			.build();

		return ReviewAccuseDto.of(reviewAccuseRepository.save(reviewAccuse));
	}

	public List<ReviewDto> accuseReviewList() { // 신고된 리뷰 확인(리뷰내용, 작성시간, 상태, 작성자)

		List<Review> reviewList = reviewRepository.findByStatus(Review.STATUS_DECLARATION);

		if (reviewList.isEmpty()) {
			throw new DevGramException(ReviewAccuseErrorCode.REVIEW_ACCUSE_NOT_EXIST);
		}

		ArrayList<ReviewDto> reviewDtos = new ArrayList<>();
		for (Review review : reviewList) {
			reviewDtos.add(ReviewDto.of(review));
		}
		return reviewDtos;
	}

	public boolean updateReviewStatus(Long reviewSeq, ReviewDto parameter){ // 리뷰 STATUS 변경(관리자)
		Review review = reviewRepository.findByReviewSeq(reviewSeq)
			.orElseThrow(() -> new DevGramException(ReviewAccuseErrorCode.REVIEW_NOT_EXIST));

		review.setStatus(parameter.getStatus());
		reviewRepository.save(review);

		return true;
	}

	public List<ReviewAccuseDto> accuseReviewDetail(Long reviewSeq){// reviewSeq에 따른 Detail 조회(신고자, 신고내용, 신고일자)
		List<ReviewAccuse> reviewAccuseList = reviewAccuseRepository.findByReviewReviewSeq(reviewSeq, sortByCreatedAtDesc);

		return ReviewAccuseDto.of(reviewAccuseList);

	}

}
