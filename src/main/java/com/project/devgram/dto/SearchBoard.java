package com.project.devgram.dto;

import com.project.devgram.type.status.Status;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class SearchBoard {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private Long id;
		private String title;
		private String content;
		private LocalDateTime createdAt;
		private Integer likeCount;
		private Long commentsCount;
		private String createdBy;
		private Long createdByUserSeq;
		private String createdByImg;
		private Status status;
		private String imageUrl;
		private List<String> tags;
		private List<ProductElement> products;
	}


	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ProductElement{
		private Long id;
		private String imgUrl;
		private String title;
		private Integer hits;
	}
}
