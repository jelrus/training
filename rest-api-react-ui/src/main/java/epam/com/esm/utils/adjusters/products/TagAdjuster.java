package epam.com.esm.utils.adjusters.products;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateNameDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * TagAdjuster utility class, adjust fields on tag create operations
 */
public final class TagAdjuster {

    /**
     * Default constructor
     */
    private TagAdjuster() {}

    /**
     * Converts tag dto fields to tag on add/delete tags operations
     *
     * @param tag requested tag
     * @param tagReq requested tag dto request
     */
    public static void adjustDtoFieldsOnGiftCertificatesChanges(Tag tag, TagDtoRequest tagReq) {
        if (!checkName(tagReq.getName())) {
            tagReq.setName(tag.getName());
        }

        if (!checkGiftCertificatesNull(tagReq)) {
            tagReq.setGiftCertificates(convertGiftCertificates(tag));
        }
    }

    /**
     * Converts tag's gift certificates to gift certificates name dto request
     *
     * @param tag requested tag
     * @return {@code List<GiftCertificateNameDtoRequest>} converted gift certificates name dto request
     */
    public static List<GiftCertificateNameDtoRequest> convertGiftCertificates(Tag tag) {
        return tag.getGiftCertificates().stream()
                .map(convertGiftCertificate())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Function for converting GiftCertificate to GiftCertificateNameDtoRequest
     *
     * @return {@code Function<GiftCertificate, GiftCertificateNameDtoRequest>} converting function
     */
    public static Function<GiftCertificate, GiftCertificateNameDtoRequest> convertGiftCertificate() {
        return gc -> {
            GiftCertificateNameDtoRequest req = new GiftCertificateNameDtoRequest();
            req.setName(gc.getName());
            return req;
        };
    }

    /**
     * Passes fields from tag dto request to tag
     *
     * @param tagReq requested tag dto request
     * @param t requested tag
     */
    public static void passFieldsFromTagDto(TagDtoRequest tagReq, Tag t) {
        t.setName(tagReq.getName());
    }

    /**
     * Checks if tag dto request null and name is correct
     *
     * @param dto requested tag dto request
     * @return {@code true} if tag dto request not null and name is correct
     */
    public static boolean checkTagDtoFields(TagDtoRequest dto) {
        return dto != null && checkName(dto.getName());
    }

    /**
     * Converts gift certificate's tags to tag dto requests
     *
     * @param gc requested gift certificate
     * @return {@code List<TagDtoRequest>} converted tag dto requests
     */
    public static List<TagDtoRequest> convertTags(GiftCertificate gc) {
        return gc.getTags().stream().map(convertTag()).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Function for converting Tag to TagDtoRequest
     *
     * @return {@code  Function<Tag, TagDtoRequest>} converting function
     */
    public static Function<Tag, TagDtoRequest> convertTag() {
        return x -> {
            TagDtoRequest req = new TagDtoRequest();
            req.setName(x.getName());
            return req;
        };
    }

    /**
     * Checks if tag dto requests is null, empty and contains null values
     *
     * @param dtoTags requested tag dto requests
     * @return {@code true} if tag dto requests not null, empty and doesn't contain null values
     */
    public static boolean checkDtoTags(List<TagDtoRequest> dtoTags) {
        return dtoTags != null && !dtoTags.isEmpty() && !dtoTags.contains(null);
    }

    /**
     * Converts tag dto requests to tags
     *
     * @param tagsReq requested tag dto requests
     * @return {@code List<Tag>} converted tags
     */
    public static List<Tag> convertToTags(List<TagDtoRequest> tagsReq) {
        List<Tag> tags = new ArrayList<>();

        for (TagDtoRequest t: tagsReq) {
            addTagIfCorrect(tags, t);
        }

        return tags;
    }

    /**
     * Checks if tag name is null, empty and correct length
     *
     * @param name requested tag's name
     * @return {@code true} if tag's name not null, empty and correct length
     */
    private static boolean checkName(String name) {
        return !StringUtils.isBlank(name) && name.length() <= 255;
    }

    /**
     * Checks if gift certificates dto requests on tag dto request is null
     *
     * @param dto requested tag dto request
     * @return {@code true} if gift certificates dto request no null
     */
    private static boolean checkGiftCertificatesNull(TagDtoRequest dto) {
        return dto.getGiftCertificates() != null;
    }

    /**
     * Converts tag dto request and puts it to list if correct
     *
     * @param tags requested tags
     * @param tagReq requested tag dto request
     */
    private static void addTagIfCorrect(List<Tag> tags, TagDtoRequest tagReq) {
        if (checkTagDtoFields(tagReq)) {
            tags.add(convertTag(tagReq));
        } else {
            throw new InputException("Tag fields was corrupted or wrong format used");
        }
    }

    /**
     * Converts tag dto request to tag
     *
     * @param tagReq requested tag dto request
     * @return {@code Tag} converted tag
     */
    private static Tag convertTag(TagDtoRequest tagReq) {
        Tag tag = new Tag();
        passFieldsFromTagDto(tagReq, tag);
        return tag;
    }
}