package epam.com.esm.utils.converters.date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * IsoDateConverter util class for converting attributes from datasource column to entity fields and vice versa
 */
@Converter
public final class IsoDateConverter implements AttributeConverter<LocalDateTime, String> {

    /**
     * ISO8601 pattern
     */
    private static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Default constructor
     */
    private IsoDateConverter() {}

    /**
     * Converts entity field value to datasource column value
     *
     * @param localDateTime the entity attribute value to be converted
     * @return {@code String} converted attribute
     */
    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(ISO8601_FORMAT));
    }

    /**
     * Converts datasource column value to entity field value
     *
     * @param s the date from the database column to be converted
     * @return {@code LocalDateTime} converted attribute
     */
    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        return LocalDateTime.from(DateTimeFormatter.ofPattern(ISO8601_FORMAT).parse(s));
    }
}