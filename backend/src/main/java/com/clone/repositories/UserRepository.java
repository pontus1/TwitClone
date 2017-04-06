package com.clone.repositories;

import com.clone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserId(int username);

    User findByUsername(String username);

    User findByEmail(String email);

    // Find users followers
    @Query("SELECT u FROM Follow f JOIN f.userByFollowerId u WHERE f.followeeId = :userId")
    List<User> findAllFollowersOf(@Param("userId") int userId);

    // Find users that user is following (followees)
    @Query("SELECT u FROM Follow f JOIN f.userByFolloweeId u WHERE f.followerId = :userId")
    List<User> findAllFolloweesOf(@Param("userId") int userId);
}
