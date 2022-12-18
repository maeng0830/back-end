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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/like")
public class ProductLikeController {

	private final ProductLikeService productLikeService;
	@PostMapping // 좋아요 누르기
	public ResponseEntity<ProductLikeDto> productLike(@RequestBody @Valid ProductLikeDto productLikeDto) {
		productLikeService.productLike(productLikeDto);
		return new ResponseEntity<>(productLikeDto, HttpStatus.CREATED);

	}

	@DeleteMapping("/delete") // 좋아요 삭제
	public ResponseEntity<ProductLikeDto> productUnLike(@RequestBody @Valid ProductLikeDto productLikeDto) {
		productLikeService.productUnLike(productLikeDto);
		return new ResponseEntity<>(productLikeDto, HttpStatus.OK);
	}

	@GetMapping("/test3") // 내용 확인용(요부분은 username넣어서 확인하는걸로 수정할예정입니다)
	public List<ProductLikeDto> list(){
		return productLikeService.list();
	}

}
