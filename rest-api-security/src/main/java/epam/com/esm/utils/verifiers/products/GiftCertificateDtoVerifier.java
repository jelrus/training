package epam.com.esm.utils.verifiers.products;

import epam.com.esm.exception.types.InputException;
import epam.com.esm.persistence.entity.impl.products.GiftCertificate;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateDtoRequest;
import epam.com.esm.view.dto.request.impl.products.GiftCertificateTagsDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static epam.com.esm.utils.adjusters.products.GiftCertificateAdjuster.*;
import static epam.com.esm.utils.adjusters.products.TagAdjuster.checkDtoTags;
import static epam.com.esm.utils.adjusters.products.TagAdjuster.convertToTags;

/**
 * GiftCertificateDtoVerifier is the service class, verifies gift certificate dto request and converts it to
 * gift certificate
 */
@Component
public class GiftCertificateDtoVerifier {

    /**
     * Default constructor
     */
    @Autowired
    public GiftCertificateDtoVerifier() {}

    /**
     * Verifies gift certificate dto request on create operation
     *
     * @param dto provided gift certificate dto request
     * @return {@code GiftCertificate} generated gift certificate
     */
    public GiftCertificate verifyCreate(GiftCertificateDtoRequest dto) {
        adjustDatesOnCreate(dto);
        return verify(dto);
    }

    /**
     * Verifies gift certificate dto request on update operation and adjusts if some fields empty
     *
     * @param dto provided gift certificate dto request
     * @param preUpdate pre updated gift certificate
     * @return {@code GiftCertificate} generated gift certificate
     */
    public GiftCertificate verifyUpdate(GiftCertificateDtoRequest dto, GiftCertificate preUpdate) {
        adjustDtoFieldsOnUpdate(preUpdate, dto);
        GiftCertificate updated = verify(dto);
        updated.setId(preUpdate.getId());
        return updated;
    }

    /**
     * Verifies gift certificate dto request on tag changes
     *
     * @param dto provided gift certificate dto request
     * @param preUpdate pre updated gift certificate
     * @return {@code GiftCertificate} generated gift certificate
     */
    public GiftCertificate verifyTagsChanges(GiftCertificateTagsDtoRequest dto, GiftCertificate preUpdate) {
        GiftCertificateDtoRequest req = new GiftCertificateDtoRequest();
        req.setTags(dto.getTags());
        checkDtoCollection(req);
        return verifyUpdate(req, preUpdate);
    }

    /**
     * Verifies gift certificate dto request and tags dto requests
     *
     * @param dto provided gift certificate dto request
     * @return {@code GiftCertificate} generated gift certificate
     */
    private GiftCertificate verify(GiftCertificateDtoRequest dto) {
        checkDtoFields(dto);
        GiftCertificate gCert = new GiftCertificate();

        if (checkDtoTags(dto.getTags())) {
            gCert.setTags(convertToTags(dto.getTags()));
        }

        passFieldsFromGiftCertificateDto(dto, gCert);
        return gCert;
    }

    /**
     * Checks if gift certificate dto request fields set correct
     *
     * @param dto provided gift certificate dto request
     */
    private void checkDtoFields(GiftCertificateDtoRequest dto) {
        if (!checkGiftCertificateDtoFields(dto)) {
            throw new InputException("Gift Certificate fields was corrupted or wrong format used");
        }
    }

    /**
     * Checks if gift certificate's tags dto requests set correct
     *
     * @param dto provided gift certificate dto request
     */
    private void checkDtoCollection(GiftCertificateDtoRequest dto) {
        if (!checkDtoTags(dto.getTags())) {
            throw new InputException("No tags attached to Gift Certificate");
        }
    }
}