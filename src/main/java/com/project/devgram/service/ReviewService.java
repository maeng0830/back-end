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
			throw new DevGramException(ReviewErrorCode.USER_NOT_EXIST);
		}

		Product product = productRepository.findById(parameter.getProductSeq()).orElse(null);
		if (product == null) {
			throw new DevGramException(ReviewErrorCode.PRODUCT_NOT_EXIST);
		}

		Review review = reviewRepository.findByUsersAndProduct(users, product);

		if (review != null) {
			throw new DevGramException(ReviewErrorCode.ALREADY_REVIEW);
		}

		review = Review.builder()
			.content(parameter.getContent())
			.mark(parameter.getMark())
			.status(Review.STATUS_APPROVE)
			.createdAt(LocalDateTime.now())
			.product(product)
			.build();

		reviewRepository.save(review);
		product.setReviewCount(product.getReviewCount() + 1);
		product.setTotalRating(product.getTotalRating() + parameter.getMark());
		product.setRating(product.getTotalRating() / product.getReviewCount());
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
