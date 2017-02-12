package com.clone.repositories;

import com.clone.entities.Follow;
import com.clone.entities.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface FollowRepository extends JpaRepository<Follow, FollowPK> {
}
