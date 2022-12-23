package com.project.devgram.controller;

import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Review;
import com.project.devgram.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	@PostMapping("/api/review") // 리뷰 등록
	public boolean write(@RequestBody ReviewDto parameter){
		return  reviewService.ReviewWrite(parameter);

	}
	@GetMapping("/api/review/list") // 전체 리뷰
	public Page<Review> list(Pageable pageable) {
		return reviewService.list(pageable);
	}
}
