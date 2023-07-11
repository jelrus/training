package epam.com.esm.view.dto.request.impl.products;

import epam.com.esm.view.dto.request.DtoRequest;

import java.util.List;
import java.util.Objects;

/**
 * GiftCertificateTagsDtoRequest is the data class, serves as gift certificate's values carrier in requests for
 * insert operations, represents gift certificate with tags only
 */
public class GiftCertificateTagsDtoRequest extends DtoRequest {

    /**
     * Holds tags dto requests
     */
    private List<TagDtoRequest> tags;

    /**
     * Gets tags dto requests
     *
     * @return {@code TagDtoRequest} tags dto requests
     */
    public List<TagDtoRequest> getTags() {
        return tags;
    }

    /**
     * Sets new tags dto requests
     *
     * @param tags tags dto requests
     */
    public void setTags(List<TagDtoRequest> tags) {
        this.tags = tags;
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
        GiftCertificateTagsDtoRequest that = (GiftCertificateTagsDtoRequest) o;
        return Objects.equals(tags, that.tags);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }
}