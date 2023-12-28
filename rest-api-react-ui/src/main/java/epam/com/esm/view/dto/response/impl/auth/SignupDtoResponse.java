package epam.com.esm.view.dto.response.impl.auth;

import epam.com.esm.view.dto.response.DtoResponse;

import java.util.Objects;

/**
 * SignupDtoResponse is the data class which holds values of auth response
 */
public class SignupDtoResponse extends DtoResponse {

    /**
     * Holds username value
     */
    private String username;

    /**
     * Holds jwt toke value
     */
    private String token;

    /**
     * Constructs SignupDtoResponse with username and toke
     *
     * @param username provided username
     * @param token    provided token
     */
    public SignupDtoResponse(String username, String token) {
        super(null);
        this.username = username;
        this.token = token;
    }

    /**
     * Gets value from username field
     *
     * @return {@code String} username value
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets new value to username field
     *
     * @param username value for setting
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets value from token field
     *
     * @return {@code String} token value
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets new value to token field
     *
     * @param token value for setting
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SignupDtoResponse that = (SignupDtoResponse) o;
        return Objects.equals(username, that.username) && Objects.equals(token, that.token);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, token);
    }
}