package com.project.devgram.controller;

import com.project.devgram.dto.ProductLikeDto;
import com.project.devgram.service.ProductLikeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/like")
public class ProductLikeController {

	private final ProductLikeService productLikeService;
	@PostMapping // 좋아요 누르기 + likeCount 증가
	public ResponseEntity<ProductLikeDto> productLike(@RequestBody @Valid ProductLikeDto productLikeDto) {
		productLikeService.productLike(productLikeDto);
		return new ResponseEntity<>(productLikeDto, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete") // 좋아요 삭제 + likeCount 감소
	public ResponseEntity<ProductLikeDto> productUnLike(@RequestBody @Valid ProductLikeDto productLikeDto) {
		productLikeService.productUnLike(productLikeDto);
		return new ResponseEntity<>(productLikeDto, HttpStatus.OK);
	}

	@GetMapping("/list") // username 에 따른 목록 반환
	public List<ProductLikeDto> list(@RequestParam String username){
		return productLikeService.list(username);
	}

}
