package com.project.devgram.repository;

import com.project.devgram.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByStatus(String status); // 상태조건 find

}
