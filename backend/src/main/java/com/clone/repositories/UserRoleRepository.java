package com.clone.repositories;

import com.clone.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pontusellboj on 2017-01-25.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
