package com.clone.repositories;

import com.clone.entities.Follow;
import com.clone.entities.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    /**
     * Returns a unique Follow entity specified by followerId (the user following)
     * and followeeId (the user being followed)
     *
     * @param followerId
     * @param followeeId
     * @return Follow
     */
    @Query("SELECT f FROM Follow f WHERE f.followerId = :followerId AND f.followeeId = :followeeId")
    Follow findByFollowerIdAndFolloweeId(@Param("followerId") int followerId, @Param("followeeId")int followeeId);
}
