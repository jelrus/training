package epam.com.esm.view.dto.request.impl.action;

import epam.com.esm.view.dto.request.DtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;

import java.util.List;
import java.util.Objects;

/**
 * OrderDtoRequest is the data class, serves as order's values carrier in requests for insert operations,
 * represents full object
 */
public class OrderDtoRequest extends DtoRequest {

    /**
     * Holds username value
     */
    private String username;

    /**
     * Holds gift certificates
     */
    private List<GiftCertificateNameDtoRequest> giftCertificates;

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
     * Gets gift certificates
     *
     * @return {@code List<GiftCertificateNameDtoRequest>} gift certificates dto requests with name only
     */
    public List<GiftCertificateNameDtoRequest> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new gift certificates dto requests with name only
     *
     * @param giftCertificates gift certificates dto requests with name only for setting
     */
    public void setGiftCertificates(List<GiftCertificateNameDtoRequest> giftCertificates) {
        this.giftCertificates = giftCertificates;
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
        OrderDtoRequest that = (OrderDtoRequest) o;
        return Objects.equals(username, that.username)
               && Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, giftCertificates);
    }
}