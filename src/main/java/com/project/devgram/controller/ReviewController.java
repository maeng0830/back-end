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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewAccuseService reviewAccuseService;
	@PostMapping// 리뷰 등록
	public boolean write(@RequestBody ReviewDto parameter){
		return  reviewService.ReviewWrite(parameter);

	}
	@GetMapping("/adminList") // 전체 리뷰(혹시 모를 관리자용..!)
	public Page<Review> adminList(Pageable pageable) {
		return reviewService.adminList(pageable);
	}
	@GetMapping("/list")// seq에 따른 목록
	public List<ReviewDto> list(@RequestParam Long reviewSeq){
		return reviewService.Reviewlist(reviewSeq);
	}

	@PostMapping("/update") // 리뷰 수정
	public boolean reviewUpdate(@RequestBody ReviewDto parameter){
		return reviewService.updateReview(parameter);
	}

	@PostMapping("/delete") // 리뷰 삭제
	public boolean reviewDelete(@RequestBody ReviewDto parameter){
		return reviewService.deleteReview(parameter);
	}

	@PostMapping("/accuse") // 리뷰 신고
	public ReviewAccuseDto reviewAccuse(@RequestBody ReviewAccuseDto parameter){
		return reviewAccuseService.reviewAccuse(parameter);
	}

	@GetMapping("/accuse/list") // 리뷰 신고 list(관리자)
	public List<ReviewDto> AccuseReviewList(){
		return reviewAccuseService.accuseReviewList();
	}

	@PostMapping("/accuse/statusUpdate") // 리뷰 status 변경(관리자)
	public boolean reviewStatusUpdate(@RequestBody ReviewDto parameter){
		return reviewAccuseService.updateReviewStatus(parameter.getReviewSeq(), parameter);
	}

	@GetMapping("/accuse/detail") // reviewSeq에 따른 신고 목록 조회
	public List<ReviewAccuseDto> AccuseReviewDetail(@RequestParam Long reviewSeq){
		return reviewAccuseService.accuseReviewDetail(reviewSeq);
	}
}
