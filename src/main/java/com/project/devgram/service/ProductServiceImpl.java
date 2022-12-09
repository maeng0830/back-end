package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.IProductRepository;
import com.project.devgram.type.ProductCode;
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

	@Override
	public List<ProductDto> list() {
		List<Product> products = productRepository.findAllByStatus(ProductCode.STATUS_APPROVE); // 승인된 건만 찾기
		return ProductDto.of(products);
	}

	@Override
	public boolean update(ProductDto parameter) {

		Long product_Seq = parameter.getProduct_Seq();

		Optional<Product> optionalProduct = productRepository.findById(product_Seq);
		if (!optionalProduct.isPresent()){
			return false;
		}

		Product product = optionalProduct.get();

		product.setPrice(parameter.getPrice());
		product.setTitle(parameter.getTitle());
		product.setContent(parameter.getContent());
		//product.setStatus(parameter.getStatus()); -> 여기서 status를 같이 바꾸는게 맞는지...
		productRepository.save(product);

		return true;
	}


}
