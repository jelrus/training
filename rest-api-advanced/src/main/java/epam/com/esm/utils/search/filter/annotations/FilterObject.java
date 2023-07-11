package epam.com.esm.utils.search.filter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Field annotation, provides object information for filtered search
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilterObject {

    /**
     * Provides class of filtered object
     *
     * @return class of filtered object
     */
    Class<?> cls();

    /**
     * Provides join name
     *
     * @return join name by which root will be joined
     */
    String path();
}
