package epam.com.esm.view.dto.response.impl.products;

import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.response.DtoResponse;

import java.util.List;
import java.util.Objects;

/**
 * TagDtoResponse is the data class, serves as tag's values carrier in requests for select operations, represents
 * full object for view
 */
public class TagDtoResponse extends DtoResponse {

    /**
     * Holds name value
     */
    private String name;

    /**
     * Holds gift certificates dto responses
     */
    private List<GiftCertificateDtoResponse> giftCertificates;

    /**
     * Constructs tag dto response from provided tag
     *
     * @param tag provided tag
     */
    public TagDtoResponse(Tag tag) {
        super(tag.getId());
        setName(tag.getName());
    }

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
     * Gets gift certificates dto responses
     *
     * @return {@code List<GiftCertificateDtoResponse>} gift certificates dto responses
     */
    public List<GiftCertificateDtoResponse> getGiftCertificates() {
        return giftCertificates;
    }

    /**
     * Sets new gift certificates dto responses
     *
     * @param giftCertificates gift certificates dto responses
     */
    public void setGiftCertificates(List<GiftCertificateDtoResponse> giftCertificates) {
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
        if (!super.equals(o)) return false;
        TagDtoResponse that = (TagDtoResponse) o;
        return Objects.equals(name, that.name) && Objects.equals(giftCertificates, that.giftCertificates);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, giftCertificates);
    }
}