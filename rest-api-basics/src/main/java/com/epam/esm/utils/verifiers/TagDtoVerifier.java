package com.epam.esm.utils.verifiers;

import com.epam.esm.persistence.entity.impl.Tag;
import com.epam.esm.exception.types.InputException;
import com.epam.esm.view.dto.request.impl.TagDtoRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TagDtoVerifier is the utility class, which used for dto request verification and dto conversion
 */
public final class TagDtoVerifier {

    /**
     * Default constructor
     */
    private TagDtoVerifier() {}

    /**
     * Verifies dto request for create operation and returns Tag object if dto contains proper data
     *
     * @param dto requested dto
     * @return {@code Tag} the result of verification
     */
    public static Tag verifyOnCreate(TagDtoRequest dto) {
        return verify(dto);
    }

    /**
     * Verifies dto request fields and requested GiftCertificates set for it
     * Used for dto response construction, if fields contain proper data
     * Will throw InputException, if one of the fields is inappropriate for Tag object
     *
     * @param dto requested dto
     * @return {@code GiftCertificate} the result of verification
     */
    private static Tag verify(TagDtoRequest dto) {
        if (checkTagDtoFields(dto)) {
            Tag tag = new Tag();

            if (GiftCertificateDtoVerifier.checkDtoGiftCertificates(dto.getGiftCertificates())) {
               tag.setGiftCertificates(GiftCertificateDtoVerifier.convertToGiftCertificates(dto.getGiftCertificates()));
            }

            passFieldsFromTagDto(dto, tag);
            return tag;
        } else {
            throw new InputException("Tag fields was corrupted or wrong format used");
        }
    }

    /**
     * Copies fields from TagDtoRequest object to Tag
     *
     * @param tagReq requested dto
     * @param t requested object
     */
    private static void passFieldsFromTagDto(TagDtoRequest tagReq, Tag t) {
        t.setName(tagReq.getName());
    }

    /**
     * Checks all fields of dto request except for GiftCertificate set
     *
     * @param dto requested dto
     * @return {@code true} if fields contain proper data
     */
    private static boolean checkTagDtoFields(TagDtoRequest dto) {
        return dto != null
                && TagDtoVerifier.checkName(dto.getName());
    }

    /**
     * Checks if name field contains proper data
     *
     * @param name requested name value
     * @return {@code true} if name contains proper data
     */
    private static boolean checkName(String name) {
        return !StringUtils.isBlank(name);
    }

    /**
     * Checks set of TageDtoRequest if null, empty or contains null value
     *
     * @param dtoTags set of tags dto requests
     * @return {@code true} if requested set meets conditions
     */
    static boolean checkDtoTags(Set<TagDtoRequest> dtoTags) {
        return dtoTags != null && !dtoTags.isEmpty() && !dtoTags.contains(null);
    }

    /**
     * Converts set of TagDtoRequest to set of Tag
     *
     * @param tagsReq requested set
     * @return {@code Set<Tag>} result of conversion
     */
    static Set<Tag> convertToTags(Set<TagDtoRequest> tagsReq) {
        Set<Tag> tags = new LinkedHashSet<>();

        for (TagDtoRequest t: tagsReq) {
            addTagIfCorrect(tags, t);
        }

        return tags;
    }

    /**
     * Checks TagDtoRequest object for data requirements and adds it to requested set
     *
     * @param tags requested set
     * @param tagReq requested dto
     */
    private static void addTagIfCorrect(Set<Tag> tags, TagDtoRequest tagReq) {
        if (TagDtoVerifier.checkTagDtoFields(tagReq)) {
            tags.add(convertTag(tagReq));
        } else {
            throw new InputException("Tag fields was corrupted or wrong format used");
        }
    }

    /**
     * Converts single TagDtoRequest to Tag
     *
     * @param tagReq requested dto
     * @return {@code Tag} result of conversion
     */
    private static Tag convertTag(TagDtoRequest tagReq) {
        Tag tag = new Tag();
        passFieldsFromTagDto(tagReq, tag);
        return tag;
    }
}