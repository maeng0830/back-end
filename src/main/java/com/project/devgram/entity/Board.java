package com.project.devgram.entity;

import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.BoardErrorCode;
import com.project.devgram.type.status.Status;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_seq", nullable = false)
	private Long boardSeq;

	@Column(length = 100, name = "title")
	private String title;

	@Column(length = 2000, name = "precautions")
	private String precautions;

	@Column(length = 2000, name = "self_introduce")
	private String selfIntroduce;

	@Column(length = 2000, name = "recommend_reason")
	private String recommendReason;

	@Column(length = 2000, name = "best_product")
	private String bestProduct;

	@Column(length = 2000, name = "other_product")
	private String otherProduct;

	@Column(length = 2000, name = "content")
	private String content;

	@Column(length = 10)
	private Status status;

	@Column(name = "like_count")
	private Integer likeCount;

	@Builder
	public Board(String title, String content, String precautions, String selfIntroduce , String recommendReason, String bestProduct, String otherProduct) {
		this.title = title;
		this.precautions = precautions;
		this.selfIntroduce = selfIntroduce;
		this.recommendReason = recommendReason;
		this.bestProduct = bestProduct;
		this.otherProduct = otherProduct;
		this.content = content;
		this.status = Status.NORMAL;
		this.likeCount = 0;
	}

	public void deleteBoard() {
		this.status = Status.DELETED;
	}

	public void updateTitle(String title) {
		checkIsDeleted();
		if (StringUtils.hasText(title)) {
			this.title = title;
		}
	}

	public void updateContent(String content) {
		checkIsDeleted();
		if (StringUtils.hasText(content)) {
			this.content = content;
		}
	}

	public void increaseLikeCount() {
		checkIsDeleted();
		this.likeCount = this.likeCount + 1;
	}

	public void decreaseLikeCount() {
		checkIsDeleted();
		if (this.likeCount == 0) {
			throw new DevGramException(BoardErrorCode.LIKE_COUNT_CANNOT_BE_MINUS);
		}
		this.likeCount = this.likeCount - 1;
	}

	private void checkIsDeleted() {
		if (this.status == Status.DELETED) {
			throw new DevGramException(BoardErrorCode.ALREADY_DELETED_BOARD);
		}
	}

}