package epam.com.esm.utils.converters.dto.products;

import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.view.dto.response.impl.products.GiftCertificateDtoResponse;
import epam.com.esm.view.dto.response.impl.products.TagDtoResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * GiftCertificateDtoConverter is the utility class, converts fields from gift certificate object to gift certificate
 * dto response
 */
public final class GiftCertificateDtoConverter {

    /**
     * Default constructor
     */
    private GiftCertificateDtoConverter() {}

    /**
     * Converts gift certificate object to gift certificate dto response
     * If full equals true will convert gift certificate with tags, otherwise only gift certificate will be converted
     *
     * @param gc requested gift certificate
     * @param full requested view
     * @return {@code GiftCertificateDtoResponse} converted gift certificate dto response
     */
    public static GiftCertificateDtoResponse toDto(GiftCertificate gc, boolean full) {
        GiftCertificateDtoResponse dtoResp = new GiftCertificateDtoResponse(gc);

        if (full) {
            collectionToDto(gc, dtoResp);
        }

        return dtoResp;
    }

    /**
     * Converts tags for gift certificate dto response from gift certificate object
     *
     * @param gc requested gift certificate
     * @param r requested order dto response
     */
    private static void collectionToDto(GiftCertificate gc, GiftCertificateDtoResponse r) {
        if (gc.getTags() != null && !gc.getTags().isEmpty()) {
            r.setTags(
                    gc.getTags().stream().map(TagDtoResponse::new)
                                         .collect(Collectors.toCollection(ArrayList::new))
            );
        }
    }
}