package com.project.devgram.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Board extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "board_seq", nullable = false)
	private Long boardSeq;

	@Column(length = 100, name= "title")
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(length = 10)
	private String status;

	private Integer likeCount;
}
