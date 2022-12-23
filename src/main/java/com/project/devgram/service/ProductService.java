package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

	boolean write(ProductDto parameter); // 글 작성(일반)

	Page<Product> confirm(Pageable pageable); // admin 제품관리

	List<ProductDto> list(Pageable pageable); // 전체 list(Approve)

	boolean update(ProductDto parameter); // product 업데이트

	boolean delete(long id); // product 삭제

	List<ProductDto> popularList(); // list(Approve) 인기순 4

	List<ProductDto> bestLikeList();

	ProductDto detail(long id); // product Detail + 조회수증가


}
