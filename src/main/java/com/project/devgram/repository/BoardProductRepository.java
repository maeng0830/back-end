package com.project.devgram.repository;

import com.project.devgram.entity.BoardProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardProductRepository extends JpaRepository<BoardProduct, Long>, CustomBoardProductRepository {

}
