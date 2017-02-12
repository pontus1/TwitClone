package com.clone.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public class FollowPK implements Serializable {
    private int followerId;
    private int followeeId;

    @Column(name = "follower_id")
    @Id
    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    @Column(name = "followee_id")
    @Id
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

        FollowPK followPK = (FollowPK) o;

        if (followerId != followPK.followerId) return false;
        if (followeeId != followPK.followeeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = followerId;
        result = 31 * result + followeeId;
        return result;
    }
}
