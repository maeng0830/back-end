package com.project.devgram.controller;

import com.project.devgram.dto.FollowDto;
import com.project.devgram.dto.UserDto;
import com.project.devgram.oauth2.exception.TokenParsingException;
import com.project.devgram.oauth2.redis.RedisService;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.FollowService;
import com.project.devgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final TokenService tokenService;

    private final UserService userService;

    private final FollowService followService;
    private final RedisService redisService;


    @PostMapping("/api/logout")
    public String logout(HttpServletRequest request) throws TokenParsingException {

        String token = request.getHeader("Authentication");
        String username = usernameMaker(token);


        redisService.deleteRefresh(username);
        log.info("logout");
        return "redirect:/";
    }


    @GetMapping("/api/user")
    @ResponseBody
    public UserDto getUserDetails(HttpServletRequest request) throws TokenParsingException {


        String token = request.getHeader("Authentication");
        String username = usernameMaker(token);

        return userService.getUserDetails(username);

    }


    @PutMapping("/api/user")
    @ResponseBody
    public void updateUserDetails(HttpServletRequest request, @RequestBody UserDto dto) throws TokenParsingException {


        String token = request.getHeader("Authentication");
        log.info("token {}", token);
        dto.setUsername(usernameMaker(token));

        userService.updateUserDetails(dto);
        log.info("update success");

    }

    @PostMapping("/api/user/follow")
    @ResponseBody
    public void followingUsers(HttpServletRequest request, @RequestBody FollowDto dto)
            throws TokenParsingException {

        String token = request.getHeader("Authentication");
        dto.setUsername(usernameMaker(token));

         followService.followAdd(dto);
         log.info("following user add success");
    }
    @DeleteMapping("/api/user/follow")
    @ResponseBody
    public ResponseEntity<String> followingUserDelete(HttpServletRequest request, @RequestBody FollowDto dto) {

        String token = request.getHeader("Authentication");

        if(!token.isEmpty()) {
            log.info("delete follow");
            followService.deleteFollowUser(dto.getUserSeq());
        }
        return ResponseEntity.ok("Delete finished");
    }

    //나를 팔로우한 사용자
    @GetMapping("/api/user/follow/{UserSeq}")
    @ResponseBody
    public ResponseEntity<List<UserDto>> followerUserList(HttpServletRequest request, FollowDto dto
            , @PathVariable("UserSeq") Long UserSeq) throws TokenParsingException {

        String token = request.getHeader("Authentication");
        log.info("token followingUserList {}", token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowList(dto);

        return ResponseEntity.ok(userList);

    }

    //내가 팔로우한 사용자
    @GetMapping("/api/user/following/{UserSeq}")
    @ResponseBody
    public ResponseEntity<List<UserDto>> followingUserList(HttpServletRequest request, FollowDto dto
            , @PathVariable("UserSeq") Long UserSeq) throws TokenParsingException {

        String token = request.getHeader("Authentication");

        log.info("token followingUserList {}", token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowingList(dto);

        return ResponseEntity.ok(userList);

    }

    //oauth2 로그인 후 처리 토큰 발급 api
    @GetMapping(value = "/api/oauth/redirect")
    @ResponseBody
    public ResponseEntity<String> getToken(HttpServletResponse response,
              @RequestParam(value = "token", required = false) String token
            , @RequestParam(value = "refresh", required = false) String refresh) {


        response.addHeader("Authentication", token);
        response.addHeader("Refresh", refresh);

        return ResponseEntity.ok("token exist header");
    }


    private String usernameMaker(String token) throws TokenParsingException {
        return tokenService.getUsername(token);
    }


}
