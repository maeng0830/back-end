package com.project.devgram.service;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.entity.Category;
import com.project.devgram.repository.ICategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService{

	private final ICategoryRepository categoryRepository;
	private Sort getSortBySortValueDesc() {
		return Sort.by(Sort.Direction.DESC, "order"); // order기준 내림차순 정렬
	}

	@Override
	public List<CategoryDto> list() {
		List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
		return CategoryDto.of(categories);
	}
}
