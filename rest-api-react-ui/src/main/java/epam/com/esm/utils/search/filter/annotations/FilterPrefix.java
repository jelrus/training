package epam.com.esm.utils.search.filter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class annotation, provides class information for filtered search
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FilterPrefix {

    /**
     * Provides prefix by which duplicate field name will be distinguished
     *
     * @return class prefix
     */
    String prefix();
}
