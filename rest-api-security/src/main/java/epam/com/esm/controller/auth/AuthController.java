package epam.com.esm.controller.auth;

import epam.com.esm.security.service.AuthService;
import epam.com.esm.view.dto.request.impl.auth.SignupDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import epam.com.esm.view.dto.response.impl.auth.SignupDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController class is the REST controller, responsible for handling auth dto requests
 */
@RestController
public class AuthController {

    /**
     * Holds AuthService object
     */
    private final AuthService authService;

    /**
     * Constructs AuthController with provided AuthService object
     *
     * @param authService service, provides auth operations
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Consumes UserDtoRequest object, creates SignupDtoResponse as the result of auth service login operation
     *
     * @param authRequest  object with requested parameters
     * @return {@code ResponseEntity<SignupDtoResponse>} response entity, represents result of login operation
     */
    @PostMapping("/login")
    public ResponseEntity<SignupDtoResponse> login(@RequestBody UserDtoRequest authRequest) {
        SignupDtoResponse dto = authService.login(authRequest);
        dto.setMessage("Successfully logged");
        return ResponseEntity.ok(dto);
    }

    /**
     * Consumes SignupDtoRequest object, creates SignupDtoResponse as the result of auth service sign up operation
     *
     * @param signupDtoRequest object with requested parameters
     * @return {@code ResponseEntity<SignupDtoResponse>} response entity, represents result of sign up operation
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupDtoResponse> signup(@RequestBody SignupDtoRequest signupDtoRequest) {
        SignupDtoResponse dto = authService.signup(signupDtoRequest);
        dto.setMessage("Successfully signed up");
        return ResponseEntity.ok().body(dto);
    }
}