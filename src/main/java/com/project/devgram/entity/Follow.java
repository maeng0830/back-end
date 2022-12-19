package com.project.devgram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users follower;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users following;

    public void setFollower(Users follower){
        this.follower=follower;
        follower.getFollowingList().add(this);

    }

    public void setFollowing(Users following){
        this.following=following;
        following.getFollowingList().add(this);
    }

}
