package epam.com.esm.utils.verifiers.user;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.user.User;
import epam.com.esm.view.dto.request.impl.auth.SignupDtoRequest;
import epam.com.esm.view.dto.request.impl.user.UserDtoRequest;
import org.springframework.stereotype.Component;

import static epam.com.esm.utils.adjusters.user.UserAdjuster.*;

/**
 * UserDtoVerifier is the service class, verifies user dto request and converts it to user
 */
@Component
public class UserDtoVerifier {

    /**
     * Default constructor
     */
    public UserDtoVerifier() {}

    /**
     * Verifies user dto request on create operation
     *
     * @param dto provided user dto request
     * @return {@code User} generated user
     */
    public User verifyOnCreate(UserDtoRequest dto) {
        return verify(dto);
    }

    /**
     * Verifies user dto request on update operation and adjusts if some fields empty
     *
     * @param dto provided user dto request
     * @param preUpdate pre updated user
     * @return {@code User} generated user
     */
    public User verifyOnUpdate(UserDtoRequest dto, User preUpdate) {
        adjustDtoFieldsOnUpdate(dto, preUpdate);
        User updated = verify(dto);
        updated.setId(preUpdate.getId());
        updated.setRoles(preUpdate.getRoles());
        return updated;
    }

    /**
     * Verifies user dto request
     *
     * @param dto provided user dto request
     * @return {@code User} generated user
     */
    private User verify(UserDtoRequest dto) {
        if (checkUserDtoFields(dto)) {
            User user = new User();
            passFieldsFromUserDto(dto, user);
            return user;
        } else {
            throw new InputException("User fields was corrupted or contain inappropriate characters. " +
                                    "Username may contain any latin letter lowercase only " +
                                    "and -_ or should be in mail style (xxx@yyy.zzz). " +
                                    "Password may contain any latin letter lowercase only " +
                                    "and any other special character except spaces.");
        }
    }

    /**
     * Verifies signup dto request
     *
     * @param dto provided signup dto request
     * @return {@code User} generated user
     */
    public User verifySignup(SignupDtoRequest dto) {
        if (checkSignupDtoFields(dto)) {
            User user = new User();
            passFieldsFromSignupDto(dto, user);
            System.out.println(user.getUsername());
            return user;
        } else {
            throw new InputException("User fields was corrupted or contain inappropriate characters. " +
                    "Username may contain any latin letter lowercase only " +
                    "and -_ or should be in mail style (xxx@yyy.zzz). " +
                    "Password may contain any latin letter lowercase only " +
                    "and any other special character except spaces." +
                    "Passwords doesn't match.");
        }
    }
}