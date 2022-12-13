package com.project.devgram.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class FollowDto {

    // 내 pk
    private Long userSeq;

    private String username;


    //내가 follow한 사람 정보
    @NotNull(message="해당하는 user의 UserSeq가 없습니다.")
    @Positive(message = "0보다 큰수를 입력하세요")
    private Long followingUserSeq;

 
    private String followingUsername;

    private String followStat;

    @Builder
    public FollowDto(Long userSeq,Long followingUserSeq){
        this.userSeq=userSeq;
        this.followingUserSeq=followingUserSeq;

    }


}
