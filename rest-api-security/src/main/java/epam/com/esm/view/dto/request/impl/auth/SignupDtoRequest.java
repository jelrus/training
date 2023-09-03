package epam.com.esm.view.dto.request.impl.auth;

import epam.com.esm.view.dto.request.DtoRequest;

import java.util.Objects;

/**
 * SignupDtoRequest is the data class, holds values for auth request
 */
public class SignupDtoRequest extends DtoRequest {

    /**
     * Holds username value
     */
    private String username;

    /**
     * Holds password value
     */
    private String password;

    /**
     * Holds password confirmation value
     */
    private String confirmPassword;

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
     * Gets value from password field
     *
     * @return {@code String} password value
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets new value to password field
     *
     * @param password value for setting
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets value from password confirmation field
     *
     * @return {@code String} password confirmation value
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets new value to confirmPassword field
     *
     * @param confirmPassword value for setting
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
        SignupDtoRequest that = (SignupDtoRequest) o;
        return Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(confirmPassword, that.confirmPassword);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, confirmPassword);
    }
}