package epam.com.esm.utils.search.filter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotation, provides plain field information for building filtered search dictionary
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterParameter {

    /**
     * Provides field name for key in dictionary
     *
     * @return dictionary field name
     */
    String fieldName();

    /**
     * Provides alias with field name for values in dictionary
     *
     * @return dictionary alias values
     */
    String[] alias();
}