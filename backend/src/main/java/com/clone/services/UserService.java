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

    @Autowired
    UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    // Get the currently logged in user
    public User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User loggedInUser = this.userRepository.findByUsername(name);
        return loggedInUser;
    }

    // Create new user_role and set it to user
    public void setUserRole(User user, String role) {  // TODO: Enums for userRoles to send in as args
        // Create new UserRole for new user
        UserRole userRole = new UserRole();
        userRole.setUserByUserId(user);
        userRole.setRole("ROLE_" + role);
        userRoleRepository.save(userRole);

        // Set userRole
        user.setUserRole(userRole);
    }

    // Get enabled status (blocked or not)
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
