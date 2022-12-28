package com.project.devgram.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	name = "board_tag",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "board_tag_constraint",
			columnNames = {"board_seq", "tag_seq"}
		)
	}
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardTag extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_tag_seq", nullable = false)
	private Long boardTagSeq;

	@ManyToOne
	@JoinColumn(name = "board_seq")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "tag_seq")
	private Tag tag;

	private String createdBy;
	private String updatedBy;

}
