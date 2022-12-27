package com.project.devgram.repository;

import com.project.devgram.dto.SearchBoard.ProductElement;
import java.util.List;

public interface CustomBoardProductRepository {
	List<ProductElement> findByBoardSeq(Long id);
}
