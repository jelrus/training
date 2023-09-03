package epam.com.esm.view.dto.request.impl.products;

import epam.com.esm.view.dto.request.DtoRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * GiftCertificateDtoRequest is the data class, serves as gift certificate's values carrier in requests for
 * insert operations, represents full object
 */
public class GiftCertificateDtoRequest extends DtoRequest {

    /**
     * Holds name value
     */
    private String name;

    /**
     * Holds description value
     */
    private String description;

    /**
     * Holds price value
     */
    private BigDecimal price;

    /**
     * Holds duration value
     */
    private Integer duration;

    /**
     * Holds update value
     */
    private String update;

    /**
     * Holds update value
     */
    private String create;

    /**
     * Holds tags dto requests
     */
    private List<TagDtoRequest> tags;

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
     * Gets value from description field
     *
     * @return {@code String} description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets new value to description field
     *
     * @param description value for setting
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets value from price field
     *
     * @return {@code BigDecimal} price value
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets new value to price field
     *
     * @param price value for setting
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets value from duration field
     *
     * @return {@code Integer} duration value
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Sets new value to duration field
     *
     * @param duration value for setting
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Gets value from create field
     *
     * @return {@code String} create value
     */
    public String getCreate() {
        return create;
    }

    /**
     * Sets new value to create field
     *
     * @param create value for setting
     */
    public void setCreate(String create) {
        this.create = create;
    }

    /**
     * Gets value from update field
     *
     * @return {@code String} update value
     */
    public String getUpdate() {
        return update;
    }

    /**
     * Sets new value to update field
     *
     * @param update value for setting
     */
    public void setUpdate(String update) {
        this.update = update;
    }

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
        GiftCertificateDtoRequest that = (GiftCertificateDtoRequest) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(price, that.price)
                && Objects.equals(duration, that.duration)
                && Objects.equals(create, that.create)
                && Objects.equals(update, that.update)
                && Objects.equals(tags, that.tags);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, duration, create, update, tags);
    }
}