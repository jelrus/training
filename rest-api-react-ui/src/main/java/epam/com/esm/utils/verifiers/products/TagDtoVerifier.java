package epam.com.esm.utils.verifiers.products;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.products.Tag;
import epam.com.esm.view.dto.request.impl.products.TagDtoRequest;
import epam.com.esm.view.dto.request.impl.products.TagGiftCertificatesDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static epam.com.esm.utils.adjusters.products.GiftCertificateAdjuster.checkGiftCertificates;
import static epam.com.esm.utils.adjusters.products.GiftCertificateAdjuster.convertToGiftCertificates;
import static epam.com.esm.utils.adjusters.products.TagAdjuster.*;

/**
 * GiftCertificateDtoVerifier is the service class, verifies tag dto request and converts it to tag
 */
@Component
public class TagDtoVerifier {

    /**
     * Default constructor
     */
    @Autowired
    public TagDtoVerifier() {}

    /**
     * Verifies tag dto request on create operation
     *
     * @param dto provided tag dto request
     * @return {@code Tag} generated tag
     */
    public Tag verifyOnCreate(TagDtoRequest dto) {
        return verify(dto);
    }

    /**
     * Verifies tag dto request on gift certificate changes
     *
     * @param dto provided tag dto request
     * @param preUpdate pre updated tag
     * @return {@code Tag} generated tag
     */
    public Tag verifyChanges(TagGiftCertificatesDtoRequest dto, Tag preUpdate) {
        TagDtoRequest req = new TagDtoRequest();
        req.setGiftCertificates(dto.getGiftCertificates());
        checkDtoCollection(req);
        adjustDtoFieldsOnGiftCertificatesChanges(preUpdate, req);
        Tag updated = verify(req);
        updated.setId(preUpdate.getId());
        return updated;
    }

    /**
     * Verifies tag dto request and gift certificates dto requests
     *
     * @param dto provided tag dto request
     * @return {@code Tag} generated tag
     */
    private Tag verify(TagDtoRequest dto) {
        if (checkTagDtoFields(dto)) {
            Tag tag = new Tag();

            if (checkGiftCertificates(dto.getGiftCertificates())) {
                tag.setGiftCertificates(convertToGiftCertificates(dto.getGiftCertificates()));
            }

            passFieldsFromTagDto(dto, tag);

            return tag;
        } else {
            throw new InputException("Tag fields was corrupted or wrong format used");
        }
    }

    /**
     * Checks if tag's gift certificates dto requests set correct
     *
     * @param dto provided tag dto request
     */
    private void checkDtoCollection(TagDtoRequest dto) {
        if (!checkGiftCertificates(dto.getGiftCertificates())) {
            throw new InputException("No gift certificates attached to Tag");
        }
    }
}