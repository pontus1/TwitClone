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

    @Query("SELECT t FROM Follow f JOIN f.userByFolloweeId u JOIN u.tweets t WHERE f.followerId = :followerId")
    List<Tweet>findAllByFollowees(@Param("followerId") int followerId);

    //@Query("SELECT u FROM Follow f JOIN f.userByFolloweeId u WHERE f.followerId = :userId")
    //List<User> findAllFolloweesOf(@Param("userId") int userId);
}
