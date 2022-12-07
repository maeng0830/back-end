package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.IProductRepository;
import com.project.devgram.type.ProductCode;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductRepository repository;

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
			if (result == true){
				System.out.println("write Test Success");
			}else {
				System.out.println("write Test Fail");
			}
		}
	}

	@Test
	@DisplayName("Product Admin List")
	void confirm() {

		List<Product> products = repository.findAll();

		if (products.isEmpty()){
			System.out.println("List is Empty");
		}else {
			System.out.println("List is not Empty");
		}
	}

	@Test
	@DisplayName("product Status Update Test")
	void updateStatus() {

		boolean result = productService.updateStatus(1L,ProductCode.STATUS_APPROVE);
		if (result == true ){
			System.out.println("Update Test Success");
		}else {
			System.out.println("Update Test Fail");
		}

	}
}