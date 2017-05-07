package com.clone.services;

import com.clone.entities.User;
import com.clone.entities.UserRole;
import com.clone.repositories.UserRepository;
import com.clone.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * Constructor
     * @param userRepository
     * @param userRoleRepository
     */
    @Autowired
    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * Returns the currently logged in user (this sessions user)
     * @return User
     */
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User loggedInUser = this.userRepository.findByUsername(name);
        return loggedInUser;
    }

    /**
     * Creates a new User-Role specified by role and sets it to specified user
     *
     * @param user The user the role is for
     * @param role The role
     */
    public void setUserRole(User user, String role) {  // TODO: Enums for userRoles to send in as args

        UserRole userRole = new UserRole();
        userRole.setUserByUserId(user);
        userRole.setRole("ROLE_" + role);
        userRoleRepository.save(userRole);

        user.setUserRole(userRole);
    }

    /**
     * Blocks a user by setting enabled to 0 (false)
     * @param user
     */
    public void blockUser(User user) {
        user.setEnabled((byte) 0);
        this.userRepository.save(user);
    }

    /**
     * Unblocks a user by setting enabled to 1 (true)
     * @param user
     */
    public void unblockUser(User user) {
        user.setEnabled((byte) 1);
        this.userRepository.save(user);
    }

    /**
     * Returns true if user specified by id is enabled.
     * If user doesn't exist in database or if user is not enabled (e.i blocked), false will be returned.
     *
     * @param userId
     * @return boolean
     */
    public boolean getEnabledStatus(int userId) {
        User user = userRepository.findOne(userId);
        /* User doesn't exist */
        if (user == null) {
            return false;
        }
        /* User is not enabled */
        if (user.getEnabled() == 0) {
            return false;
        }
        return true;
    }


}
