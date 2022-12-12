package com.project.devgram.repository;

import com.project.devgram.entity.BoardAccuse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardAccuseRepository extends JpaRepository<BoardAccuse, Long>,CustomBoardAccuseRepository {

	Optional<BoardAccuse> findByBoard_BoardSeqAndUser_UserSeq(Long boardSeq, Long userSeq);
}
