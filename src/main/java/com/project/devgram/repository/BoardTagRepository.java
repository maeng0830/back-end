package com.project.devgram.repository;

import com.project.devgram.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> , CustomBoardTagRepository {
	boolean existsByBoard_BoardSeqAndTag_TagSeq(Long boardSeq, Long tagSeq);

}
