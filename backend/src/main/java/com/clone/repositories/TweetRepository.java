package com.clone.repositories;

import com.clone.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    /**
     * Returns Tweet specified by id
     * @param messageId
     * @return Optional Tweet
     */
    Optional<Tweet> findByMessageId(int messageId);

    /**
     * Returns list of tweets specified by authors id
     * @param userId
     * @return List of Tweets
     */
    List<Tweet> findByAuthorId(int userId);

    /**
     * Returns List of tweets posted by users that specified user is following
     *
     * @param followerId
     * @return List of Tweets
     */
    @Query("SELECT t FROM Follow f JOIN f.userByFolloweeId u JOIN u.tweets t WHERE f.followerId = :followerId")
    List<Tweet>findAllByFollowees(@Param("followerId") int followerId);


}
