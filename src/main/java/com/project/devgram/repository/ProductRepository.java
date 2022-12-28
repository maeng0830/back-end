package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByStatus(String status, Pageable pageable);
	List<Product> findAllByStatus(String status);
	List<Product> findTop5ByStatusOrderByHitsDesc(String status);
	List<Product> findTop8ByStatusOrderByLikeCountDesc(String status);

}
