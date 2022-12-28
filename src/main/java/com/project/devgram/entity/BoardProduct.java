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
	name="board_product",
	uniqueConstraints={
		@UniqueConstraint(
			name="board_product_constraint",
			columnNames={"board_seq", "product_seq"}
		)
	}
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_product_seq", nullable = false)
	private Long boardProductSeq;

	@ManyToOne
	@JoinColumn(name = "board_seq")
	private Board board;

	@ManyToOne
	@JoinColumn(name = "product_seq")
	private Product product;

	private String createdBy;
	private String updatedBy;
}
