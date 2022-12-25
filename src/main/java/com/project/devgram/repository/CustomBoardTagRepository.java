package com.project.devgram.repository;

import java.util.List;

public interface CustomBoardTagRepository {

	List<String> getTagNameByBoardSeq(Long boardSeq);

}
