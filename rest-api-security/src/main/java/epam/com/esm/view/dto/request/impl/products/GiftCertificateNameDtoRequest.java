package epam.com.esm.view.dto.request.impl.products;

import epam.com.esm.view.dto.request.DtoRequest;

import java.util.Objects;

/**
 * GiftCertificateNameDtoRequest is the data class, serves as gift certificate's values carrier in requests for
 * insert operations, represents gift certificate with name only
 */
public class GiftCertificateNameDtoRequest extends DtoRequest {

    /**
     * Holds name value
     */
    private String name;

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
     * Compares source object and target object for equality
     *
     * @param o target object
     * @return {@code true} if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateNameDtoRequest that = (GiftCertificateNameDtoRequest) o;
        return Objects.equals(name, that.name);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}