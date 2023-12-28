package epam.com.esm.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * AbstractController class is the ancestor class for all REST Controllers used in application
 */
public abstract class AbstractController {

    /**
     * Gets principal from security context holder and checks its authorities
     *
     * @param roleName role to check
     * @return {@code true} if principal contains provided role name
     */
    protected boolean checkRole(String roleName) {
        return SecurityContextHolder.getContext()
                                    .getAuthentication()
                                    .getAuthorities()
                                    .contains(new SimpleGrantedAuthority(roleName));
    }

    /**
     * Gets principal from security context holder and checks its username
     *
     * @param username provided username
     * @return {@code true} if current principal has provided name
     */
    protected boolean checkUsername(String username) {
        return SecurityContextHolder.getContext().getAuthentication().getName().equals(username);
    }

    /**
     * Checks if current principal has main role or sub role with matching username
     *
     * @param mainRole provided main role
     * @param subRole provided sub role
     * @param subUsername provided username
     */
    protected void checkPermissions(String mainRole, String subRole, String subUsername) {
        if (!checkRole(mainRole) && !(checkRole(subRole) && checkUsername(subUsername))) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}