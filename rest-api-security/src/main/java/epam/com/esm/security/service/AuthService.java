package epam.com.esm.security.service;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.model.service.interfaces.entity.user.UserService;
import epam.com.esm.security.jwt.JwtTokenGenerator;
import epam.com.esm.utils.verifiers.user.UserDtoVerifier;
import epam.com.esm.view.dto.request.impl.auth.SignupDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.auth.SignupDtoResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService is the service class, provides authentication operations (signup and login)
 */
@Service
public class AuthService {

    /**
     * Holds CustomUserDetailsService object
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Holds UserService object
     */
    private final UserService userService;

    /**
     * Holds JwtTokenGenerator object
     */
    private final JwtTokenGenerator jwtTokenGenerator;

    /**
     * Holds AuthenticationManager object
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Holds BCryptPasswordEncoder object
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Holds UserDtoVerifier object
     */
    private final UserDtoVerifier userDtoVerifier;


    /**
     * Constructs AuthService with provided dependencies
     *
     * @param customUserDetailsService service, converts user pojo to spring security user detail's user object
     * @param userService              service, provides user pojo operations
     * @param jwtTokenGenerator        service, provides jwt tokens operations
     * @param authenticationManager    interface, provides authentication contracts
     * @param bCryptPasswordEncoder    password encoder
     * @param userDtoVerifier          service, provides operations for user dto validation
     */
    public AuthService(CustomUserDetailsService customUserDetailsService, UserService userService,
                       JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authenticationManager,
                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDtoVerifier userDtoVerifier) {
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDtoVerifier = userDtoVerifier;
    }

    /**
     * Consumes user dto request, authenticates user, produces signup dto response as the result of authentication
     *
     * @param userDtoRequest provided user dto request
     * @return {@code SignupDtoResponse} authentication result
     */
    public SignupDtoResponse login(UserDtoRequest userDtoRequest) {
        checkPasswords(userDtoRequest);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDtoRequest.getUsername(), userDtoRequest.getPassword())
        );
        return autoLogin(userDtoRequest.getUsername());
    }

    /**
     * Consumes signup dto request, verifies fields, creates user, produces signup dto response as the result of signup
     *
     * @param signupDtoRequest provided signup dto request
     * @return {@code SignupDtoResponse} signup result
     */
    public SignupDtoResponse signup(SignupDtoRequest signupDtoRequest) {
        return autoLogin(userService.create(userDtoVerifier.verifySignup(signupDtoRequest)).getUsername());
    }

    /**
     * Loads user by username and generates signup dto response with user and access token
     *
     * @param username provided username
     * @return {@code SignupDtoResponse} result of signup/login
     */
    private SignupDtoResponse autoLogin(String username) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return new SignupDtoResponse(username, jwtTokenGenerator.generateToken(userDetails));
    }

    /**
     * Checks if password and confirm password matches
     *
     * @param userDtoRequest provided user dto request with passwords
     */
    private void checkPasswords(UserDtoRequest userDtoRequest) {
        String password = customUserDetailsService.loadUserByUsername(userDtoRequest.getUsername()).getPassword();

        if (!bCryptPasswordEncoder.matches(userDtoRequest.getPassword(), password)) {
            throw new InputException("Oops, something went wrong, check input and try again");
        }
    }
}