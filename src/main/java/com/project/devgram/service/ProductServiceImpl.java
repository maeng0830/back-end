package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.IProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

	private final IProductRepository productRepository;

	@Override
	public boolean write(ProductDto parameter) {

		Product product = Product.builder()
			.category_Seq(parameter.getCategory_Seq())
			.title(parameter.getTitle())
			.content(parameter.getContent())
			.price(parameter.getPrice())
			.hits(0)
			.like_Count(0)
			.rating(0.0)
			.status(Product.STATUS_CHECK)
			.build();

		productRepository.save(product);
		return true;
	}

	@Override
	public List<ProductDto> confirm() {
		List<Product> products = productRepository.findAll();
		return ProductDto.of(products);
	}

	@Override
	public boolean updateStatus(Long productSeq, String status) {

		Optional<Product> optionalProduct = productRepository.findById(productSeq);

		if (!optionalProduct.isPresent()){
			return false;
		}

		Product product = optionalProduct.get();

		product.setStatus(status);
		productRepository.save(product);

		return true;
	}
}
