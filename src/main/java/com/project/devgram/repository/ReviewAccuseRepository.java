package com.project.devgram.repository;

import com.project.devgram.entity.ReviewAccuse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewAccuseRepository extends JpaRepository<ReviewAccuse, Long> {

	Optional<ReviewAccuse> findTop1ByReviewReviewSeq(Long reviewSeq, Sort sort);

	List<ReviewAccuse> findByReviewReviewSeq(Long reviewSeq, Sort sort);
}
