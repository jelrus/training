package com.epam.esm.view.dto.response;

import java.util.Objects;

/**
 * DtoResponse is the superclass for all DtoResponse entities
 */
public abstract class DtoResponse {

    /**
     * Field to hold name vale
     */
    private Long id;

    /**
     * Constructs DtoResponse object with id value
     *
     * @param id requested id value
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
        return Objects.hash(id);
    }
}