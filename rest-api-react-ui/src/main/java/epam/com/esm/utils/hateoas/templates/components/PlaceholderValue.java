package epam.com.esm.utils.hateoas.templates.components;

import java.util.Objects;

/**
 * PlaceholderValue is the data class which holds pair - placeholder and it's value
 */
public class PlaceholderValue {

    /**
     * Holds placeholder value
     */
    private final String placeholder;

    /**
     * Holds string value
     */
    private final String value;

    /**
     * Constructs PlaceholderValue with provided placeholder and value
     *
     * @param placeholder provided placeholder value
     * @param value provided value for placeholder replacement
     */
    public PlaceholderValue(String placeholder, String value) {
        this.placeholder = placeholder;
        this.value = value;
    }

    /**
     * Gets value from placeholder field
     *
     * @return {@code  String} placeholder value
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Gets value from value field
     *
     * @return {@code  String} value to replace
     */
    public String getValue() {
        return value;
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
        PlaceholderValue that = (PlaceholderValue) o;
        return Objects.equals(placeholder, that.placeholder) && Objects.equals(value, that.value);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(placeholder, value);
    }
}