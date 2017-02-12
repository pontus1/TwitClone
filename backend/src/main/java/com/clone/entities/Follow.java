package com.clone.entities;

import javax.persistence.*;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Entity
@IdClass(FollowPK.class)
public class Follow {
    private int followerId;
    private int followeeId;
    private User userByFollowerId;
    private User userByFolloweeId;

    @Id
    @Column(name = "follower_id")
    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    @Id
    @Column(name = "followee_id")
    public int getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(int followeeId) {
        this.followeeId = followeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Follow follow = (Follow) o;

        if (followerId != follow.followerId) return false;
        if (followeeId != follow.followeeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = followerId;
        result = 31 * result + followeeId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "user_id", nullable = false, insertable =  false, updatable = false)
    public User getUserByFollowerId() {
        return userByFollowerId;
    }

    public void setUserByFollowerId(User userByFollowerId) {
        this.userByFollowerId = userByFollowerId;
    }

    @ManyToOne
    @JoinColumn(name = "followee_id", referencedColumnName = "user_id", nullable = false, insertable =  false, updatable = false)
    public User getUserByFolloweeId() {
        return userByFolloweeId;
    }

    public void setUserByFolloweeId(User userByFolloweeId) {
        this.userByFolloweeId = userByFolloweeId;
    }
}
