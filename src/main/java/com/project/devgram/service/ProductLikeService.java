package com.project.devgram.service;

import com.project.devgram.dto.ProductLikeDto;
import com.project.devgram.entity.Product;
import com.project.devgram.entity.ProductLike;
import com.project.devgram.entity.Users;
import com.project.devgram.repository.IProductRepository;
import com.project.devgram.repository.ProductLikeRepository;
import com.project.devgram.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

	private final ProductLikeRepository productLikeRepository;
	private final UserRepository userRepository;
	private final IProductRepository productRepository;
	private Users users;
	private Product product;

	public void productLike(ProductLikeDto productLikeDto) {

		ProductLike productLike = ProductLike.builder()
			.productSeq(productLikeDto.getProductSeq())
			.username(productLikeDto.getUsername())
			.build();
		productLikeRepository.save(productLike);

		product = productRepository.findById(productLike.getProductSeq()).get();
		product.setLike_Count(product.getLike_Count() + 1);
		productRepository.save(product);

	}

	public void productUnLike(ProductLikeDto productLikeDto) {
		Optional<ProductLike> productLike = findProductLikeByUsernameAndProductSeq(productLikeDto);

		productLikeRepository.delete(productLike.get());

		product.setLike_Count(product.getLike_Count() - 1);
		productRepository.save(product);

	}

	public Optional<ProductLike> findProductLikeByUsernameAndProductSeq(
		ProductLikeDto productLikeDto) {
		return productLikeRepository.findProductLikeByUsernameAndProductSeq(
			productLikeDto.getUsername(),
			productLikeDto.getProductSeq());
	}

	public List<ProductLikeDto> list(String username) {
		List<ProductLike> productLikes = productLikeRepository.findAllByUsername(username);
		return ProductLikeDto.of(productLikes);
	}
}
