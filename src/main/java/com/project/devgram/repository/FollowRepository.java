package com.project.devgram.repository;

import com.project.devgram.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {

    List<Follow> findByFollowing_UserSeqOrderByFollowing(Long userSeq);

    List<Follow> findByFollower_UserSeqOrderByFollower(Long userSeq);

    Optional<Follow> findByFollower_UserSeq(Long userSeq);



}
