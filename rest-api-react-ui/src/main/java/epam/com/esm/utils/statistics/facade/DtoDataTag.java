package epam.com.esm.utils.statistics.facade;

import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DtoDataTag is the data class, serves as container for DTO with search parameters, represents DTO data from search
 */
public class DtoDataTag extends RepresentationModel<DtoDataTag> {

    /**
     * Holds order cost
     */
    private BigDecimal cost;

    /**
     * Holds tag count value
     */
    private Long count;

    /**
     * Holds tag dto response
     */
    private TagDtoResponse tag;

    /**
     * Constructs result tag with provided tag dto response, order cost and tag count
     *
     * @param tag provided tag dto response
     * @param cost provided order cost
     * @param count provided tag count
     */
    public DtoDataTag(TagDtoResponse tag, BigDecimal cost, Long count) {
        this.tag = tag;
        this.cost = cost;
        this.count = count;
    }

    /**
     * Gets tag dto response
     *
     * @return {@code TagDtoResponse} tag dto response
     */
    public TagDtoResponse getTag() {
        return tag;
    }

    /**
     * Sets new tag
     *
     * @param tag for setting
     */
    public void setTag(TagDtoResponse tag) {
        this.tag = tag;
    }

    /**
     * Gets value from cost field
     *
     * @return {@code BigDecimal} cost value
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets new value to cost field
     *
     * @param cost value for setting
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets value from count field
     *
     * @return {@code Long} count value
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets new value to count field
     *
     * @param count value for setting
     */
    public void setCount(Long count) {
        this.count = count;
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
        DtoDataTag that = (DtoDataTag) o;
        return Objects.equals(tag, that.tag)
               && Objects.equals(cost, that.cost)
               && Objects.equals(count, that.count);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(tag, cost, count);
    }
}