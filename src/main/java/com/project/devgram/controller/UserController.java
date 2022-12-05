package com.project.devgram.controller;

import com.project.devgram.dto.CommonDto;
import com.project.devgram.dto.FollowDto;
import com.project.devgram.dto.UserDto;
import com.project.devgram.oauth2.exception.TokenParsingException;
import com.project.devgram.oauth2.response.UserResponse;
import com.project.devgram.oauth2.token.TokenService;
import com.project.devgram.service.FollowService;
import com.project.devgram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {


private final TokenService tokenService;

private final UserService userService;

private final FollowService followService;


    @GetMapping("/api/loginForm")
    public  String login(){

        return "loginForm";
    }

    @PostMapping("/api/logout")
    public String logout(){
        return "redirect:/";
    }


    @GetMapping("/api/user")
    @ResponseBody
    public CommonDto<UserDto> getUserDetails(HttpServletRequest request) throws TokenParsingException {


        String token= request.getHeader("Authentication");
        log.info("token {}",token);

        String username = usernameMaker(token);
        log.info("username: {} ",username);

        UserDto users= userService.getUserDetails(username);

        return new CommonDto<>(HttpStatus.OK.value(),users);

    }


    @PutMapping("/api/user")
    @ResponseBody
    public CommonDto<UserResponse> updateUserDetails(HttpServletRequest request,@RequestBody UserDto dto) throws TokenParsingException {


        String token= request.getHeader("Authentication");
        log.info("token {}",token);
        dto.setUsername(usernameMaker(token));

        UserResponse users = userService.updateUserDetails(dto);

        return new CommonDto<>(HttpStatus.OK.value(),users);
    }

    @PostMapping("/api/user/follow")
    @ResponseBody
    public CommonDto<UserResponse> followingUsers(HttpServletRequest request,@RequestBody FollowDto dto) throws TokenParsingException {

        String token= request.getHeader("Authentication");
        dto.setUsername(usernameMaker(token));

        UserResponse userFollow = followService.followAdd(dto);

        return new CommonDto<>(HttpStatus.OK.value(),userFollow);
    }

    //나를 팔로우한 사용자
    @GetMapping("/api/user/follow/{UserSeq}")
    @ResponseBody
    public CommonDto<List<UserDto>> followerUserList(HttpServletRequest request, FollowDto dto
    ,@PathVariable("UserSeq") Long UserSeq) throws TokenParsingException {

        String token= request.getHeader("Authentication");
        log.info("token followingUserList {}",token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowList(dto);

        return new CommonDto<>(HttpStatus.OK.value(),userList);

    }

    //내가 팔로우한 사용자
    @GetMapping("/api/user/following/{UserSeq}")
    @ResponseBody
    public CommonDto<List<UserDto>> followingUserList(HttpServletRequest request, FollowDto dto
            ,@PathVariable("UserSeq") Long UserSeq) throws TokenParsingException {

        String token= request.getHeader("Authentication");

        log.info("token followingUserList {}",token);
        dto.setUsername(usernameMaker(token));
        dto.setUserSeq(UserSeq);

        List<UserDto> userList = followService.getFollowingList(dto);

        return new CommonDto<>(HttpStatus.OK.value(),userList);

    }



    private String usernameMaker(String username) throws TokenParsingException {
        return tokenService.getUsername(username);
    }






}
