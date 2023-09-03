package epam.com.esm.utils.statistics.repository;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ResultTag is the data class, serves as container for querying result from dao tag search operation,
 * represents raw data from search
 */

public class ResultTag {

    /**
     * Holds tag name
     */
    private String name;

    /**
     * Holds order cost
     */
    private BigDecimal orderCost;

    /**
     * Holds tag count
     */
    private Long count;

    /**
     * Constructs result tag with provided tag name, order cost and tag count
     *
     * @param name provided tag name
     * @param orderCost provided order cost
     * @param count provided tag count
     */
    public ResultTag(String name, BigDecimal orderCost, Long count) {
        this.name = name;
        this.orderCost = orderCost;
        this.count = count;
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
     * Gets value from orderCost field
     *
     * @return {@code BigDecimal} orderCost value
     */
    public BigDecimal getOrderCost() {
        return orderCost;
    }

    /**
     * Sets new value to orderCost field
     *
     * @param orderCost value for setting
     */
    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
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
        ResultTag that = (ResultTag) o;
        return Objects.equals(name, that.name)
               && Objects.equals(orderCost, that.orderCost)
               && Objects.equals(count, that.count);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, orderCost, count);
    }
}