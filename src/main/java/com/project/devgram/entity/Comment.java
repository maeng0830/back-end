package com.project.devgram.entity;

import com.project.devgram.type.CommentStatus;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentSeq; // 댓글 번호

    private String content; // 댓글 내용
    private Long commentGroup; // 댓글 그룹
    private Long parentCommentSeq; // 부모 댓글 번호
    private String parentCommentCreatedBy; // 부모 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_seq", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board; // 게시글 번호

    private String createdBy; // 작성자
    private String updatedBy; // 수정자

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus; // 댓글 상태
}
