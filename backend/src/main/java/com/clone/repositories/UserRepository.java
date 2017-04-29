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

    /**
     * Returns user specified by id
     * @param userId
     * @return Optional User
     */
    Optional<User> findByUserId(int userId);

    /**
     * Returns user specified by username
     * @param username
     * @return User
     */
    User findByUsername(String username);

    /**
     * Returns user specified by email
     * @param email
     * @return User
     */
    User findByEmail(String email);

    /**
     * Returns all users following user specified by id
     * (all followers of a user)
     *
     * @param userId
     * @return List of users
     */
    @Query("SELECT u FROM Follow f JOIN f.userByFollowerId u WHERE f.followeeId = :userId")
    List<User> findAllFollowersOf(@Param("userId") int userId);

    /**
     * Returns all users that user specified by id is following
     * (all users being followed by user)
     *
     * @param userId
     * @return
     */
    @Query("SELECT u FROM Follow f JOIN f.userByFolloweeId u WHERE f.followerId = :userId")
    List<User> findAllFolloweesOf(@Param("userId") int userId);
}
