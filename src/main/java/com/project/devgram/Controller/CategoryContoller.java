package com.project.devgram.Controller;

import com.project.devgram.dto.CategoryDto;
import com.project.devgram.service.ICategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoryContoller {

	private final ICategoryService categoryService;

	@GetMapping("/api/categories")
	public String list(Model model){

		List<CategoryDto> list = categoryService.list();
		model.addAttribute("list", list); // view page

		return "/api/categories";
	}

}
