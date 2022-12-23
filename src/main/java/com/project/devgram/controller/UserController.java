package com.project.devgram.controller;

import com.project.devgram.dto.CommonDto;
import com.project.devgram.dto.FollowDto;
import com.project.devgram.dto.UserDto;
import com.project.devgram.oauth2.redis.RedisService;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.FollowService;
import com.project.devgram.service.UserService;
import com.project.devgram.type.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final TokenService tokenService;
    private final UserService userService;
    private final FollowService followService;
    private final RedisService redisService;


    @PostMapping("/api/logout")
    public void logout(HttpServletRequest request) {

        String token = request.getHeader("Authentication");
        String username = usernameMaker(token);

        boolean redis = redisService.deleteRefresh(username);

        // 추가 accessToken 만료 ;
        if (redis) {
            redisService.blackListPush(token);

            log.info("add black list ");
        }

    }


    @GetMapping("/api/user")
    public UserDto getUserDetails(HttpServletRequest request) {


        String token = request.getHeader("Authentication");
        String username = usernameMaker(token);

        return userService.getUserDetails(username);

    }


    @PutMapping("/api/user")
    public void updateUserDetails(HttpServletRequest request, @RequestBody UserDto dto) {


        String token = request.getHeader("Authentication");
        log.info("token {}", token);
        dto.setUsername(usernameMaker(token));

        userService.updateUserDetails(dto);
        log.info("update success");

    }

    @PostMapping("/api/user/follow")
    public CommonDto<?> followingUsers(HttpServletRequest request, @Valid @RequestBody FollowDto dto, BindingResult bindingResult){

        String token = request.getHeader("Authentication");

        dto.setUsername(usernameMaker(token));

        followService.followAdd(dto);

        return new CommonDto<>(HttpStatus.OK.value(), Response.SUCCESS);


    }

    @DeleteMapping("/api/user/follow")
    public ResponseEntity<String> followingUserDelete(HttpServletRequest request, @RequestBody FollowDto dto) {

        String token = request.getHeader("Authentication");

        if (!token.isEmpty()) {
            log.info("delete follow");
            followService.deleteFollowUser(dto.getUserSeq());
        }
        return ResponseEntity.ok("Delete finished");
    }

    //나를 팔로우한 사용자
    @GetMapping("/api/user/follow/{UserSeq}")
    public ResponseEntity<List<UserDto>> followerUserList(HttpServletRequest request, FollowDto dto
            , @PathVariable("UserSeq") Long UserSeq) {

        String token = request.getHeader("Authentication");
        log.info("token followingUserList {}", token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowList(dto);

        return ResponseEntity.ok(userList);

    }

    //내가 팔로우한 사용자
    @GetMapping("/api/user/following/{UserSeq}")
    public ResponseEntity<List<UserDto>> followingUserList(HttpServletRequest request, FollowDto dto
            , @PathVariable("UserSeq") Long UserSeq) {

        String token = request.getHeader("Authentication");

        log.info("token followingUserList {}", token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowingList(dto);

        return ResponseEntity.ok(userList);

    }

    //oauth2 로그인 후 처리 토큰 발급 api
    @GetMapping(value = "/login/callback")
    public ResponseEntity<?> getToken(HttpServletResponse response,
                                           @RequestParam(value = "token", required = false) String token
            , @RequestParam(value = "refresh", required = false) String refresh) {


        response.addHeader("Authentication", token);
        response.addHeader("Refresh", refresh);


        return new ResponseEntity<String>(HttpStatus.OK);
    }


    private String usernameMaker(String token) {
          return tokenService.getUsername(token);
    }


}
