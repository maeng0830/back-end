package com.project.devgram.service;

import com.project.devgram.dto.FollowDto;
import com.project.devgram.dto.UserDto;
import com.project.devgram.entity.Follow;
import com.project.devgram.entity.User;
import com.project.devgram.oauth2.response.UserResponse;
import com.project.devgram.repository.FollowRepository;
import com.project.devgram.repository.UserRepository;
import com.project.devgram.type.ResponseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;


    @Transactional
    public UserResponse followAdd(FollowDto dto) {

        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        Optional<User> optionalFollowing = userRepository.findByUsername(dto.getFollowingUsername());
        List<Follow> followList = followRepository.findByFollowing_UserSeqOrderByFollowing(dto.getFollowingUserSeq());


        if (optionalUser.isPresent() && optionalFollowing.isPresent() && followList.size() == 0) {
            // 내가 팔로우 신청을 했을때
            User user = optionalUser.get();
            user.setFollowCount(user.getFollowCount() + 1);

            User followingUser = optionalFollowing.get();
            followingUser.setFollowerCount(user.getFollowerCount() + 1);

            User follower = User.builder()
                    .userSeq(user.getUserSeq())
                    .followerList(new ArrayList<>())
                    .followingList(new ArrayList<>())
                    .build();

            User following = User.builder()
                    .userSeq(followingUser.getUserSeq())
                    .followingList(new ArrayList<>())
                    .followerList(new ArrayList<>())
                    .build();

            Follow followers = new Follow();
            followers.setFollower(follower);
            followers.setFollowing(following);
            
            user.getFollowingList().add(followers);

            userRepository.save(user);


            return new UserResponse(String.valueOf(ResponseEnum.success), "following add success");
        }

        return new UserResponse(String.valueOf(ResponseEnum.fail), "이미 등록된 유저입니다.");
    }

    //나를 팔로잉한 사람 리스트
    public List<UserDto> getFollowList(FollowDto dto) {

        List<Follow> follower = followRepository.findByFollowing_UserSeqOrderByFollowing(dto.getUserSeq());

        return getUserDtoLists(follower, "follower");
    }

    //내가 팔로잉한 사람 리스트
    public List<UserDto> getFollowingList(FollowDto dto) {

        List<Follow> following = followRepository.findByFollower_UserSeqOrderByFollower(dto.getUserSeq());

        return getUserDtoLists(following, "following");
    }

    private List<UserDto> getUserDtoLists(List<Follow> followList, String check) {
        if (followList.size() > 0) {

            List<User> userList = new ArrayList<>();

            for (int i = 0; i < followList.size(); i++) {
                //나를 팔로우한 유저 userSeq
                Long userSeq;

                if("follower".equals(check)) {
                    userSeq = followList.get(i).getFollower().getUserSeq();
                }else {
                    userSeq = followList.get(i).getFollowing().getUserSeq();
                }

                Optional<User> optionalUser = userRepository.findById(userSeq);

                if (optionalUser.isPresent()) {

                    User userInfo = optionalUser.get();
                    userList.add(userInfo);
                }
            }

            return UserDto.of(userList);

        }
        log.error("해당하는 list 크기가 0입니다.");
        return null;
    }



    public void deleteFollowUser(Long userSeq){
        log.info("following user delete");

        Optional<Follow> optionalFollow = followRepository.findByFollower_UserSeq(userSeq);

        if(optionalFollow.isPresent()){

            Follow follow =optionalFollow.get();

            followRepository.delete(follow);

            followerCountMinus(follow);

            log.info("following user delete success");
        }

    }

    // 팔로우 취소시 상대 follower, 내 following 감소
    private void followerCountMinus (Follow follow){

        log.info("follower Minus start");

        Long mySeq = follow.getFollower().getUserSeq();
        Long yourSeq = follow.getFollowing().getUserSeq();

        Optional<User> optionalUser = userRepository.findById(mySeq);
        Optional<User> optionalFollowing = userRepository.findById(yourSeq);

        if(optionalUser.isPresent() ) {

            User user = optionalUser.get();
            user.setFollowCount(user.getFollowCount() - 1);

            userRepository.save(user);
        }

        if( optionalFollowing.isPresent()){

            User followingUser = optionalFollowing.get();
            followingUser.setFollowerCount(followingUser.getFollowerCount() - 1);

            userRepository.save(followingUser);
        }

        log.info("count minus success");
    }

}

