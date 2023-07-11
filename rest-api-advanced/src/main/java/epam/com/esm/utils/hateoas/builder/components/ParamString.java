package epam.com.esm.utils.hateoas.builder.components;

import java.util.Objects;

/**
 * ParamString is the data class which serves as pattern for link relation
 */
public class ParamString {

    /**
     * Holds link value
     */
    private final String link;

    /**
     * Holds prefix value
     */
    private final String prefix;

    /**
     * Holds suffix value
     */
    private final String suffix;

    /**
     * Constructs ParamString object with provided link, prefix and suffix
     *
     * @param link requested link
     * @param prefix requested prefix
     * @param suffix requested suffix
     */
    public ParamString(String link, String prefix, String suffix) {
        this.link = link;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Gets value from link field
     *
     * @return {@code String} link value
     */
    public String getLink() {
        return link;
    }

    /**
     * Gets value from prefix field
     *
     * @return {@code String} prefix value
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Gets value from suffix field
     *
     * @return {@code String} suffix value
     */
    public String getSuffix() {
        return suffix;
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
        ParamString that = (ParamString) o;
        return Objects.equals(link, that.link)
               && Objects.equals(prefix, that.prefix)
               && Objects.equals(suffix, that.suffix);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(link, prefix, suffix);
    }
}