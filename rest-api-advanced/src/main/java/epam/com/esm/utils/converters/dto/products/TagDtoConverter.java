package epam.com.esm.utils.converters.dto.products;

import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * TagDtoConverter is the utility class, converts fields from tag object to tag dto response
 */
public final class TagDtoConverter {

    /**
     * Default constructor
     */
    private TagDtoConverter() {}

    /**
     * Converts tag object to tag dto response
     * If full equals true will convert tag with gift certificates, otherwise only tag will be converted
     *
     * @param t requested tag
     * @param full requested view
     * @return {@code TagDtoResponse} converted tag dto response
     */
    public static TagDtoResponse toDto(Tag t, boolean full) {
        TagDtoResponse dtoResp = new TagDtoResponse(t);

        if (full) {
            collectionToDto(t, dtoResp);
        }

        return dtoResp;
    }

    /**
     * Converts gift certificates for tag dto response from tag object
     *
     * @param t requested tag
     * @param r requested order dto response
     */
    private static void collectionToDto(Tag t, TagDtoResponse r) {
        if (t.getGiftCertificates() != null && !t.getGiftCertificates().isEmpty()) {
            r.setGiftCertificates(
                    t.getGiftCertificates().stream()
                                           .map(GiftCertificateDtoResponse::new)
                                           .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }
}