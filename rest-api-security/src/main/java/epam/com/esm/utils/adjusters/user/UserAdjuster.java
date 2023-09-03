package epam.com.esm.utils.adjusters.user;

import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.request.impl.auth.SignupDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * UserAdjuster utility class, adjust fields on user create or update operations
 */
public final class UserAdjuster {

    /**
     * Default constructor
     */
    private UserAdjuster() {}

    /**
     * Checks user dto request fields on update
     *
     * @param dto requested user dto request
     * @param preUpdate requested pre updated user
     */
    public static void adjustDtoFieldsOnUpdate(UserDtoRequest dto, User preUpdate) {
        if (checkUsername(dto.getUsername())) {
            preUpdate.setUsername(dto.getUsername());
        }

        if (checkPassword(dto.getPassword())) {
            preUpdate.setPassword(dto.getPassword());
        }
    }

    /**
     * Passes fields from user dto request to user
     *
     * @param dto requested user dto request
     * @param user requested user
     */
    public static void passFieldsFromUserDto(UserDtoRequest dto, User user) {
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
    }

    public static void passFieldsFromSignupDto(SignupDtoRequest dto, User user) {
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
    }

    /**
     * Checks user dto fields if correct
     *
     * @param dto requested user dto request
     * @return {@code true} if user dto fields are correct
     */
    public static boolean checkUserDtoFields(UserDtoRequest dto) {
        return dto != null
                && checkUsername(dto.getUsername())
                && checkPassword(dto.getPassword());
    }

    /**
     * Checks signup dto fields if correct
     *
     * @param dto requested signup dto request
     * @return {@code true} if signup dto fields are correct
     */
    public static boolean checkSignupDtoFields(SignupDtoRequest dto) {
        return dto != null
                && checkUsername(dto.getUsername())
                && checkPassword(dto.getPassword())
                && checkPassword(dto.getConfirmPassword())
                && checkPasswordMatching(dto.getPassword(), dto.getConfirmPassword());
    }

    /**
     * Checks if username null, empty, blank, matches pattern, length
     * Will accept if consists of:
     * latin letters (user) - true
     * numbers (user12) - true
     * lowercase letters (user12a) - true
     * underscore or hyphen (user_, user-12) - true
     * matches email pattern (user@12.com) - true
     *
     * @param username requested username
     * @return {@code true} if username matches conditions
     */
    private static boolean checkUsername(String username) {
        return  !StringUtils.isBlank(username)
                && (username.matches("^[a-z0-9_-]+$") || username.matches("^[a-z0-9]+[@]+[a-z]+[.]+[a-z]+$"))
                && username.length() <= 255
                && username.toLowerCase(Locale.ROOT).equals(username);
    }

    /**
     * Checks if password matches null, empty, blank, pattern, length
     * Will accept if consist of:
     * latin letter (password) - true
     * numbers (password12) - true
     * lowercase letters (password12) - true
     * any special symbol (password1!&) - true
     * whitespaces will fail (pass word) - false
     *
     * @param password requested password
     * @return {@code true} if password matches conditions
     */
    private static boolean checkPassword(String password) {
        return !StringUtils.isBlank(password)
                && password.matches("^[a-z0-9\\S]+$")
                && password.length() <= 255
                && password.toLowerCase(Locale.ROOT).equals(password);
    }

    /**
     * Checks password and confirm password fields of signup dto for equality
     *
     * @param password requested password
     * @param confirm requested password confirm
     * @return {@code true} if passwords matches
     */
    private static boolean checkPasswordMatching(String password, String confirm) {
        return password.equals(confirm);
    }
}