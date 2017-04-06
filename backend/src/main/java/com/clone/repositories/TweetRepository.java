package com.clone.repositories;

import com.clone.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findByAuthorId(int userId);


    @Query("SELECT DISTINCT t FROM Follow f JOIN f.userByFolloweeId u JOIN u.tweets t WHERE f.followerId = :followerId OR t.authorId =:followerId")
    List<Tweet>findAllByFollowees(@Param("followerId") int followerId);

    // @Query("SELECT t FROM Follow f JOIN f.userByFolloweeId u JOIN u.tweets t WHERE f.followerId = :followerId")
    // List<Tweet>findAllByFollowees(@Param("followerId") int followerId);


}
