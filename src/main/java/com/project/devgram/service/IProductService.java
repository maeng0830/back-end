package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.dto.ProductDto;
import java.util.List;

public interface IProductService {

	boolean write(ProductDto parameter); // 글 작성

	List<ProductDto> confirm(); // admin 제품관리

	boolean updateStatus(Long productSeq, String status); // 제품 status 변경
}
