package com.project.devgram.repository;

import com.project.devgram.dto.DetailResponse;
import com.project.devgram.dto.SearchBoard.Response;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBoardRepository {

	Page<Response> findBy(Pageable pageable, String sort, List<Long> tagSeqList);

	Page<Response> findByFollowerUserSeq(Pageable pageable, List<Long> followerList, List<Long> tagSeqList);

	DetailResponse findDetailByBoardSeq(Long boardSeq);
}
