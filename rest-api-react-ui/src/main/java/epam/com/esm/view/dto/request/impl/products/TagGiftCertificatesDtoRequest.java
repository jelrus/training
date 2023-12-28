package epam.com.esm.view.dto.request.impl.products;

import java.util.List;
import java.util.Objects;

/**
 * TagGiftCertificatesDtoRequest is the data class, serves as tag's values carrier in requests for insert operations,
 * represents tag with gift certificates only
 */
public class TagGiftCertificatesDtoRequest {

    /**
     * Holds gift certificates dto requests with name only
     */
    private List<GiftCertificateNameDtoRequest> giftCertificates;

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
        TagGiftCertificatesDtoRequest that = (TagGiftCertificatesDtoRequest) o;
        return Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(giftCertificates);
    }
}