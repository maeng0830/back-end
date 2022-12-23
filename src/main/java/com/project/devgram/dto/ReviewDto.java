package com.project.devgram.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {


	private Long reviewSeq;
	private double mark;
	private String content;
	private String status;
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 시간은 제외? 포함?
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	private String username;
	private Long productSeq;

}
