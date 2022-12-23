package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceImplTest {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository repository;

	@Test
	@DisplayName("Product Write Test")
	void write() {

		ProductDto parameter = ProductDto.builder()
			.product_Seq(1L)
			.title("테스트1")
			.content("테스트 내용입니다.")
			.rating(4.5)
			.status(Product.STATUS_CHECK)
			.hits(0)
			.like_Count(0)
			.price(150000)
			.build();

		boolean result = productService.write(parameter);
		{
			if (result == true) {
				System.out.println("write Test Success");
			} else {
				System.out.println("write Test Fail");
			}
		}
	}

	@Test
	@DisplayName("Product Admin List")
	void confirm() {

		List<Product> products = repository.findAll();

		if (products.isEmpty()) {
			System.out.println("List is Empty");
		} else {
			System.out.println("List is not Empty");
		}
	}

/*	@Test
	@DisplayName("Product List - Approve")
	void list() {

		List<Product> products = repository.findAllByStatus(Product.STATUS_APPROVE);

		if (products.isEmpty()) {
			System.out.println("Test Success");
		} else {
			System.out.println("Test Fail");
		}
	}*/

	@Test
	@DisplayName("Product Delete Test")
	void delete() {
		ProductDto parameter = ProductDto.builder()
			.product_Seq(1L).build();
		boolean result = productService.delete(parameter.getProduct_Seq());
		if (result == true) {
			System.out.println("Delete Test Success");
		} else {
			System.out.println("Delete Test Fail");
		}
	}

	@Test
	@DisplayName("Product PopularList Test")
	void popularList() {

		List<Product> products = repository.findTop4ByStatusOrderByHitsDesc(Product.STATUS_APPROVE);

		if (products.isEmpty()) {
			System.out.println("List is Empty");
		} else {
			System.out.println("List is not Empty");
		}
	}
}