package epam.com.esm.utils.hateoas.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class annotation which provides information about controller class to LinkBuilder
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLink {

    /**
     * Provides controller's name for relations building
     *
     * @return {@code String} controller's name
     */
    String name() default "";

    /**
     * Provides controller's mapping for link building
     *
     * @return {@code String} controller's mapping
     */
    String mapping() default "";
}