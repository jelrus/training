package epam.com.esm.utils.search.filter.components;

import java.util.Objects;

/**
 * ClassDescription is the data class, carries prefix and join name for filtered search class map building
 */
public class ClassDescription {

    /**
     * Holds prefix value
     */
    private String prefix;

    /**
     * Holds join name
     */
    private String joinName;

    /**
     * Constructs ClassDescription with provided prefix and join name
     *
     * @param prefix provided class prefix
     * @param joinName provided class join name
     */
    public ClassDescription(String prefix, String joinName) {
        this.prefix = prefix;
        this.joinName = joinName;
    }

    /**
     * Gets value from prefix field
     *
     * @return {@code  String} prefix value
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets new value to prefix field
     *
     * @param prefix value for setting
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Gets value from joinName field
     *
     * @return {@code  String} joinName value
     */
    public String getJoinName() {
        return joinName;
    }

    /**
     * Sets new value to joinName field
     *
     * @param joinName value for setting
     */
    public void setJoinName(String joinName) {
        this.joinName = joinName;
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
        ClassDescription that = (ClassDescription) o;
        return Objects.equals(prefix, that.prefix) && Objects.equals(joinName, that.joinName);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(prefix, joinName);
    }
}