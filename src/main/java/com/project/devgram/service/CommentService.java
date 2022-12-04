package com.project.devgram.service;

import com.project.devgram.entity.Comment;
import com.project.devgram.dto.CommentDto;
import com.project.devgram.exception.DevGramException;
import com.project.devgram.exception.errorcode.CommentErrorCode;
import com.project.devgram.type.CommentStatus;
import com.project.devgram.repository.CommentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /*
     * 댓글 등록
     */
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
            .content(commentDto.getContent())
            .parentCommentSeq(commentDto.getParentCommentSeq())
            .boardSeq(commentDto.getBoardSeq())
            .createdBy(commentDto.getCreatedBy())
            .commentStatus(CommentStatus.POST)
            .build();

        return CommentDto.from(commentRepository.save(comment));
    }

    /*
     * 댓글 조회(보드)
     */
    public List<CommentDto> getCommentList(Long boardSeq) {

        List<Comment> commentList = commentRepository.findByBoardSeqAndCommentStatusNot(boardSeq,
                CommentStatus.DELETE)
            .orElseThrow(() -> new DevGramException(
                CommentErrorCode.NOT_EXISTENT_COMMENT_FOR_BOARD));

        ArrayList<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment: commentList) {
            commentDtoList.add(CommentDto.from(comment));
        }

        return commentDtoList;
    }

    /*
     * 신고 댓글 조회(관리자)
     */
    public List<CommentDto> getAccusedCommentList() {
        List<Comment> commentList = commentRepository.findByCommentStatus(CommentStatus.ACCUSE)
            .orElseThrow(() -> new DevGramException(CommentErrorCode.NOT_EXISTENT_ACCUSED_COMMENT));

        ArrayList<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment: commentList) {
            commentDtoList.add(CommentDto.from(comment));
        }

        return commentDtoList;
    }
}
