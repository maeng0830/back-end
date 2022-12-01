package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import java.util.List;

public interface ICategoryService {

	List<CategoryDto> list(); // 카테고리 목록

	boolean add(CategoryDto parameter); // 카테고리 추가
	boolean update(CategoryDto parameter); // 카테고리 수정
	boolean del(long id); // 카테고리 삭제
}
