package com.project.devgram.service;

import com.project.devgram.dto.ReviewDto;
import com.project.devgram.entity.Product;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.Users;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.ReviewErrorCode;
import com.project.devgram.repository.ProductRepository;
import com.project.devgram.repository.ReviewRepository;
import com.project.devgram.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	public boolean ReviewWrite(ReviewDto parameter) {
		Users users = userRepository.findByUsername(parameter.getUsername()).orElse(null);
		if (users == null) {
			throw new DevGramException(ReviewErrorCode.USER_NOT_EXIST); // 사용자 존재X
		}

		Product product = productRepository.findById(parameter.getProductSeq()).orElse(null);
		if (product == null) {
			throw new DevGramException(ReviewErrorCode.PRODUCT_NOT_EXIST); // 제품 존재X
		}

		Review review = reviewRepository.findByUsersAndProduct(users, product);
		if (review != null){
			throw new DevGramException(ReviewErrorCode.ALREADY_REVIEW); // 이미 리뷰 등록됨
		}

		review = Review.builder()
			.content(parameter.getContent())
			.mark(parameter.getMark())
			.status(Review.STATUS_APPROVE) // 최초 승인상태로
			.createdAt(LocalDateTime.now())
			.users(users)
			.product(product)
			.build();

		reviewRepository.save(review);

		product.setRating((product.getRating() + parameter.getMark())); // 기존값 mark  + mark ...++ 평균치를 어떻게 낼지????
		productRepository.save(product);
		product.addReview(review);
		return true;

	}

	public Page<Review> list(Pageable pageable) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page, 5, Sort.by(Direction.DESC, "reviewSeq"));
		return reviewRepository.findAll(pageable);
	}
}
