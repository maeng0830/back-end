package com.project.devgram.service;

import com.project.devgram.entity.User;
import com.project.devgram.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {



    @Mock
    private UserRepository userRepository;



    @Test
    @DisplayName("유저 정보 조회")
    void getUserInfo(){
    //given

        Long [] userId = {1L,22L,33L};

        doReturn(Arrays.asList(
                User.builder().userSeq(userId[0]).build(),
                User.builder().userSeq(userId[1]).build(),
                User.builder().userSeq(userId[2]).build()

        )).when(userRepository).findAllById(Arrays.asList(userId));
    //when

        final List<User> result =getMembershipList(List.of(userId));

    //then

    assertThat(result.size()).isEqualTo(3);
    assertEquals(1L,result.get(0).getUserSeq());

    }


    @Test
    void updateUserList(){
    //given
        User user = User.builder()
                .userSeq(12L)
                .username("testUser")
                .build();
        userRepository.save(user);
        User user2= User.builder()
                .userSeq(12L)
                .username("users")
                .build();
        userRepository.save(user2);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user2));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    //when

        Optional<User> userOptional = userRepository.findById(12L);
        User user3 = userOptional.get();
    //then
        verify(userRepository,times(2)).save(captor.capture());
        assertNotNull(userOptional);
        assertEquals("users",user3.getUsername());


    }

    public List<User> getMembershipList(final List<Long> userId) {
        return userRepository.findAllById(userId);
    }

}