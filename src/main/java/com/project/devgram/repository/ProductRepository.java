package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByStatus(String status, Pageable pageable); // 상태조건 find + paging 5
	List<Product> findTop4ByStatusOrderByHitsDesc(String status); // (from product where status=? orderBy hits desc limit 4) // 조건 + 조회기준 내림차순
}
