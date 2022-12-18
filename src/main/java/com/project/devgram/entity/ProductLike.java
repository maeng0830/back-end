package com.project.devgram.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_like")
public class ProductLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productLikeSeq;

	@Column(name = "product_seq")
	@NonNull
	private Long productSeq;

	@Column(name = "user_name")
	@NonNull
	private String username;
}
