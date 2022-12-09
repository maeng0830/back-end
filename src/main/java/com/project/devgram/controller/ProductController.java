package com.project.devgram.controller;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.service.IProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final IProductService productService;

	@PostMapping("/api/products") // 일반회원 글작성
	public boolean write(@RequestBody ProductDto parameter) {

		return productService.write(parameter);
	}

	@GetMapping("/api/products/admin") // admin 페이지 - 전체조회
	public List<ProductDto> confirm() {
		return productService.confirm();
	}

	@GetMapping("/api/products/list") // 일반 - 제품목록 list
	public List<ProductDto> list(String status) {
		return productService.list();
	}

	@PostMapping("/api/products/update") // admin - 제품 수정
	public boolean update(@RequestBody ProductDto parameter){

		return productService.update(parameter);

	}
	@PostMapping("/api/products/delete") // admin - 제품 삭제
	public boolean delete(@RequestBody ProductDto parameter){

		return productService.delete(parameter.getProduct_Seq());

	}
	@GetMapping("/api/products/popular") // 일반 - 인기제품 list
	public List<ProductDto> popularList(){
		return productService.popularList();
	}

}
