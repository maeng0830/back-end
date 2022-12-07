package com.project.devgram.entity;


import com.project.devgram.type.ProductCode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product implements ProductCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long product_Seq;

	String title;
	String content;
	Integer hits;
	double rating;
	Integer like_Count;
	double price;
	String status;
	long category_Seq; // 카테고리 id

}
