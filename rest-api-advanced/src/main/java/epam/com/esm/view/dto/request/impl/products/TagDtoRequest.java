package epam.com.esm.view.dto.request.impl.products;

import epam.com.esm.view.dto.request.DtoRequest;

import java.util.List;
import java.util.Objects;

/**
 * TagDtoRequest is the data class, serves as tag's values carrier in requests for insert operations,
 * represents full object
 */
public class TagDtoRequest extends DtoRequest {

    /**
     * Holds name value
     */
    private String name;

    /**
     * Holds gift certificates dto requests with name only
     */
    private List<GiftCertificateNameDtoRequest> giftCertificates;

    /**
     * Gets value from name field
     *
     * @return {@code String} name value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets new value to name field
     *
     * @param name value for setting
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets gift certificates dto requests with name only
     *
     * @return {@code GiftCertificateNameDtoRequest} gift certificates dto requests with name only
     */
    public List<GiftCertificateNameDtoRequest> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new gift certificates dto requests with name only
     *
     * @param giftCertificates gift certificates dto requests with name only
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
        TagDtoRequest that = (TagDtoRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, giftCertificates);
    }
}