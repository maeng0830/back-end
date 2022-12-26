package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import com.project.devgram.entity.Review;
import com.project.devgram.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review findByUsersAndProduct(Users users, Product product);
	Optional<Review> findByReviewSeq(Long reviewSeq);
	List<Review> findByStatus(String status);
	List<Review> findByReviewSeqAndStatusNot(Long reviewSeq, String status);
}
