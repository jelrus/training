package epam.com.esm.security.service;

import epam.com.esm.exception.types.NotFoundException;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.persistence.repository.impl.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * CustomUserDetailsService is the service class, provides user to user details user conversion methods
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Holds UserRepository object
     */
    private final UserRepository uRepo;


    /**
     * Constructs CustomUserDetailsService with provided user repository
     *
     * @param uRepo repository, provides user jpa operations
     */
    public CustomUserDetailsService(UserRepository uRepo) {
        this.uRepo = uRepo;
    }

    /**
     * Loads user details user from user pojo
     *
     * @param username the username identifying the user whose data is required.
     * @return {@code UserDetails} user details user
     * @throws UsernameNotFoundException if username is corrupted
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return convertToUserDetails(getUser(username));
    }

    /**
     * Loads user from repository
     *
     * @param username provided username
     * @return {@code User} user pojo
     */
    private User getUser(String username) {
        return uRepo.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Something went wrong, try again")
        );
    }

    /**
     * Converts user pojo to user details user
     *
     * @param user provided user pojo
     * @return {@code User} user details user
     */
    private org.springframework.security.core.userdetails.User convertToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList())
        );
    }
}