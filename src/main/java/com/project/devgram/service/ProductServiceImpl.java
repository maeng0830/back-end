package com.project.devgram.service;

import com.project.devgram.dto.ProductDto;
import com.project.devgram.entity.Product;
import com.project.devgram.repository.ProductRepository;
import com.project.devgram.repository.ProductLikeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

	private final ProductRepository productRepository;
	private final ProductLikeRepository productLikeRepository;

	@Override
	public boolean write(ProductDto parameter) {

		Product product = Product.builder()
			.category_Seq(parameter.getCategory_Seq())
			.title(parameter.getTitle())
			.content(parameter.getContent())
			.price(parameter.getPrice())
			.hits(0)
			.likeCount(0)
			.rating(0.0)
			.status(Product.STATUS_CHECK)
			.build();

		productRepository.save(product);
		return true;
	}

	@Override
	public Page<Product> confirm(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 5, Sort.by(Direction.DESC, "productSeq"));
		return productRepository.findAll(pageable);
	}

	@Override
	public List<ProductDto> list(Pageable pageable) {
		List<Product> products = productRepository.findAllByStatus(Product.STATUS_APPROVE, pageable);
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
		product.setStatus(parameter.getStatus());
		productRepository.save(product);

		return true;
	}

	@Override
	public boolean delete(long id) {

		productRepository.deleteById(id);
		return true;
	}

	@Override
	public List<ProductDto> popularList() {

		List<Product> products = productRepository.findTop5ByStatusOrderByHitsDesc(Product.STATUS_APPROVE);
		return ProductDto.of(products);
	}

	@Override
	public List<ProductDto> bestLikeList() {

		List<Product> products = productRepository.findTop5ByStatusOrderByLikeCountDesc(Product.STATUS_APPROVE);
		return ProductDto.of(products);
	}

	@Override
	public ProductDto detail(long id) {
		Product product = productRepository.findById(id).get();

		product.setHits(product.getHits() + 1);
		productRepository.save(product);

		return ProductDto.of(product);
	}


}
