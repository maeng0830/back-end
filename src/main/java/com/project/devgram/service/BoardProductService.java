package com.project.devgram.service;

import com.project.devgram.dto.BoardProductDto;
import com.project.devgram.entity.Board;
import com.project.devgram.entity.BoardProduct;
import com.project.devgram.entity.Category;
import com.project.devgram.entity.Product;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.CategoryErrorCode;
import com.project.devgram.exception.errorcode.ProductErrorCode;
import com.project.devgram.repository.BoardProductRepository;
import com.project.devgram.repository.CategoryRepository;
import com.project.devgram.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardProductService {

	private final BoardProductRepository boardProductRepository;
	private final ProductRepository productRepository;

	private final CategoryRepository categoryRepository;

	public List<BoardProductDto> registerBoardProduct(Board board, List<Long> productSeqList) {
		List<BoardProductDto> boardProductDtoList = new ArrayList<>();
		for (Long productSeq : productSeqList) {
			Product product = productRepository.findById(productSeq)
				.orElseThrow(() -> new DevGramException(ProductErrorCode.CANNOT_FIND_PRODUCT_BY_PRODUCT_SEQ));

			Category category = categoryRepository.findById(product.getCategory_Seq()).orElseThrow(() -> new DevGramException(
				CategoryErrorCode.CANNOT_FIND_CATEGORY_BY_CATEGORY_SEQ));

			BoardProduct boardProduct = boardProductRepository.save(BoardProduct.builder().product(product)
				.board(board).build());

			boardProductDtoList.add(BoardProductDto.from(boardProduct, category, product));
		}
		return boardProductDtoList;
	}
}
