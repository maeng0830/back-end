package com.project.devgram.dto;


import com.project.devgram.entity.Users;
import com.project.devgram.type.ROLE;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@RequiredArgsConstructor
public class UserDto {
    @NotNull(message = "아이디는 null일수 없습니다.")
    private String id;
    private Long userSeq;
    private String email;
    private String password;
    private String username;
    private ROLE role;

    private MultipartFile imageFile;

    private String providerId;
    private String annual;
    private String job;

    private int followCount;
    private int followerCount;


    @Builder
    public UserDto(Long userSeq, String email, String password, String username,
                   ROLE role, String providerId, String annual, String job, int followCount, int followerCount){

        this.username = username;
        this.userSeq = userSeq;
        this. email = email;
        this.job = job;
        this.role= role;
        this.followCount=followCount;
        this.annual = annual;
        this.providerId= providerId;
        this.password = password;
        this.followerCount= followerCount;
    }

    public static List<UserDto> of(List<Users> userList) {

        if(userList.size() > 0){
            List<UserDto> followerList = new ArrayList<>();
            for (Users u: userList) {
                UserDto dto = UserDto.builder()
                        .userSeq(u.getUserSeq())
                        .username(u.getUsername())
                        .annual(u.getAnnual())
                        .job(u.getJob())
                        .followerCount(u.getFollowerCount())
                        .followerCount(u.getFollowerCount())
                        .build();
                followerList.add(dto);
            }
            return followerList;
        }
        log.error("followerList error");
        return null;

    }


}
