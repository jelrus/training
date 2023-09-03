package epam.com.esm.utils.hateoas.templates;

import epam.com.esm.utils.hateoas.templates.components.PlaceholderValue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ReplaceableTemplate is the class which provides string manipulation e.g. replacement for values
 */
public class ReplaceableTemplate {

    /**
     * Holds placeholder-values
     */
    private final Map<String, String> placeholdersValues;

    /**
     * Holds template
     */
    private String template;

    /**
     * Constructs ReplaceableTemplate with template and placeholder-value array
     *
     * @param template requested template
     * @param placeholderValue requested placeholder-values
     */
    public ReplaceableTemplate(String template, PlaceholderValue ... placeholderValue) {
        this.placeholdersValues = new LinkedHashMap<>();
        this.template = template;
        initPlaceholders(placeholderValue);
    }

    /**
     * Adds placeholder-value to map for link building
     *
     * @param placeholderValue requested placeholder value
     * @return {@code ReplaceableTemplate} current instance of replaceable template
     */
    public ReplaceableTemplate addReplacement(PlaceholderValue placeholderValue) {
        placeholdersValues.put(placeholderValue.getPlaceholder(), placeholderValue.getValue());
        return this;
    }

    /**
     * Builds link replacing placeholder with values
     *
     * @return {@code String} built link
     */
    public String build() {

        for (Map.Entry<String, String> e: placeholdersValues.entrySet()) {
            String buffer = this.template;
            this.template = buffer.replace(e.getKey(), e.getValue());
        }

        return this.template;
    }

    /**
     * Adds placeholder values to map
     *
     * @param placeholderValue requested placeholder-values
     */
    private void initPlaceholders(PlaceholderValue ... placeholderValue) {
        Arrays.stream(placeholderValue).forEach(pv -> this.placeholdersValues.put(pv.getPlaceholder(), pv.getValue()));
    }

    /**
     * Gets value from placeholderValues field
     *
     * @return {@code  Map<String, String>} placeholderValues map
     */
    public Map<String, String> getPlaceholdersValues() {
        return placeholdersValues;
    }

    /**
     * Gets value from template field
     *
     * @return {@code  String} template value
     */
    public String getTemplate() {
        return template;
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
        ReplaceableTemplate that = (ReplaceableTemplate) o;
        return Objects.equals(placeholdersValues, that.placeholdersValues) && Objects.equals(template, that.template);
    }

    /**
     * Calculates hash for source object
     *
     * @return {@code int} hashcode integer value
     */
    @Override
    public int hashCode() {
        return Objects.hash(placeholdersValues, template);
    }
}