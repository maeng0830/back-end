package com.project.devgram.controller;

import com.project.devgram.dto.ReviewAccuseDto;
import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Review;
import com.project.devgram.service.ReviewAccuseService;
import com.project.devgram.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewAccuseService reviewAccuseService;
	@PostMapping("/api/review") // 리뷰 등록
	public boolean write(@RequestBody ReviewDto parameter){
		return  reviewService.ReviewWrite(parameter);

	}
	@GetMapping("/api/review/list") // 전체 리뷰(관리자 / 페이지 limit5) 필요한가?
	public Page<Review> list(Pageable pageable) {
		return reviewService.list(pageable);
	}

// <--------NEW Code start-------->
	@PostMapping("/api/review/accuse") // 리뷰 신고
	public ReviewAccuseDto reviewAccuse(@RequestBody ReviewAccuseDto parameter){
		return reviewAccuseService.reviewAccuse(parameter);
	}

	@GetMapping("/api/review/accuse/list") // 리뷰 신고 list
	public List<ReviewDto> AccuseReviewList(){
		return reviewAccuseService.accuseReviewList();
	}

	@PostMapping("/api/review/accuse/statusUpdate") // 리뷰 status 변경(관리자)
	public boolean reviewStatusUpdate(@RequestBody ReviewDto parameter){
		return reviewAccuseService.updateReviewStatus(parameter.getReviewSeq(), parameter);
	}

	@GetMapping("/api/review/accuse/detail") // reviewSeq에 따른 신고 목록 조회
	public List<ReviewAccuseDto> AccuseReviewDetail(@RequestParam Long reviewSeq){
		return reviewAccuseService.accuseReviewDetail(reviewSeq);
	}
}
