package epam.com.esm.utils.converters.dto.custom;

import epam.com.esm.utils.statistics.facade.DtoDataTag;
import epam.com.esm.utils.statistics.service.ObjectDataTag;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;

/**
 * DtoDataTagConverter is the utility class, converts fields from object data tag object to dto data tag
 */
public final class DtoDataTagConverter {

    /**
     * Default constructor
     */
    private DtoDataTagConverter() {}

    /**
     * Converts object data tag to dto data tag
     *
     * @param o requested object data tag
     * @return {@code DtoDataTag} converted dto data tag
     */
    public static DtoDataTag toDto(ObjectDataTag o) {
        return new DtoDataTag(new TagDtoResponse(o.getTag()), o.getCost(), o.getCount());
    }
}