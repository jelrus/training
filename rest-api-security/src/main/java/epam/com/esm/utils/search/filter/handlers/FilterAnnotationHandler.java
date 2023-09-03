package epam.com.esm.utils.search.filter.handlers;

import epam.com.esm.utils.search.filter.annotations.FilterObject;
import epam.com.esm.utils.search.filter.annotations.FilterParameter;
import epam.com.esm.utils.search.filter.annotations.FilterPrefix;
import epam.com.esm.utils.search.filter.components.ClassDescription;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FilterAnnotationHandler is the service class, scans class for filter parameters, builds dictionary and class map
 * for filtered search
 */
public final class FilterAnnotationHandler {

    /**
     * Holds prefix value
     */
    private final String prefix;

    /**
     * Holds search dictionary map
     */
    private Map<String, String> dictionary;

    /**
     * Holds search class map
     */
    private Map<Class<?>, ClassDescription> classMap;

    /**
     * Constructs FilterAnnotationHandler with provided class, builds base filter data
     *
     * @param cls class to filter
     */
    public FilterAnnotationHandler(Class<?> cls) {
        initMaps();
        this.prefix = scanForPrefix(cls);
        this.classMap = scanForObject(cls);
        this.dictionary = scanClassForParams(cls);
    }

    /**
     * Initializes class and dictionary maps
     */
    private void initMaps() {
        this.classMap = new LinkedHashMap<>();
        this.dictionary = new LinkedHashMap<>();
    }

    /**
     * Scans provided class for prefix
     *
     * @param cls provided class
     * @return {@code String} class prefix if found
     */
    private String scanForPrefix(Class<?> cls) {
        return cls.isAnnotationPresent(FilterPrefix.class) ? cls.getAnnotation(FilterPrefix.class).prefix() : null;
    }

    /**
     * Scans class for filter params
     *
     * @param cls provide class
     * @return {@code Map<String, String>} generated dictionary map
     */
    private Map<String, String> scanClassForParams(Class<?> cls) {
        Class<?> current = cls;

        while (current.getSuperclass() != null) {
            if (cls.isAnnotationPresent(FilterPrefix.class)) {
                String prefix = cls.getAnnotation(FilterPrefix.class).prefix();

                for (Field field : current.getDeclaredFields()) {
                    scanPlainField(prefix, field);
                    scanCollectionField(field);
                }
            }

            current = current.getSuperclass();
        }

        return dictionary;
    }

    /**
     * Scans field annotated with FilterParameter annotation and puts obtained data to dictionary
     *
     * @param prefix provided class prefix
     * @param field provided class field
     */
    private void scanPlainField(String prefix, Field field) {
        if (field.isAnnotationPresent(FilterParameter.class)) {
            field.setAccessible(true);
            String dbName = prefix + field.getAnnotation(FilterParameter.class).fieldName();
            String[] urlNames = field.getAnnotation(FilterParameter.class).alias();
            Arrays.stream(urlNames).forEach(x -> dictionary.put(x, dbName));
        }
    }

    /**
     * Scans field annotated with FilterObject annotation and scans its fields
     *
     * @param field provided class field
     */
    private void scanCollectionField(Field field) {
        if (field.isAnnotationPresent(FilterObject.class)) {
            Class<?> currentColl = field.getAnnotation(FilterObject.class).cls();

            while (currentColl.getSuperclass() != null) {
                if (currentColl.isAnnotationPresent(FilterPrefix.class)) {
                    String prefix = currentColl.getAnnotation(FilterPrefix.class).prefix();
                    for (Field fieldColl : currentColl.getDeclaredFields()) {
                        scanPlainField(prefix, fieldColl);
                    }
                }

                currentColl = currentColl.getSuperclass();
            }
        }
    }

    /**
     * Scans field annotated with FilterObject annotation and puts obtained data to class map
     *
     * @param cls provided class
     * @return {@code Map<Class<?>, ClassDescription>} generated class map
     */
    private Map<Class<?>, ClassDescription> scanForObject(Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(FilterObject.class)) {
                Class<?> clazz = field.getAnnotation(FilterObject.class).cls();
                String joinName = field.getAnnotation(FilterObject.class).path();

                if (clazz.isAnnotationPresent(FilterPrefix.class)) {
                    String prefix = clazz.getAnnotation(FilterPrefix.class).prefix();
                    classMap.put(clazz, new ClassDescription(prefix, joinName));
                }
            }
        }

        return classMap;
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
     * Gets dictionary map
     *
     * @return {@code Map<String, String>} dictionary map
     */
    public Map<String, String> getDictionary() {
        return dictionary;
    }

    /**
     * Gets class map
     *
     * @return {@code Map<Class<?>, ClassDescription>} class map
     */
    public Map<Class<?>, ClassDescription> getClassMap() {
        return classMap;
    }
}