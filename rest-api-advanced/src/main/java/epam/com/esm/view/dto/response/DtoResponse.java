package epam.com.esm.view.dto.response;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

/**
 * DtoResponse abstract class, serves as parent class to all dto responses children
 */
public abstract class DtoResponse extends RepresentationModel<DtoResponse>{

    /**
     * Holds id value
     */
    private Long id;

    /**
     * Constructs dto response with provided id
     *
     * @param id provided id
     */
    public DtoResponse(Long id) {
        this.id = id;
    }

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
        DtoResponse that = (DtoResponse) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}