package com.project.devgram.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "category")
public class Category {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Long category_Seq;
	String name;
	String color;

	@OneToMany(mappedBy = "category")
	@JsonManagedReference
	private List<Product> productList = new ArrayList<>();

}
