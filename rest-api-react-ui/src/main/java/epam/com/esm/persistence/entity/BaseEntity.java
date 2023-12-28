package epam.com.esm.persistence.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * BaseEntity is the abstract class, serves as base class to all entities that uses id field in their model
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Holds id value
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Default constructor
     */
    public BaseEntity() {}

    /**
     * Gets value from id field
     *
     * @return {@code Long} id value
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets new value to id field
     *
     * @param id value for setting
     */
    public void setId(Long id) {
        this.id = id;
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
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}